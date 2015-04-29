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
    private int mResId;
    private PicassoUtil mPicassoUtil;

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
        setupPlaceholder();
        if (isInCache() && !fromResource()) {
            mImageView.setImageBitmap(
                    mPicassoUtil.getBitmapCache().getBitmapFromMemoryCache(mUri.toString()));
        } else {
            mPicassoUtil.getExecutorService().execute(new BitmapWorkerTask(this));
        }
    }

    private boolean fromResource() {
        if (mResId != 0) {
            mImageView
                    .setImageDrawable(mPicassoUtil.getContext().getResources().getDrawable(mResId));
            return true;
        }
        return false;
    }

    private boolean isInCache() {
        return mPicassoUtil.getBitmapCache().containsKey(mUri.toString());
    }

    private void setupPlaceholder() {
        if (mPlaceholder != null) {
            mImageView.setImageDrawable(mPlaceholder);
        }
    }

    public Uri getRequestedUri() {
        return mUri;
    }

    public ImageView getTarget() {
        return mImageView;
    }

    public void setObtainedBitmap(Bitmap bitmap) {
        mPicassoUtil.getBitmapCache().addBitmapToMemoryCache(mUri.toString(), bitmap);
        mObtainedBitmap = bitmap;
    }

    public Bitmap getObtainedBitmap() {
        return mObtainedBitmap;
    }

    public PicassoUtil getPicassoUtil() {
        return mPicassoUtil;
    }
}
