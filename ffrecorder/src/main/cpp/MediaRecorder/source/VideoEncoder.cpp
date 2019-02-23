//
// Created by fangyanlei on 19-2-2.
//

#include "VideoEncoder.h"
#include "AndroidLog.h"
void ffmpeg_log(void *ptr, int level, const char *fmt, va_list vl) {
    FILE *fp = fopen("/storage/emulated/0/av_log.txt", "a+");
    if (fp) {
        vfprintf(fp, fmt, vl);
        fflush(fp);
        fclose(fp);
    }
}

VideoEncoder::VideoEncoder()
        :isInit(false),
         avCodecContext(nullptr),
         avCodec(nullptr),
         avFormatContext(nullptr),
         next_pts(0)
{
    av_log_set_callback(ffmpeg_log);
}

int VideoEncoder::initEncoder(EncoderParams *params)
{
    int ret = -1;
    if (params != nullptr && !isInit) {
        ret = 0;
        av_register_all();
        avcodec_register_all();
        avCodec = avcodec_find_encoder(AV_CODEC_ID_H264);
        if (!avCodec) {
            av_log(nullptr,AV_LOG_ERROR,"find encoder null");
            goto error;
        }
        avCodecContext = avcodec_alloc_context3(avCodec);
        if (!avCodecContext) {
            ALOGE("avcodec_alloc_context3 null");
            av_log(nullptr,AV_LOG_ERROR,"avcodec_alloc_context3 null");
            goto error;
        }
        avCodecContext->bit_rate = params->bitRate;
        avCodecContext->width = params->videoWidth;
        avCodecContext->height = params->videoHeight;
        avCodecContext->gop_size = 12;
        avCodecContext->framerate = (AVRational){params->frameRate,1};
        avCodecContext->time_base = (AVRational){1,params->frameRate};
        avCodecContext->max_b_frames = 1;
        avCodecContext->pix_fmt = params->pixelFormat;
        avCodecContext->thread_count = params->threadCount;

        avCodecContext->codec_id = AV_CODEC_ID_H264;
        avCodecContext->flags |= AV_CODEC_FLAG_GLOBAL_HEADER;
        AVDictionary *opt = nullptr;
        av_dict_set(&opt, "tune", "zerolatency", 0);
        av_dict_set(&opt, "profile", "baseline", 0);
        av_opt_set(avCodecContext->priv_data, "preset", "slow", 0);
        ret = avcodec_open2(avCodecContext, avCodec, NULL);
        if (ret < 0 ) {
            char * errStr = av_err2str(ret);
            ALOGE("avcodec_open2 fail %s",errStr);
            av_log(nullptr,AV_LOG_ERROR,"avcodec_open2 fail");
            goto error;
        }
        ret = avformat_alloc_output_context2(&avFormatContext, nullptr, nullptr,params->outputFilePath);
        if (ret < 0) {
            ALOGE("avformat_alloc_output_context2 fail");
            av_log(nullptr,AV_LOG_ERROR,"avformat_alloc_output_context2 fail");
            goto error;
        }

        avStream = avformat_new_stream(avFormatContext,nullptr);
        avStream->id = 0;
        avStream->codecpar->codec_tag = 0;
        ret = avcodec_parameters_from_context(avStream->codecpar,avCodecContext);
        if (ret < 0) {
            ALOGE("avcodec_parameters_from_context fail");
            av_log(nullptr,AV_LOG_ERROR,"avcodec_parameters_from_context fail");
            goto error;
        }
        av_dict_set(&avStream->metadata, "rotate", "90", 0);

        yuv = av_frame_alloc();
        yuv->format = AV_PIX_FMT_YUV420P;
        yuv->width = params->videoWidth;
        yuv->height = params->videoHeight;

        ret = av_frame_get_buffer(yuv,32);
        if (ret < 0) {
            ALOGE("av_frame_get_buffer fail");
            av_log(nullptr,AV_LOG_ERROR,"av_frame_get_buffer fail");
            goto error;
        }
        ret = avio_open(&avFormatContext->pb,params->outputFilePath,AVIO_FLAG_WRITE);
        if (ret < 0) {
            ALOGE("avio_open fail");
            av_log(nullptr,AV_LOG_ERROR,"avio_open fail");
            goto error;
        }
        ret = avformat_write_header(avFormatContext, nullptr);
        if (ret < 0) {
            ALOGE("avformat_write_header fail");
            av_log(nullptr,AV_LOG_ERROR,"avformat_write_header fail");
        }
    }
    error:
    ;
    return ret;
}

int VideoEncoder::videoEncode(uint8_t *data) {
    memcpy(yuv->data[0], data, avCodecContext->width * avCodecContext->height);
    memcpy(yuv->data[1], (char *) data + avCodecContext->width * avCodecContext->height,
           avCodecContext->width * avCodecContext->height / 4);
    memcpy(yuv->data[2], (char *) data + avCodecContext->width * avCodecContext->height * 5 / 4,
           avCodecContext->width * avCodecContext->height / 4);
    int ret = avcodec_send_frame(avCodecContext,yuv);
    yuv->pts = av_rescale_q(next_pts++,
                              avCodecContext->framerate, avCodecContext->time_base);
    if (ret != 0)
    {
        ALOGE("avcodec_send_frame fail");
        return ret;
    }
    AVPacket pkt = {0};
    av_init_packet(&pkt);
    //接收编码结果
    ret = avcodec_receive_packet(avCodecContext,&pkt);
    if (ret != 0){
        ALOGE("avcodec_receive_packet fail");
    }
    //将编码后的帧写入文件
    av_interleaved_write_frame(avFormatContext,&pkt);
    av_packet_unref(&pkt);
    return 0;
}

int VideoEncoder::stopEncode() {
    if (avFormatContext) {
        av_write_trailer(avFormatContext);
    }
    return 0;
}

VideoEncoder::~VideoEncoder() {
    //关闭视频输出IO
    avio_close(avFormatContext->pb);

    //清理封装输出上下文
    avformat_free_context(avFormatContext);

    av_frame_free(&yuv);

    //关闭编码器
    avcodec_close(avCodecContext);

    //清理编码器上下文
    avcodec_free_context(&avCodecContext);
}
