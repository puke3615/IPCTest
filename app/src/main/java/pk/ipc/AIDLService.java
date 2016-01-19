package pk.ipc;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import pk.base.util.ToastUtil;

/**
 * @author zijiao
 * @version 2016/1/14
 * @Mark
 */
public class AIDLService extends Service {

    public Handler mHandler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Player();
    }

    public class Player extends IPlayer.Stub {

        @Override
        public void play(final String title) throws RemoteException {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    T(title);
                }
            });
        }
    }

    void T(Object s) {
        ToastUtil.show(s);
        Log.i("IPC", s + "");
    }

}
