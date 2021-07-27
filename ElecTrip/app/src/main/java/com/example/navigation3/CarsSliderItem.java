package com.example.navigation3;

import android.content.Context;
import android.graphics.Bitmap;

public class CarsSliderItem {
    private Bitmap image;

    public CarsSliderItem(String url, Context context) {
        UrlToBitmap urlToBitmap = new UrlToBitmap();
        image = urlToBitmap.getImage(url, context);
    }

    public Bitmap getImage() {
        return image;
    }
}
