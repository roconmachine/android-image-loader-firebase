package com.example.firebase_image_loader.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.request.target.Target;

import java.util.Locale;

@SuppressLint("SetTextI18n") // text set only for debugging
public class MyProgressTarget<Z> extends ProgressTarget<Z> {
    public static final String TAG = "MyProgressTarget";

    private final TextView text;
    private final ProgressBar progress;
    private final ImageView image;

    public MyProgressTarget(Context context, Target<Z> target, ProgressBar progress, ImageView image, TextView text) {
        super(context, target);
        this.progress = progress;
        this.image = image;
        this.text = text;
    }

    @Override
    public float getGranualityPercentage() {
        return 0.1f; // this matches the format string for #text below
    }

    @Override
    protected void onConnecting() {
        Log.i(TAG, "onConnecting");
        progress.setIndeterminate(true);
        progress.setVisibility(View.VISIBLE);
        image.setImageLevel(0);
        text.setVisibility(View.VISIBLE);
        text.setText("connecting");
    }

    @Override
    protected void onDownloading(long bytesRead, long expectedLength) {
        Log.i(TAG, "onDownloading:this:" + this);
        progress.setIndeterminate(false);
        progress.setProgress((int) (100 * bytesRead / expectedLength));
        image.setImageLevel((int) (10000 * bytesRead / expectedLength));
        String percentage = "";
        text.setText(String.format(Locale.ROOT, "Downloading  ... %.1f%%",
                100f * bytesRead / expectedLength));
    }

    @Override
    protected void onDownloaded() {
        Log.i(TAG, "onDownloaded");
        progress.setIndeterminate(true);
        image.setImageLevel(10000);
        text.setText("decoding and transforming");
    }

    @Override
    protected void onDelivered() {
        Log.i(TAG, "onDelivered");
        progress.setVisibility(View.INVISIBLE);
        image.setImageLevel(0); // reset ImageView default
        text.setVisibility(View.INVISIBLE);
    }
}

