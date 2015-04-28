package yuriitsap.example.com.custompicasso.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yuriitsap on 27.04.15.
 */
public class PicassoUtil {

    static final Handler HANDLER = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INTO_PLACEHOLDER:
                    Request request = (Request) msg.obj;
                    request.getImageView().setImageDrawable(request.getPlaceholder());
                    break;
                case INTO_BITMAP:
                    break;
            }
        }
    };
    static final int INTO_PLACEHOLDER = 1;
    static final int INTO_BITMAP = 2;

    private static volatile PicassoUtil instance;
    Context mContext;
    ExecutorService mExecutorService;
    BitmapCache mBitmapCache;

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
        return null;
    }

    public Request load(Uri uri) {
        return null;
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


}
