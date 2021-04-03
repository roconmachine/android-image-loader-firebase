package com.example.firebase_image_loader.ui.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.firebase_image_loader.R;
import com.example.firebase_image_loader.model.Image;
import com.example.firebase_image_loader.ui.MainActivity;
import com.example.firebase_image_loader.utils.GlideApp;
import com.example.firebase_image_loader.utils.MyProgressTarget;
import com.example.firebase_image_loader.utils.ProgressTarget;
import com.google.firebase.storage.FirebaseStorage;


import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder> {
    private final List<Image> models;
    private MainActivity.OnItemclickListner listner;

    public ProgressAdapter(List<Image> models, MainActivity.OnItemclickListner _listner) {
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


    class ProgressViewHolder extends RecyclerView.ViewHolder {
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

        void bind(Image image) {

            target.setModel(FirebaseStorage.getInstance().getReference().child(image.getThumb())); // update target's cache
            GlideApp.with(context)
                    .asBitmap()
                    .placeholder(R.drawable.glide_progress)
                    .load(FirebaseStorage.getInstance().getReference().child(image.getThumb()))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .centerCrop()
                    .into(target)
            ;
        }
    }


}