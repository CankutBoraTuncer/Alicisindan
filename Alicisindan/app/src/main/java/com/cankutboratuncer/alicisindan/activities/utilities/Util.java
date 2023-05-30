package com.cankutboratuncer.alicisindan.activities.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import com.cankutboratuncer.alicisindan.R;

import java.io.ByteArrayOutputStream;

public class Util {

    public static String encodeImage(Bitmap bitmap) {
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String drawableToString(Resources resources, int img){
        Bitmap icon = ((BitmapDrawable) ResourcesCompat.getDrawable(resources, img , null)).getBitmap();
        return Util.encodeImage(icon);
    }

    public static Bitmap decodeImage(String encodedImage) {
        try {
            byte[] imageBytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getBitmapFromEncodedString(String encodedImage) {
        if (encodedImage != null) {
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            return null;
        }
    }


}
