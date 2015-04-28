package yuriitsap.example.com.custompicasso.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by ytsap on 13.10.2014.
 */
public class BitmapSizeDecoder {


    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
            int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqHeight) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize > reqHeight) && (halfWidth / inSampleSize
                    > reqWidth)) {
                inSampleSize *= 2;
            }


        }
        return inSampleSize;

    }

    public static Bitmap decodeObtainedBitmap(Resources resources, int resId, int reqWidth,
            int reqHeight) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqHeight, reqWidth);
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(resources, resId, options);
        return Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, true);
    }

    public static Bitmap decodeFromFile(String path, int reqHeight, int reqWidth) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;

    }

    public static Bitmap decodeSampleBitmapFromResource(Resources resource, int resId, int reqWidth,
            int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resource, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqHeight, reqWidth);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resource, resId, options);
    }


}
