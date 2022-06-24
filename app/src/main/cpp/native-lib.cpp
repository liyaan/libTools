#include <jni.h>
#include <string>
#include <android/log.h>
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,"LIYAAN",__VA_ARGS__)
extern "C"{
    #include <libavcodec/avcodec.h>
    #include <libavformat/avformat.h>
}

static double r2d(AVRational r){
    return r.num==0||r.den==0?0:(double)r.num/(double)r.den;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_liyaan_myffmepgfirst_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    hello+=avcodec_configuration();
    LOGW("%s",hello.c_str());
    return env->NewStringUTF(hello.c_str());
}extern "C"
JNIEXPORT void JNICALL
Java_com_liyaan_myffmepgfirst_MainActivity_openFile(JNIEnv *env, jobject thiz, jstring url,jobject handle) {
    const char *url_ = env->GetStringUTFChars(url,0);
    //将读写方式放在读写权限的末尾："rb"、"wt"、"ab"、"r+b"、"w+t"、"a+t"
    //将读写方式放在读写权限的中间："rb+"、"wt+"、"ab+"
//    FILE *fp = fopen(url_,"rb");
//
//    if (fp==NULL){
//        __android_log_print(ANDROID_LOG_ERROR, "LIYAAN", "FOPEN FAIL with %d",errno);
//        LOGW("FILE FAILE = %s",url_);
//    }else{
//        LOGW("FILE SUCCESS = %s",url_);
//        fclose(fp);
//    }
    LOGW("%s",url_);
    //初始化解封装
    av_register_all();
    //初始化网络
    avformat_network_init();
    //视频上下文
    AVFormatContext *ic = NULL;
    int fps = 0;
    int width = 0;
    int height = 0;
    int videoStream = 0;
    int audioStream = 0;
    //打开文件
    int ret = avformat_open_input(&ic,url_,0,0);
    if (ret != 0){
        LOGW("avformat_open_input failed!:%s",av_err2str(ret));
        return;
    }
    LOGW("avformat_open_input %s success",url_);
    ret = avformat_find_stream_info(ic,0);
    if (ret!=0){
        LOGW("avformat_find_stream_info failed!:%s",av_err2str(ret));
        return;
    }
    LOGW("duration = %lld , nb_streams = %d",ic->duration,ic->nb_streams);
    //ic->nb_streams循环获取音视频流的信息
    for (int i = 0; i < ic->nb_streams ; i++) {
        AVStream *as = ic->streams[i];
        if (as->codecpar->codec_type == AVMEDIA_TYPE_VIDEO){
            LOGW("视频数据");
            videoStream = i;
            fps = r2d(as->avg_frame_rate);
            LOGW("FPS = %d , width = %d , height = %d , codeid = %d，pix_format = %d",fps,
                    as->codecpar->width,as->codecpar->height,as->codecpar->codec_id,as->codecpar->format);
        }else if (as->codecpar->codec_type == AVMEDIA_TYPE_AUDIO){
            LOGW("音频数据");
            audioStream = i;
            LOGW("sample_rate = %d  channels=%d sample_format=%d",as->codecpar->sample_rate,
                    as->codecpar->channels,as->codecpar->format);
        }
    }
    //获取音频流的信息
    audioStream = av_find_best_stream(ic,AVMEDIA_TYPE_AUDIO,-1,
            -1,NULL,0);
    LOGW("av_find_best_stream audioStream = %d",audioStream);
    ///////////////打开视频解码器////////////
    //先找到解码器 ----软解码
    AVCodec *codec = avcodec_find_decoder(ic->streams[videoStream]->codecpar->codec_id);
    //解码器初始化
    AVCodecContext *vc = avcodec_alloc_context3(codec);
    avcodec_parameters_to_context(vc,ic->streams[videoStream]->codecpar);
    vc->thread_count = 1;
    ret = avcodec_open2(vc,0,0);
    if(ret != 0){
        LOGW("avcodec open2 failed!");
        return;
    }

    ///////////////打开音频解码器////////////
    //先找到解码器 ----软解码
    AVCodec *acodec = avcodec_find_decoder(ic->streams[audioStream]->codecpar->codec_id);
    //解码器初始化
    AVCodecContext *ac = avcodec_alloc_context3(acodec);
    avcodec_parameters_to_context(ac,ic->streams[audioStream]->codecpar);
    ac->thread_count = 1;
    ret = avcodec_open2(ac,0,0);
    if(ret != 0){
        LOGW("avcodec open2 failed!");
        return;
    }
//    AVPacket  *pkt = av_packet_alloc();
//    for (;;){
//        int re = av_read_frame(ic,pkt);
//        if (re != 0){
//            LOGW("读取到结尾处");
//            int pos = 10*r2d(ic->streams[videoStream]->time_base);
//            av_seek_frame(ic,videoStream,pos,AVSEEK_FLAG_BACKWARD|AVSEEK_FLAG_FRAME);
//            continue;
//        }
//        LOGW("STREAM = %d size = %d pts=%lld flag=%d",pkt->stream_index,pkt->size,pkt->pts,pkt->flags);
//        av_packet_unref(pkt);
//    }
    avformat_close_input(&ic);
    env->ReleaseStringUTFChars(url,url_);
    return;
}