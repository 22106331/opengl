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
    void initEncoder(EncoderParams * params);

private:
    void ffmpeg_log(void *ptr, int level, const char *fmt, va_list vl);
    bool isInit;
    AVCodec *avCodec ;
    AVCodecContext *avCodecContext ;
};


#endif //OPENGL_VIDEOENCODER_H
