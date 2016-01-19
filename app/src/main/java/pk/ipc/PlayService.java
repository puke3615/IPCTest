package pk.ipc;

import android.app.Service;
import android.content.ComponentCallbacks;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author zijiao
 * @version 2016/1/14
 * @Mark
 */
public class PlayService extends Service {

    @Override
    public void onCreate() {
        L("onCreate");
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L("onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void L(Object s) {
        Log.i("PlayService", s + "");
    }

}
