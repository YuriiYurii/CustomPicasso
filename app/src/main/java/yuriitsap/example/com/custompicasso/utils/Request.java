package yuriitsap.example.com.custompicasso.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by yuriitsap on 27.04.15.
 */
public class Request {

    static final int DEFAULT_HEIGHT = 100;
    static final int DEFAULT_WIDTH = 100;

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
        mPlaceholder = mPicassoUtil.mContext.getResources().getDrawable(placeholderId);
        return this;
    }

    public void into(ImageView imageView) {
        mImageView = imageView;
        Message message = new Message();
        message.obj = this;
        if (mPlaceholder != null) {
            message.what = PicassoUtil.INTO_PLACEHOLDER;
            mPicassoUtil.HANDLER.sendMessage(message);
        }
        if ((mObtainedBitmap=mPicassoUtil.mBitmapCache.getBitmapFromMemoryCache(String.valueOf(mResId))) != null) {
            Log.e("TAG", "From Cache");
            message.what = PicassoUtil.INTO_BITMAP;
            mPicassoUtil.HANDLER.sendMessage(message);
        } else {
            mPicassoUtil.mExecutorService.execute(new BitmapWorkerTask(this));
        }
    }

    ImageView getImageView() {
        return mImageView;
    }

    Drawable getPlaceholder() {
        return mPlaceholder;
    }

    Bitmap getObtainedBitmap() {
        return mObtainedBitmap;
    }

    void setObtainedBitmap(Bitmap obtainedBitmap) {
        mPicassoUtil.mBitmapCache.addBitmapToMemoryCache(String.valueOf(mResId), obtainedBitmap);
        mObtainedBitmap = obtainedBitmap;
    }
}
