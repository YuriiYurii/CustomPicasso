package yuriitsap.example.com.custompicasso.utils;

import android.graphics.Bitmap;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by yuriitsap on 28.04.15.
 */
public class BitmapWorkerTask implements Runnable {

    private WeakReference<Request> mRequest;

    public BitmapWorkerTask(Request request) {
        mRequest = new WeakReference<>(request);
    }

    @Override
    public void run() {
        Request request = mRequest.get();
        request.setObtainedBitmap(BitmapSizeDecoder
                .decodeSampleBitmapFromResource(request.mPicassoUtil.mContext.getResources(),
                        request.mResId, request.DEFAULT_WIDTH, request.DEFAULT_HEIGHT));
        Message message = new Message();
        message.what = PicassoUtil.INTO_BITMAP;
        message.obj = request;
        request.mPicassoUtil.HANDLER.sendMessage(message);
        mRequest.clear();
    }
}
