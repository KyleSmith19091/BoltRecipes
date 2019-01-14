package com.example.kylesmith.boltrecipes.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kylesmith.boltrecipes.Controller.DBHandler;
import com.example.kylesmith.boltrecipes.Controller.PasswordHandler;
import com.example.kylesmith.boltrecipes.Model.User;
import com.example.kylesmith.boltrecipes.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    //TAG used for debugging in conjunction with the Log statement
    private static final String TAG = LoginActivity.class.getSimpleName();

    //View Component Vars
    private EditText etxtUsernameEntry;
    private EditText etxtPasswordEntry;

    private Button btnLogin;

    private TextView linkSignUp;

    private ProgressDialog progressDialog;

    //Primitive Vars
    private String sUsername;
    private String sPassword;

    //Member Objects
    DBHandler db;
    PasswordHandler passwordHandler;


    ////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Entry point for this activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AllocateVars();
        InitViewComponents();
        db.InsertUser(new User("admin", "password"));
        OnLoginButtonPressed();

    }
    ////////////////////////////////////////////////////

    ////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }
    ////////////////////////////////////////////////////

    ////////////////////////////////////////////////////
    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
        Log.i(TAG, "Activity Destroyed");
    }
    ////////////////////////////////////////////////////

    private void AllocateVars(){

        db = new DBHandler(this);
        passwordHandler = new PasswordHandler();

    }

    private void InitViewComponents(){

        //Edit Texts
        etxtUsernameEntry = (EditText) findViewById(R.id.etxt_username_entry);
        etxtPasswordEntry = (EditText) findViewById(R.id.etxt_password_entry);

        //Buttons
        btnLogin = (Button) findViewById(R.id.btn_login);

        //Links
        linkSignUp = (TextView) findViewById(R.id.link_signup);

        //Progress dialog
        progressDialog = new ProgressDialog(LoginActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
    }

    private boolean OnLoginButtonPressed(){

         boolean bResult = false;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sUsername = etxtUsernameEntry.getText().toString().trim();
                String sPassword = etxtPasswordEntry.getText().toString().trim();

                if (Valid()) {
                    SetProgressDialogAtrribs();
                 boolean bUsernameFound = false;
                 int iUsernameIndex = 0;
                    ArrayList<User> arrlUsers = db.GetUserData();
                    for (int i = 0; i < arrlUsers.size(); i++) {
                        if(sUsername.equals(arrlUsers.get(i).getsUsername())){
                            //Entered username has been found in the database change boolean to true
                            bUsernameFound = true;
                            //Save the index where the entered username is in the arraylist
                            iUsernameIndex = i;
                            //Once found exit the loop
                            break;
                        }
                    }

                        if (bUsernameFound) {

                            Log.i(TAG, "Username Found checking Password");

                            if (sPassword.equals(passwordHandler.DecodePasswordBase64(arrlUsers.get(iUsernameIndex).getsPassword()))) {

                                Log.i(TAG, "Password Check Done");
                                //Username and Password Correct move onto the next activity
                                Intent toRecipeListView = new Intent(getApplicationContext(), UsersRecipeActivity.class);
                                //Send the username of the entered username to next activity to display relevant data for that user
                                Log.i(TAG,arrlUsers.get(iUsernameIndex).getsUsername());
                                toRecipeListView.putExtra("USERNAME", arrlUsers.get(iUsernameIndex).getsUsername());
                                startActivity(toRecipeListView);
                            } else {

                                Log.i(TAG, "Incorrect Credentials -- Password");

                            }

                        } else if(bUsernameFound != true){
                            Log.i(TAG, "Incorrect Credentials -- Username");
                        }


                    }

                }

        });

        return bResult;

    }

    //Check that the entered data in the edittext fields is valid
    private boolean Valid() {

        boolean valid = true;

        //Set the values entered in the edittext fields to the appropriate variables
        sUsername = etxtUsernameEntry.getText().toString();
        sPassword = etxtPasswordEntry.getText().toString();

        if (sUsername.isEmpty()) {

            etxtUsernameEntry.setError("Enter Valid Email");
            valid = false;

        }

        if (sPassword.isEmpty() || sPassword.length() < 4 || sPassword.length() > 10) {
            etxtPasswordEntry.setError("between 4 and 10 alphanumeric characters");
            valid = false;

        }

        return valid;

    }

    private void SetProgressDialogAtrribs(){

        //Shows a loading circle animation
        progressDialog.setIndeterminate(true);

        progressDialog.setIcon(R.mipmap.ic_app_launcher);
        progressDialog.setMessage("Authenticating...");

        progressDialog.show();

        Log.i(TAG, "Showing Progressdialog");

    }

}
