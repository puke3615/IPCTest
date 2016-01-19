package pk.ipc.play;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zijiao
 * @version 2016/1/15
 * @Mark
 */
public class ServiceHolder {

    private static final byte[] mLock = new byte[0];
    private static ServiceHolder mInstance;
    private final Map<Class<?>, BaseService> mMap = new ConcurrentHashMap<>();

    private ServiceHolder() {
    }

    public static ServiceHolder instance() {
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new ServiceHolder();
                }
            }
        }
        return mInstance;
    }

    public void put(Object obj, BaseService baseService) {
        mMap.put(obj.getClass(), baseService);
    }

    public void remove(Object obj) {
        Class<?> cls = obj.getClass();
        if (mMap.containsKey(cls)) {
            mMap.remove(cls);
        }
    }

    public <T extends BaseService> T get(Class cls) {
        return (T) mMap.get(cls);
    }

    public void removeAll() {
        mMap.clear();
    }

}
