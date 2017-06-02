package com.selwyn.ciaran.cookbook;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Ciaran on 20/02/2017.
 */
public class ImageEncoder {

    public static String encode(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int quality = 100; //100: compress nothing
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);

        if(bitmap != null && !bitmap.isRecycled()){//important! prevent out of memory
            bitmap.recycle();
            bitmap = null;
        }

        byte [] ba = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(ba, Base64.DEFAULT);
        return encodedImage;
    }
}
