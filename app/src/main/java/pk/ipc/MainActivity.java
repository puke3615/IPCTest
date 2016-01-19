package pk.ipc;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

import java.io.File;

import pk.base.debug.DebugActivity;
import pk.base.debug.Helper;
import pk.base.util.ToastUtil;
import pk.ipc.play.DataProvider;
import pk.ipc.play.OneService;
import pk.ipc.play.SettingActivity;
import pk.ipc.play.TwoService;


public class MainActivity extends DebugActivity {

    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/kgmusic/download/";
//    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mogujie/transformer/music/";
    public static final String MessengerACTION = "MessengerService.TTT";
    public static final String AidlACTION = "AIDLService.TTT";
    private IPlayer mBinder;

    private Messenger mMessenger;

    private ServiceConnection mMessengerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void init() {
        Intent messenger = new Intent(MessengerACTION);
        messenger.setPackage(getPackageName());
        bindService(messenger, mMessengerConnection, Context.BIND_AUTO_CREATE);


        Intent aidl = new Intent(AidlACTION);
        aidl.setPackage(getPackageName());
        bindService(aidl, mAidlConnection, Context.BIND_AUTO_CREATE);

        startService(new Intent(this, PlayService.class));

        startService(new Intent(this, OneService.class));
        startService(new Intent(this, TwoService.class));
    }


    private ServiceConnection mAidlConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mBinder = IPlayer.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBinder = null;
        }
    };


    @Override
    protected Item.Builder getItems(final Item.Builder builder) {
        return builder
                .add(
                        new Item("choose", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                choose();
                            }
                        })
                )
                .add(
                        new Item("Start", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                )
                .add(
                        new Item("测试Messenger", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    Message msg = Message.obtain();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("key", "MESSENGER");
                                    msg.obj = bundle;
                                    mMessenger.send(msg);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                )
                .add(
                        new Item("测试AIDL", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mBinder != null) {
//                                        mBinder.transact(0, new Data("发送数据测试").create(), null, 0);
                                    try {
                                        mBinder.play("AIDL");
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        })
                )
                .add(
                        new Item("1232", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Helper.getData(mContext, new Helper.IR() {

                                    @Override
                                    public void onInput(String[] r) {
                                        StringBuilder sb = new StringBuilder();
                                        for (String s : r) {
                                            sb.append(s).append(" ");
                                        }
                                        ToastUtil.show(sb.toString());
                                    }
                                }, "测试1", "测试2", "测试3");
                            }
                        })
                );
    }

    private void choose() {
        File file = new File(PATH);
        if (file.exists() && file.isDirectory()) {
            final File[] fs = file.listFiles();
            String[] names = new String[fs.length];
            for (int i = 0; i < fs.length; i ++) {
                names[i] = fs[i].getName();
            }
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Choose")
                    .setItems(names, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataProvider.instance().setPath(fs[which].getAbsolutePath());
                            startActivity(new Intent(mContext, SettingActivity.class));
                        }
                    })
                    .show();
        }
    }
}
