package yuriitsap.example.com.custompicasso.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

/**
 * Created by yuriitsap on 28.04.15.
 */
public class BitmapWorkerTask implements Runnable {

    private Request mRequest;

    public BitmapWorkerTask(Request request) {
        mRequest = request;
    }

    @Override
    public void run() {
        Log.e("TAG", "BitmapWorkerTask Thread = " + Thread.currentThread().getName());
        if (mRequest.getObtainedBitmap() == null) {
            mRequest.setObtainedBitmap(getBitmapFromURL(mRequest.getRequestedUri()));
            mRequest.mPicassoUtil.getHandler().post(this);
            return;
        }
        mRequest.getTarget().setImageBitmap(mRequest.getObtainedBitmap());
    }

    public static Bitmap getBitmapFromURL(Uri uri) {
        try {

            URL url = new URL(uri.toString());
            Bitmap myBitmap = BitmapSizeDecoder
                    .decodeSampleBitmapStream(url.openStream(), 100, 100);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
