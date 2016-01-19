package pk.ipc.play;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import pk.ipc.IMusic;
import pk.ipc.MP3Player;

/**
 * @author zijiao
 * @version 2016/1/14
 * @Mark
 */
public abstract class BaseService extends Service implements IData {

    private IMusic mMusic;

    @Override
    public void onCreate() {
        ServiceHolder.instance().put(this, this);
        DataProvider.instance().registerObserver(this);
        mMusic = new MP3Player(this);
    }

    public IMusic getMusic() {
        return mMusic;
    }

    @Override
    public void onChange(String path) {
        mMusic.play(path);
    }

    @Override
    public void onDestroy() {
        DataProvider.instance().unregisterObserver(this);
        ServiceHolder.instance().remove(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicHolder();
    }

    public class MusicHolder extends Binder {

        public IMusic getMusic() {
            return mMusic;
        }

    }

}
