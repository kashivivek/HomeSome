package com.avsoftsol.homesome;


public class Data_Model {

    // Getter and Setter model for recycler view items
    private String title;
    private int image;
    private String subtitle;

    public Data_Model(String title, int image, String subtitle) {

        this.title = title;

        this.subtitle = subtitle;

        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getImage() {
        return image;
    }
}
