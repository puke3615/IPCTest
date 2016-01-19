package pk.ipc.play;

import android.database.Observable;

/**
 * @author zijiao
 * @version 2016/1/14
 * @Mark
 */
public class DataProvider extends Observable<IData> {

    private DataProvider() {
    }

    private static class DataProviderHolder {
        static DataProvider instance = new DataProvider();
    }

    public static DataProvider instance() {
        return DataProviderHolder.instance;
    }

    public synchronized void setPath(String path) {
        for (IData data : mObservers) {
            if (data != null) {
                data.onChange(path);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
