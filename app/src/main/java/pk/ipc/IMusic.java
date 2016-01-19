package pk.ipc;

/**
 * @author zijiao
 * @version 2015/12/21
 * @Mark 音乐播放接口
 */
public interface IMusic {

    /**
     * 播放
     * @param file
     */
    void play(String file);

    /**
     * 暂停
     */
    void pause();

    /**
     * 继续
     */
    void resume();

    void seekTo(float progress);

    /**
     * 停止
     */
    void stop();

    /**
     * 设置播放器回调监听
     * @param errorListener
     */
    void setErrorListener(IPlayError errorListener);

    void setUpdateListener(IUpdateListener listener);

    public static interface IUpdateListener {
        void onUpdate(long cur, long count);
    }

    public static interface IPlayError {
        void onError();
    }

}
