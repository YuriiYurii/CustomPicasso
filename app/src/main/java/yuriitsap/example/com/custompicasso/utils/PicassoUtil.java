package yuriitsap.example.com.custompicasso.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yuriitsap on 27.04.15.
 */
public class PicassoUtil {

    private static volatile PicassoUtil instance;
    private Handler mHandler = new Handler();
    private Context mContext;
    private ExecutorService mExecutorService;
    private BitmapCache mBitmapCache;

    public PicassoUtil(Context context, ExecutorService executorService,
            BitmapCache bitmapCache) {
        mContext = context;
        mExecutorService = executorService;
        mBitmapCache = bitmapCache;
    }

    public static PicassoUtil with(Context context) {
        if (instance == null) {
            synchronized (PicassoUtil.class) {
                if (instance == null) {
                    instance = new Builder(context).build();
                }
            }
        }
        return instance;
    }

    public Request load(int resId) {
        return new Request(null, resId, instance);
    }

    public Request load(String filePath) {
        return new Request(Uri.parse(filePath), 0, instance);
    }

    public Request load(Uri uri) {
        return new Request(uri, 0, instance);
    }

    private static class Builder {

        private Context mContext;
        private ExecutorService mExecutorService;
        private BitmapCache mBitmapCache;

        public Builder(Context context) {
            mContext = context;
        }

        public PicassoUtil build() {
            if (mBitmapCache == null) {
                mBitmapCache = BitmapCache.getInstance();
            }
            if (mExecutorService == null) {
                mExecutorService = Executors.newCachedThreadPool();
            }
            return new PicassoUtil(mContext, mExecutorService, mBitmapCache);
        }
    }

    public Handler getHandler() {
        return mHandler;
    }

    public Context getContext() {
        return mContext;
    }

    public ExecutorService getExecutorService() {
        return mExecutorService;
    }

    public BitmapCache getBitmapCache() {
        return mBitmapCache;
    }
}
