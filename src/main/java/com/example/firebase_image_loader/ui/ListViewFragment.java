package com.example.firebase_image_loader.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_image_loader.R;
import com.example.firebase_image_loader.model.Image;
import com.example.firebase_image_loader.ui.adapter.ProgressAdapter;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListViewFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private List<Image> imageList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageList = new ArrayList<>(0);
        imageList.add(new Image("image1.jpeg", "full1.jpg"));
        imageList.add(new Image("image2.jpeg", "full2.jpg"));
        imageList.add(new Image("image3.jpeg", "full3.jpg"));
        imageList.add(new Image("image5.jpeg", "full4.jpg"));
        imageList.add(new Image("image6.jpg", "full5.jpg"));
        imageList.add(new Image("image7.jpg", "full6.jpg"));
        imageList.add(new Image("image5.jpeg", "full8.jpg"));
        imageList.add(new Image("image6.jpg", "full9.jpg"));
        imageList.add(new Image("image7.jpg", "full10.jpg"));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new ProgressAdapter(imageList, index -> {
            //go to details screen
            Bundle result = new Bundle();
            result.putString("image", imageList.get(index).getMain());


            NavHostFragment.findNavController(this).navigate(R.id.action_ListImage_to_Full_Image, result);

        }));



    }


}
