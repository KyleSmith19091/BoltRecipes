package com.example.kylesmith.boltrecipes.Controller;

import android.provider.BaseColumns;

public final class UserRecipeContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UserRecipeContract(){}


    //Static nested class: Users
    public static class UserEntry implements BaseColumns{
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }

    //Static nested class: Recipes
    public static class RecipeEntry implements BaseColumns{
        public static final String TABLE_NAME = "recipe";
        public static final String COLUMN_NAME_RECIPE_NAME = "name";
        public static final String COLUMN_NAME_RECIPE_TYPE = "type";
        public static final String COLUMN_NAME_RECIPE_INGREDIENTS = "ingredients";
        public static final String COLUMN_NAME_RECIPE_METHOD = "method";
        public static final String COLUMN_NAME_RECIPE_DESCRIPTION = "description";
        public static final String COLUMN_NAME_RECIPE_IMAGE_DATA = "image_data";
    }



}
