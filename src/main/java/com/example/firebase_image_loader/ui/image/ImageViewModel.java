package com.example.firebase_image_loader.ui.image;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.firebase_image_loader.model.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageViewModel extends ViewModel {

    private final MutableLiveData<Image> selected = new MutableLiveData<Image>();
    private List<Image> imageList;

    public ImageViewModel() {
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
    }
    public void select(int index) {selected.setValue(imageList.get(index));}
    public  Image getSelected() {return selected.getValue();}

    public List<Image> getData(){
        return imageList;
    }


}
