package pk.ipc;

import android.animation.ValueAnimator;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;

/**
 * @author zijiao
 * @version 2015/12/22
 * @Mark 音乐播放页面的实现类
 */
public class MP3Player implements IMusic, MediaPlayer.OnErrorListener {

    private MediaPlayer mPlayer;
    private Context mContext;
    private IPlayError mErrorListener;
    private IUpdateListener mUpdateListener;

    public MP3Player(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public void play(String file) {
        stop();
        if (!TextUtils.isEmpty(file)) {
            Uri uri = Uri.parse(file);
            mPlayer = MediaPlayer.create(mContext, uri);
            mPlayer.setOnErrorListener(this);
            mPlayer.start();

            ValueAnimator v = ValueAnimator.ofInt(0, Integer.MAX_VALUE);
            v.setDuration(200);
            v.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator a) {
                    if (mUpdateListener != null && mPlayer != null) {
                        mUpdateListener.onUpdate(mPlayer.getCurrentPosition(), mPlayer.getDuration());
                    }
                }
            });
            v.setRepeatCount(Integer.MAX_VALUE);
            v.start();
        }
    }

    @Override
    public void pause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
        }
    }

    @Override
    public void resume() {
        if (mPlayer != null && !mPlayer.isPlaying()) {
            mPlayer.start();
        }
    }

    @Override
    public void seekTo(float progress) {
        if (mPlayer != null) {
            mPlayer.seekTo((int) (mPlayer.getDuration() * progress));
        }
    }

    @Override
    public void stop() {
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop();
            }
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void setErrorListener(IPlayError errorListener) {
        this.mErrorListener = errorListener;
    }

    @Override
    public void setUpdateListener(IUpdateListener listener) {
        this.mUpdateListener = listener;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (mErrorListener != null) {
            mErrorListener.onError();
        }
        return false;
    }
}
