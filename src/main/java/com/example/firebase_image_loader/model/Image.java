package com.example.firebase_image_loader.model;

public class Image {
    private String thumb;
    private String main;

    public Image(String thumb, String main) {
        this.thumb = thumb;
        this.main = main;
    }

    public Image() {
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
