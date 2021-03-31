package com.example.firebase_image_loader.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.example.firebase_image_loader.utils.GlideApp;
import com.example.firebase_image_loader.utils.ProgressTarget;
import com.example.firebase_image_loader.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        List<StorageReference> imageRefs = Arrays.asList(

                storageRef.child("image5.jpeg"),
                storageRef.child("image6.jpg"),
                storageRef.child("image7.jpg")
        );
        mRecyclerView.setAdapter(new ProgressAdapter(imageRefs, index -> {
            //go to details screen
        }));



    }


    private  class ProgressViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private final ImageView image;
        private final TextView text;
        private final ProgressBar progress;
        /**
         * Cache target because all the views are tied to this view holder.
         */
        private final ProgressTarget<Bitmap> target;

        ProgressViewHolder(View root) {
            super(root);
            context = root.getContext();
            image = root.findViewById(R.id.image);
            text = root.findViewById(R.id.text);
            progress = root.findViewById(R.id.progress);
            target = new MyProgressTarget<>(context, new BitmapImageViewTarget(image), progress, image, text);
        }

        void bind(StorageReference imageRef) {
            target.setModel(imageRef); // update target's cache
            GlideApp.with(context)
                    .asBitmap()
                    .placeholder(R.drawable.glide_progress)
                    .load(imageRef)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(target)
            ;
        }
    }


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

    public class ProgressAdapter extends RecyclerView.Adapter<ProgressViewHolder> {
        private final List<StorageReference> models;
        private OnItemclickListner listner;

        public ProgressAdapter(List<StorageReference> models, OnItemclickListner _listner) {
            this.models = models;
            this.listner = _listner;
        }

        @Override
        public ProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_glide, parent, false);

            return new ProgressViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ProgressViewHolder holder, int position) {
            holder.bind(models.get(position));
            holder.itemView.setOnClickListener(view -> {
                this.listner.onItemClieked(position);
            });
        }

        @Override
        public int getItemCount() {
            return models.size();
        }


    }


    public interface OnItemclickListner
    {
        void onItemClieked(int index);
    }

}