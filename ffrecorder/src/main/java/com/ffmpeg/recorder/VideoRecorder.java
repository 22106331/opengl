package com.ffmpeg.recorder;

public class VideoRecorder {
    private VideoRecorder(){}


    static {
        System.loadLibrary("ffmpeg");
        System.loadLibrary("media_recorder");
    }

    /**
     * 初始化录制器
     * @param videoPath
     * @param previewWidth
     * @param previewHeight
     * @param videoWidth
     * @param videoHeight
     * @param frameRate
     * @param bitRate
     * @param enableAudio
     * @param audioBitRate
     * @param audioSampleRate
     * @return
     */
    public static native int initMediaRecorder(String videoPath, int previewWidth, int previewHeight,
                                               int videoWidth, int videoHeight, int frameRate,
                                               int bitRate, boolean enableAudio,
                                               int audioBitRate, int audioSampleRate);

    /**
     * 开始录制
     */
    public static native void startRecord();

    /**
     * 发送需要编码的yuv数据
     * @param data
     * @return
     */
    public static native int encodeYUVFrame(byte[] data);

    /**
     * 发送需要编码的PCM数据
     * @param data
     * @return
     */
    public static native int encodePCMFrame(byte[] data, int len);

    /**
     * 发送停止命令
     */
    public static native void stopRecord();


    /**
     * 释放资源
     */
    public static native void nativeRelease();
}
