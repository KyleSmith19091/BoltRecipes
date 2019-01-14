package com.example.kylesmith.boltrecipes.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.kylesmith.boltrecipes.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreateRecipeBaseActivity extends AppCompatActivity {

    private static final String TAG = CreateRecipeBaseActivity.class.getSimpleName();

    //View Component Vars
    private Button btnToRecipeContentsActivity;
    private EditText etxtRecipeDescription;
    private CardView cvAddRecipeImage;
    private ImageView imgRecipeImage;
    private EditText etxtRecipeName;

    //Primitive Vars
    private static final int PICK_IMAGE = 1;

    //Member Objects
    InputStream SelectedImg;
    Bitmap BImgRecipe;


    //Called Once the Activity is visible to the user, and references appropriate xml file for activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_base_recipe);
        //Initialise View Components(Buttons, TextViews, ImageViews etc.)
        InitViewComponents();
        //Add OnClickListener for the CardView
        OnCardViewSelected();
        //Add OnClickListener for the Contents Button to create Intent for the next activity
        OnBtnToRecipeContentsActivityPressed();

    }

    //Executed once the startActivityForResult method has been executed
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Check if the user has returned from the gallery application
        //If so then then the request code will equal the constant PICK_IMAGE
        if(requestCode == PICK_IMAGE){
            //OpenInputStream method throws a fileNotFoundException
            try {
                 //Retrieve data from the Intent and decode it to an InputStream Object
                 SelectedImg = this.getContentResolver().openInputStream(data.getData());
                 //Use the BitmapFactory Object to decode the InputStream further to a bitmap
                 BImgRecipe = BitmapFactory.decodeStream(SelectedImg);
                 //Change the imageView to the retrieved image from the gallery application
                 imgRecipeImage.setImageBitmap(BImgRecipe);
            } catch (FileNotFoundException e) {
                Log.e(TAG, "" + e);
            }




        }

    }

    private void InitViewComponents(){

        btnToRecipeContentsActivity = (Button) findViewById(R.id.btnToRecipeContentsActivity);
        etxtRecipeDescription = (EditText) findViewById(R.id.etxtRecipeDescription);
        cvAddRecipeImage = (CardView) findViewById(R.id.cvAddRecipeImage);
        imgRecipeImage = (ImageView) findViewById(R.id.imgAddRecipeImage);
        etxtRecipeName = (EditText) findViewById(R.id.etxtRecipeName);

    }

    private void AllocateVars(){


    }

    //Create CardView Event Listener(Click == Touch) and open gallery application
    private void OnCardViewSelected(){
        //Add OnClickListener(responds to finger touch) for the card view, the listener is inheriting
        //from an Interface
        cvAddRecipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new empty Intent instance
                Intent intent = new Intent();
                //Defines what type of data the intent needs to carry
                intent.setType("image/*");
                //Sets what the intent needs to do -- needs to get content from a provider -- image in this case
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //Start the activity for Selecting an image which is the gallery
                //@params PICK_IMAGE is a constant value which is used to check whether the user
                //has returned from the gallery application
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

    }

    private void OnBtnToRecipeContentsActivityPressed(){

        btnToRecipeContentsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ToCreateRecipeContentsActivity = new Intent(getApplicationContext(), CreateRecipeContentsActivity.class);

                String[] ArrRecipeToNextActivity = new String[2];
                ArrRecipeToNextActivity[0] = etxtRecipeDescription.getText().toString();
                ArrRecipeToNextActivity[1] = etxtRecipeDescription.getText().toString();
                ToCreateRecipeContentsActivity.putExtra("RECIPE_DESCRIPTION", ArrRecipeToNextActivity);

                imgRecipeImage.buildDrawingCache();
                Bitmap bCachedImage = imgRecipeImage.getDrawingCache();

                ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
                bCachedImage.compress(Bitmap.CompressFormat.PNG, 200, BAOS);
                byte[] bArrImagData = BAOS.toByteArray();
                ToCreateRecipeContentsActivity.putExtra("IMAGE_BYTE_DATA", bArrImagData);

                //Go to specified activity in Params
                startActivity(ToCreateRecipeContentsActivity);


            }
        });

    }



}
