//
// Created by fangyanlei on 19-2-2.
//

#ifndef OPENGL_VIDEOENCODER_H
#define OPENGL_VIDEOENCODER_H

#include "CommonRecorder.h"
#include "RecorderParams.h"

class VideoEncoder {
    public:
        VideoEncoder();
        ~VideoEncoder();
        int initEncoder(EncoderParams * params);
        int videoEncode(uint8_t * data);
        int stopEncode();
    private:
        bool isInit;
        AVCodec *avCodec ;
        AVCodecContext *avCodecContext ;
        AVFormatContext * avFormatContext;
        AVStream *avStream;
        AVFrame *yuv;
        AVPacket *pkt;

};


#endif //OPENGL_VIDEOENCODER_H
