package com.shoker.fightersgym;

public class Food {

    private String name;
    private String image_url;
    public int calories;

    public void setName(String name) {
        this.name = name;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setCalories(String calories) {
        float f =Float.parseFloat(calories);
        this.calories = Math.round(f);
    }

    public String getName() {
        return name;
    }

    public String getImage_url() {
        return image_url;
    }

    public int getCalories() {
        return calories;
    }
}
