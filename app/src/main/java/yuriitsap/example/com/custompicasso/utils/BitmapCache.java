package yuriitsap.example.com.custompicasso.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by yuriitsap on 28.04.15.
 */
public class BitmapCache {

    private static volatile BitmapCache instance;
    private int mAvailableCachedMemory;
    private LruCache<String, Bitmap> mMemoryCache;


    public static synchronized BitmapCache getInstance() {
        if (instance == null) {
            instance = new BitmapCache();
        }
        return instance;
    }

    private BitmapCache() {
        mAvailableCachedMemory = (int) Runtime.getRuntime().maxMemory() / 1024 * 4;
        mMemoryCache = new LruCache<String, Bitmap>(mAvailableCachedMemory) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }

        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    public int getCachedMemory() {
        return mAvailableCachedMemory;
    }

    public void setCachedMemory(int cachedMemory) {
        this.mAvailableCachedMemory = cachedMemory;
    }

    public int memoryAvailable() {
        return mAvailableCachedMemory - mMemoryCache.size();
    }
}
