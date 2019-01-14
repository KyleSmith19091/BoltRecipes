package com.example.kylesmith.boltrecipes.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kylesmith.boltrecipes.Model.Recipe;
import com.example.kylesmith.boltrecipes.R;

import java.util.ArrayList;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    //@params @NonNull --> parsed value can not be null
    public RecipeAdapter(Context context, ArrayList<Recipe> users) {
        super(context, 0, users);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Get Data from specified position and populate object
        Recipe recipe = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_recipe_view_adapter, parent, false);
        }

        // Lookup view for data population
        TextView txtRecipeName = (TextView) convertView.findViewById(R.id.txtRecipeName);
        TextView txtRecipeType = (TextView) convertView.findViewById(R.id.txtRecipeType);
        // Populate the data into the template view using the data object
        txtRecipeName.setText(recipe.getsRecipeName());
        txtRecipeType.setText(recipe.getsRecipeType());
        // Return the completed view to render on screen
        return convertView;

    }
}
