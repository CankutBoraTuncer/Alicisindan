package com.cankutboratuncer.alicisindan.activities.utilities;

public class Subcategory extends AllCategories{
    Category parentCategory;
    public Subcategory(String name, Category parentCategory, int image) {
        this.name = name;
        this.parentCategory = parentCategory;
        this.image = image;
    }
}