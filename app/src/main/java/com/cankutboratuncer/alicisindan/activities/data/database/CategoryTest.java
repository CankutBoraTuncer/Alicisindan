package com.cankutboratuncer.alicisindan.activities.data.database;

import android.util.Log;

import com.cankutboratuncer.alicisindan.activities.utilities.AllCategories;
import com.cankutboratuncer.alicisindan.activities.utilities.Category;
import com.cankutboratuncer.alicisindan.activities.utilities.Constants;
import com.cankutboratuncer.alicisindan.activities.utilities.Subcategory;

import java.util.ArrayList;
import java.util.Objects;

public class CategoryTest {

    public static ArrayList<AllCategories> categories = createCategories();
    public static ArrayList<AllCategories> subcategories;
    // subcategories arraylist stores the subcategories for each category index


    public static ArrayList<AllCategories> createCategories() {
        ArrayList<AllCategories> categoryArrayList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            categoryArrayList.add(new Category(Constants.categories.get(i), Constants.categoryImages.get(i)));
        }
        return categoryArrayList;
    }

    public static ArrayList<AllCategories> createSubCategories(String category) {
        ArrayList<AllCategories> subcategoryArrayList = new ArrayList<>();
        for (int i = 0; i < Constants.categories.size(); i++) {
            String categoryName = Constants.categories.get(i);
            ArrayList<String> subcategories = Constants.findSubCategory(category);
            if (categoryName.equals(category)) {
                Log.d("catname", category + " " + categoryName + " " + i);
                for (int j = 0; j < Objects.requireNonNull(subcategories).size(); j++) {
                    subcategoryArrayList.add(new Subcategory(subcategories.get(j), (Category) categories.get(i), Constants.categoryImages.get(i)));
                }
                break;
            }
        }
        return subcategoryArrayList;
    }

}