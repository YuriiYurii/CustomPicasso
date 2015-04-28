package yuriitsap.example.com.custompicasso.utils;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Message;
import android.widget.ImageView;

/**
 * Created by yuriitsap on 27.04.15.
 */
public class Request {

    private Uri mUri;
    private int mResId;
    private Drawable mPlaceholder;
    private ImageView mImageView;
    private PicassoUtil mPicassoUtil;

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
        if (mPlaceholder != null) {
            Message message = new Message();
            message.what = PicassoUtil.INTO_PLACEHOLDER;
            message.obj = this;
            mPicassoUtil.HANDLER.sendMessage(message);
        }
    }

    ImageView getImageView() {
        return mImageView;
    }

    Drawable getPlaceholder() {
        return mPlaceholder;
    }

}
