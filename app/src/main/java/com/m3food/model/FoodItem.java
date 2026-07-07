package com.m3food.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Traditional Java POJO model representing a food item.
 * Leverages the Builder Pattern for high quality, robust object construction in Java.
 */
public class FoodItem {
    private final String id;
    private final String name;
    private final String nameMy;
    private final double price;
    private final double rating;
    private final String category;
    private final String imageUrl;
    private final String description;
    private final String descriptionMy;
    private final String preparationTime;
    private final List<String> ingredients;

    private FoodItem(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.nameMy = builder.nameMy;
        this.price = builder.price;
        this.rating = builder.rating;
        this.category = builder.category;
        this.imageUrl = builder.imageUrl;
        this.description = builder.description;
        this.descriptionMy = builder.descriptionMy;
        this.preparationTime = builder.preparationTime;
        this.ingredients = builder.ingredients;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getNameMy() { return nameMy; }
    public double getPrice() { return price; }
    public double getRating() { return rating; }
    public String getCategory() { return category; }
    public String getImageUrl() { return imageUrl; }
    public String getDescription() { return description; }
    public String getDescriptionMy() { return descriptionMy; }
    public String getPreparationTime() { return preparationTime; }
    public List<String> getIngredients() { return new ArrayList<>(ingredients); }

    // Builder Pattern
    public static class Builder {
        private String id;
        private String name;
        private String nameMy;
        private double price;
        private double rating;
        private String category;
        private String imageUrl;
        private String description;
        private String descriptionMy;
        private String preparationTime;
        private List<String> ingredients = new ArrayList<>();

        public Builder setId(String id) { this.id = id; return this; }
        public Builder setName(String name) { this.name = name; return this; }
        public Builder setNameMy(String nameMy) { this.nameMy = nameMy; return this; }
        public Builder setPrice(double price) { this.price = price; return this; }
        public Builder setRating(double rating) { this.rating = rating; return this; }
        public Builder setCategory(String category) { this.category = category; return this; }
        public Builder setImageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public Builder setDescription(String description) { this.description = description; return this; }
        public Builder setDescriptionMy(String descriptionMy) { this.descriptionMy = descriptionMy; return this; }
        public Builder setPreparationTime(String preparationTime) { this.preparationTime = preparationTime; return this; }
        public Builder setIngredients(List<String> ingredients) { this.ingredients = ingredients; return this; }

        public FoodItem build() {
            if (id == null || name == null) {
                throw new IllegalStateException("ID and Name are mandatory attributes");
            }
            return new FoodItem(this);
        }
    }
}