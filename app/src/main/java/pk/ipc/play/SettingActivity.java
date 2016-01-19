package pk.ipc.play;

import android.widget.SeekBar;

import pk.base.anno.PK;
import pk.base.base.BaseActivity;
import pk.ipc.IMusic;
import pk.ipc.MP3Player;
import pk.ipc.R;

/**
 * @author zijiao
 * @version 2016/1/14
 * @Mark
 */
@PK(R.layout.activity_setting)
public class SettingActivity extends BaseActivity {

    @PK
    private SeekBar p1, p2;
    private boolean isTouch1, isTouch2;
    private BaseService mService1, mService2;

    @Override
    protected void init() {
        ServiceHolder holder = ServiceHolder.instance();
        mService1 = holder.get(OneService.class);
        mService2 = holder.get(TwoService.class);
        mService1.getMusic().setUpdateListener(new IMusic.IUpdateListener() {
            @Override
            public void onUpdate(long cur, long count) {
                if (!isTouch1) {
                    p1.setProgress((int) (cur * 100f / count));
                }
            }
        });
        mService2.getMusic().setUpdateListener(new IMusic.IUpdateListener() {
            @Override
            public void onUpdate(long cur, long count) {
                if (!isTouch2) {
                    p2.setProgress((int) (cur * 100f / count));
                }
            }
        });

        p1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isTouch1 = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mService1.getMusic().seekTo(seekBar.getProgress() / 100f);
                isTouch1 = false;
            }
        });
        p2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isTouch2 = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mService2.getMusic().seekTo(seekBar.getProgress() / 100f);
                isTouch2 = false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mService1.getMusic().setUpdateListener(null);
        mService2.getMusic().setUpdateListener(null);
    }

    @Override
    protected void initListener() {

    }
}
