package com.example.musiccloud.Async;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    CollapsingToolbarLayout bmImage;
    Context context;

    public DownloadImageTask(Context context, CollapsingToolbarLayout bmImage) {
        this.context = context;
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        System.out.println("Link:" + urldisplay);
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        Log.e("OK", "Chay roi");
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), result);
        bmImage.setBackground(bitmapDrawable);
    }
}
