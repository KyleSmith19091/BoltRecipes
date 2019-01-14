package com.example.kylesmith.boltrecipes.Model;

public class Recipe {

    //Vars
    private String sRecipeName;
    private String sRecipeType;
    private String sIngredients;
    private String sMethod;

    public Recipe(String sRecipeName, String inSRecipeType, String inIngredients, String inMethod) {
        this.sRecipeName = sRecipeName;
        this.sRecipeType = inSRecipeType;
        this.sIngredients = inIngredients;
        this.sMethod = inMethod;
    }

    public String getsRecipeName() {
        return sRecipeName;
    }

    public void setsRecipeName(String sRecipeName) {
        this.sRecipeName = sRecipeName;
    }

    public String getsRecipeType() {
        return sRecipeType;
    }

    public void setsRecipeType(String sRecipeType) {
        this.sRecipeType = sRecipeType;
    }

    public String getsIngredients() {
        return sIngredients;
    }

    public void setsIngredients(String sIngredients) {
        this.sIngredients = sIngredients;
    }

    public String getsMethod() {
        return sMethod;
    }

    public void setsMethod(String sMethod) {
        this.sMethod = sMethod;
    }
}
