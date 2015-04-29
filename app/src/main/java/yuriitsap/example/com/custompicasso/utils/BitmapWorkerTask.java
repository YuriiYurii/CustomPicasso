package yuriitsap.example.com.custompicasso.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

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
        if (mRequest.getObtainedBitmap() == null) {
            mRequest.setObtainedBitmap(getBitmapFromURL(mRequest.getRequestedUri()));
            mRequest.getPicassoUtil().getHandler().post(this);
            return;
        }
        mRequest.getTarget().setImageBitmap(mRequest.getObtainedBitmap());
    }

    public Bitmap getBitmapFromURL(Uri uri) {
        try {

            URL url = new URL(uri.toString());
            Bitmap myBitmap = BitmapFactory.decodeStream(url.openStream());
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
