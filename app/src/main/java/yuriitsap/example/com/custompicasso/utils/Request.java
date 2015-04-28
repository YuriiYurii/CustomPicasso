package yuriitsap.example.com.custompicasso.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by yuriitsap on 27.04.15.
 */
public class Request {

    private Uri mUri;
    private Drawable mPlaceholder;
    private ImageView mImageView;
    private Bitmap mObtainedBitmap;
    int mResId;
    PicassoUtil mPicassoUtil;

    public Request(Uri uri, int resId,
            PicassoUtil picassoUtil) {
        mUri = uri;
        mResId = resId;
        mPicassoUtil = picassoUtil;
    }

    public Request placeholder(int placeholderId) {
        mPlaceholder = mPicassoUtil.getContext().getResources().getDrawable(placeholderId);
        return this;
    }

    public void into(ImageView imageView) {
        mImageView = imageView;
        if (mPlaceholder != null) {
            imageView.setImageDrawable(mPlaceholder);
        }
        if (mResId != 0) {
            imageView
                    .setImageDrawable(mPicassoUtil.getContext().getResources().getDrawable(mResId));
        }
        if (isInCache()) {
            mPicassoUtil.getHandler().post(new Response(mImageView,
                    mPicassoUtil.getBitmapCache().getBitmapFromMemoryCache(mUri.toString())));
        } else {
            mPicassoUtil.getExecutorService().execute(new BitmapWorkerTask(this));
        }


    }

    private boolean isInCache() {
        return mPicassoUtil.getBitmapCache().containsKey(mUri.toString());
    }

    public Uri getRequestedUri() {
        return mUri;
    }

    public ImageView getTarget() {
        return mImageView;
    }

    public void setObtainedBitmap(Bitmap bitmap) {
        mObtainedBitmap = bitmap;
    }

    public Bitmap getObtainedBitmap() {
        return mObtainedBitmap;
    }
}
