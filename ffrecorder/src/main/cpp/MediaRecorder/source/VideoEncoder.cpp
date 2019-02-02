//
// Created by fangyanlei on 19-2-2.
//

#include "VideoEncoder.h"

VideoEncoder::VideoEncoder()
        :isInit(false),
         avCodecContext(nullptr),
         avCodec(nullptr)
{
    av_log_set_callback(&ffmpeg_log);
}

void VideoEncoder::initEncoder(EncoderParams *params)
{
    if (params != nullptr && !isInit) {
        avCodec = avcodec_find_encoder(AV_CODEC_ID_H264);
        if (!avCodec) {
            av_log(nullptr,AV_LOG_ERROR,"find encoder null");
            return;
        }
        avCodecContext = avcodec_alloc_context3(avCodec);
        if (!avCodecContext) {
            av_log(nullptr,AV_LOG_ERROR,"avcodec_alloc_context3 null");
            return;
        }
        avCodecContext->bit_rate = params->bitRate;
        avCodecContext->width = params->videoWidth;
        avCodecContext->height = params->videoHeight;
        avCodecContext


    }
}

void ffmpeg_log(void *ptr, int level, const char *fmt, va_list vl) {
    FILE *fp = fopen("/storage/emulated/0/av_log.txt", "a+");
    if (fp) {
        vfprintf(fp, fmt, vl);
        fflush(fp);
        fclose(fp);
    }
}