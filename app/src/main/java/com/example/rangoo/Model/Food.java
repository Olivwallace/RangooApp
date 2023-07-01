package com.example.rangoo.Model;

public class Food {

    private String idFood;
    private String name;
    private String resume;
    private String description;
    private String imageUrl;

    public Food() {}

    public Food(String id, String name, String resume, String description, String imageUrl){
        setIdFood(id);
        setName(name);
        setResume(resume);
        setDescription(description);
        setImageUrl(imageUrl);
    }

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}

