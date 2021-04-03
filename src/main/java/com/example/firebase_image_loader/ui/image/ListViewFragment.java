package com.example.firebase_image_loader.ui.image;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_image_loader.R;
import com.example.firebase_image_loader.model.Image;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListViewFragment extends Fragment implements ImageViewModelInterface{

    private ImageViewModel viewModel;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private List<Image> imageList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_list, container, false);
        ButterKnife.bind(this, view);
        viewModel = new ViewModelProvider(requireActivity()).get(ImageViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageList = viewModel.getData();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new ProgressAdapter(imageList, index -> {
            viewModel.select(index);
            NavHostFragment.findNavController(this).navigate(R.id.action_ListImage_to_Full_Image);

        }));

    }


}
