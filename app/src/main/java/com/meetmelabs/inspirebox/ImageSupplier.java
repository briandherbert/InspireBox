package com.meetmelabs.inspirebox;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by bherbert on 3/8/17.
 */

public class ImageSupplier implements InspireActivity.Supplier {
    static final String TAG = ImageSupplier.class.getSimpleName();

    static final String[] BITMAP_URLS = {"http://i.imgur.com/Xdl0Abtm.jpg",
            "http://i.imgur.com/tIdfSVgm.jpg",
            "http://i.imgur.com/zOZtpYum.jpg"};

    static final String FILENAME = "file";

    static int currentIdx = 0;

    @Override
    public void init(Context context) {
        downloadImages(context, BITMAP_URLS);
    }

    @Override
    public void inspire(ViewGroup container) {
        Context context = container.getContext();
        ImageView img = new ImageView(context);
        container.addView(img);
        //img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        currentIdx = (currentIdx + 1) % BITMAP_URLS.length;
        Log.v(TAG, "showing img " + currentIdx);

        String dir = context.getFilesDir().getAbsolutePath();
        File f0 = new File(dir, FILENAME + currentIdx);
        Ion.with(context).load(f0).intoImageView(img);
    }

    void downloadImages(final Context context, String[] urls) {
        for (int i = 0; i < urls.length; i++) {
            final int idx = i;
            Ion.with(context).load(urls[idx]).withBitmap().asBitmap()
                    .setCallback(new FutureCallback<Bitmap>() {
                        @Override
                        public void onCompleted(Exception e, Bitmap result) {
                            //mImg.setImageBitmap(result);
                            String filename = FILENAME + idx;

                            try {
                                FileOutputStream fOut = context.openFileOutput(filename, Context.MODE_PRIVATE);
                                result.compress(Bitmap.CompressFormat.PNG, 80, fOut);
                                fOut.close();
                                Log.v(TAG, "Saved image " + idx + " as " + filename);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
        }
    }
}
