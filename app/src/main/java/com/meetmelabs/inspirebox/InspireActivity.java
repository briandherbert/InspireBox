package com.meetmelabs.inspirebox;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.SupportActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Random;

/**
 * Created by bherbert on 3/8/17.
 */

public class InspireActivity extends SupportActivity {
    static final String TAG = InspireActivity.class.getSimpleName();

    static final int NUM_FILES = 10;

    ViewGroup[] mContainers = new ViewGroup[3];

    Random rand = new Random();

    public interface Supplier {
        void init(Context context);
        void inspire(ViewGroup container);
    }

    Supplier[] mSuppliers = {new ImageSupplier()};

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.base_layout);
        mContainers[0] = (ViewGroup) findViewById(R.id.container0);
        mContainers[1] = (ViewGroup) findViewById(R.id.container1);
        mContainers[2] = (ViewGroup) findViewById(R.id.container2);

        for (Supplier supplier : mSuppliers) {
            supplier.init(this);
        }


        findViewById(R.id.root_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextImage();
            }
        });
    }

    public void showNextImage() {
        for (ViewGroup vg : mContainers) {
            vg.removeAllViews();
            vg.setVisibility(View.GONE);
        }

        int numViews = rand.nextInt(mContainers.length) + 1;

        for (int i = 0; i < mContainers.length; i++) {
            ViewGroup vg = mContainers[i];
            vg.removeAllViews();
            if (i < numViews) {
                vg.setVisibility(View.VISIBLE);
                Supplier supplier = mSuppliers[rand.nextInt(mSuppliers.length)];
                supplier.inspire(vg);
                Log.v(TAG, "Location " + i + " inspired by " + supplier.getClass().getSimpleName());
            } else  {
                vg.setVisibility(View.GONE);
            }
        }
    }

    void deleteImages() {


    }
}
