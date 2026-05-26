package com.example.catalog_kazakov.datas;

import com.example.catalog_kazakov.domains.models.Category;

import java.util.ArrayList;

public class CategoryContext {
    public static ArrayList<Category> allCategory(){
        ArrayList<Category> categories = new ArrayList<>();

        categories.add(new Category(-1, "Все"));
        categories.add(new Category(0, "Мужское"));
        categories.add(new Category(1, "Женское"));
        categories.add(new Category(2, "Unisex"));

        return categories;
    }
}
