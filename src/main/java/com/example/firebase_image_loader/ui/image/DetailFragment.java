package com.example.firebase_image_loader.ui.image;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.firebase_image_loader.R;
import com.example.firebase_image_loader.utils.GlideApp;
import com.example.firebase_image_loader.utils.MyProgressTarget;
import com.example.firebase_image_loader.utils.ProgressTarget;
import com.google.firebase.storage.FirebaseStorage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {

    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.text)
    TextView text;

    @BindView(R.id.progress)
    ProgressBar progressBar;

//    private String imageUrl;
    private ProgressTarget<Bitmap> target;
    private ImageViewModel viewModel;
    public DetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        imageUrl = this.getArguments().getString("image");
        View view = inflater.inflate(R.layout.full_image, container, false);
        ButterKnife.bind(this, view);
        viewModel = new ViewModelProvider(requireActivity()).get(ImageViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        target = new MyProgressTarget<>(getActivity(), new BitmapImageViewTarget(image), progressBar, image, text);
        target.setModel(FirebaseStorage.getInstance().getReference().child(viewModel.getSelected().getMain()));

        GlideApp.with(getActivity())
                .asBitmap()
                .placeholder(R.drawable.glide_progress)
                .load(FirebaseStorage.getInstance().getReference().child(viewModel.getSelected().getMain()))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

                .into(target)
        ;

        super.onViewCreated(view, savedInstanceState);
    }

    private void goHome(){

        NavHostFragment.findNavController(this).navigate(R.id.action_Full_Image_to_ListImage);
    }
}
