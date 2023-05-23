package com.cankutboratuncer.alicisindan.activities.ui.main.profile.database;

import com.cankutboratuncer.alicisindan.R;
import com.cankutboratuncer.alicisindan.activities.utilities.Category;

import java.util.ArrayList;

public class CategoryTest {
    public static ArrayList<Category> categories = createSampleCategories();

    public static ArrayList<Category> createSampleCategories() {
        ArrayList<Category> categoryArrayList = new ArrayList<>();
        categoryArrayList.add(new Category("Cars", R.drawable.product_image));
        categoryArrayList.add(new Category("Houses", R.drawable.product_image));
        categoryArrayList.add(new Category("Phones", R.drawable.product_image));
        categoryArrayList.add(new Category("Tablets", R.drawable.product_image));
        categoryArrayList.add(new Category("Home Appliances", R.drawable.product_image));
        categoryArrayList.add(new Category("Laptops", R.drawable.product_image));
        categoryArrayList.add(new Category("Books", R.drawable.product_image));
        categoryArrayList.add(new Category("Other", R.drawable.product_image));
        return categoryArrayList;
    }
}