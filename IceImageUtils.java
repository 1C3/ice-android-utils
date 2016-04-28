/**
 * Created by riccardo on 4/28/16.
 */

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class IceImageUtils {

    public static Bitmap bitmapLoad( Resource res, int resId, int width, int height ) {

        // calc scale, load appropriately downsampled bitmap from given resource

        Bitmap rawBitmap, resWidth, resHeight = resLoad( res, resId, width, height );

        // compare aspect ratio and crop

        rawBitmap = bitmapCrop( rawBitmap, width, height, resWidth, resHeight );

        // scale to desired size

        return Bitmap.createScaledBitmap( rawBitmap, width, height, true );
    }

    // load downsampled bitmap from resource

    private static Bitmap resLoad( Resource res, int resId, int width, int height ) {

        BitmapFactory.Options resOptions = new BitmapFactory.Options();
        resOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource( res, resId, resOptions );

        int resHeight = resOptions.outHeight;
        int resWidth = resOptions.outWidth;

        float xScale = (float) width / (float) resWidth;
        float yScale = (float) height / (float) resHeight;
        float scale = Math.max( xScale, yScale );

        resOptions.inSampleSize = sampleSize( scale );
        resWidth /= resOptions.inSampleSize;
        resHeight /= resOptions.inSampleSize;
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource( res, resId, options ), resWidth, resHeight;
    }

    // calc samplesize for scaled resource loading

    private static int sampleSize( float scale ) {

        int size = 1;

        while( scale < 0.5f ) {

            size *= 2;
            scale *= 2;
        }

        return size;
    }

    // crop bitmap to chosen aspect ratio

    private static Bitmap bitmapCrop( Bitmap rawBitmap, int width, int height, int resWidth, int resHeight ) {

        float xScale = (float) width / (float) resWidth;
        float yScale = (float) height / (float) resHeight;
        float scale = Math.max( xScale, yScale );

        if( xScale > yScale ) {
            int cropWidth = (int) Math.round( resWidth );
            int cropX = 0;
            int cropHeight = (int) Math.round( height / scale );
            int cropY = ( resHeight - cropHeight ) / 2;
        }
        else {
            int cropWidth = (int) Math.round( width / scale );
            int cropX = ( resWidth - cropWidth ) / 2;
            int cropHeight = (int) Math.round( resHeight );
            int cropY = 0;
        }

        return Bitmap.createBitmap( rawBitmap, cropX, cropY, cropWidth, cropHeight );
    }
}
