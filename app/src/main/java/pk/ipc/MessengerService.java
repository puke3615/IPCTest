package pk.ipc;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

import pk.base.util.ToastUtil;

/**
 * @author zijiao
 * @version 2016/1/14
 * @Mark
 */
public class MessengerService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            String s = ((Bundle)msg.obj).getString("key");
            Log.i("IPC", s + "");
            T(s);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Messenger(mHandler).getBinder();
    }

    void T(Object s) {
        ToastUtil.show(s);
        Log.i("IPC", s + "");
    }

}
