package pk.ipc;

import android.os.Environment;
import android.os.Parcel;

/**
 * @author zijiao
 * @version 2016/1/14
 * @Mark
 */
public class Data {

    public String content;

    public Data(String content) {
        this.content = content;
    }

    public Parcel create() {
        Parcel p = Parcel.obtain();
        p.writeString(content);
        return p;
    }

}
