package yuriitsap.example.com.custompicasso.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by yuriitsap on 28.04.15.
 */
public class Response implements Runnable {

    public Response(ImageView target, Bitmap bitmap) {
        mTarget = target;
        mBitmap = bitmap;
    }

    private ImageView mTarget;
    private Bitmap mBitmap;

    @Override
    public void run() {
        mTarget.setImageBitmap(mBitmap);
    }
}
