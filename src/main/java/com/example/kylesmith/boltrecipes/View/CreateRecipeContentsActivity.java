package com.example.kylesmith.boltrecipes.View;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.kylesmith.boltrecipes.R;

public class CreateRecipeContentsActivity extends AppCompatActivity {

    //View Component Vars
    Button btnAddredientsField;
    Button btnAddMethodField;

    LinearLayout linearLayoutChildIngredients;
    LinearLayout linearLayoutChildMethod;

    //Primitive Vars

    //Member Objects

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe_contents);

        InitViewComponents();
        AllocateVars();

        OnAddIngredientButtonPressed();

    }

    private void InitViewComponents(){

        btnAddredientsField = (Button) findViewById(R.id.btnAddIngredientsField);
        btnAddMethodField = (Button) findViewById(R.id.btnAddMethodField);

        linearLayoutChildIngredients = (LinearLayout) findViewById(R.id.linearLayoutChildingredients);
        linearLayoutChildMethod = (LinearLayout) findViewById(R.id.linearLayoutChildMethods);

    }

    private void AllocateVars(){}

    private void OnAddIngredientButtonPressed(){

        btnAddredientsField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.ingredients_field, null);
                // Add the new row before the add field button.
                linearLayoutChildIngredients.addView(rowView, linearLayoutChildIngredients.getChildCount() - 1);
            }
        });

    }


}
