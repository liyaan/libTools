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
Java_com_liyaan_myffmepgfirst_MainActivity_openFile(JNIEnv *env, jobject thiz, jstring url) {
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
    av_register_all();
    avformat_network_init();
    AVFormatContext *ic = NULL;
    int fps = 0;
    int width = 0;
    int height = 0;
    int videoStream = 0;
    int audioStream = 0;
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
    for (int i = 0; i < ic->nb_streams ; i++) {
        AVStream *as = ic->streams[i];
        if (as->codecpar->codec_type == AVMEDIA_TYPE_VIDEO){
            LOGW("视频数据");
            videoStream = i;
            fps = r2d(as->avg_frame_rate);
            LOGW("FPS = %d , width = %d , height = %d , codeid = %d",fps,
                    as->codecpar->width,as->codecpar->height,as->codecpar->codec_id);
        }else if (as->codecpar->codec_type == AVMEDIA_TYPE_AUDIO){
            LOGW("音频数据");
            audioStream = i;
            LOGW("sample_rate = %d  channels=%d sample_format=%d",as->codecpar->sample_rate,
                    as->codecpar->channels,as->codecpar->format);
        }
    }
    audioStream = av_find_best_stream(ic,AVMEDIA_TYPE_AUDIO,-1,
            -1,NULL,0);
    LOGW("av_find_best_stream audioStream = %d",audioStream);

    avformat_close_input(&ic);
    env->ReleaseStringUTFChars(url,url_);
    return;
}