package com.example.kylesmith.boltrecipes.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.example.kylesmith.boltrecipes.Model.Recipe;
import com.example.kylesmith.boltrecipes.Model.User;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper{

    //TAG for debugging in the log terminal
    private static final String TAG = DBHandler.class.getSimpleName();

    // <!-- ... -->
    //Database Attribs
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_FILE_NAME = "user.db";

    // <!-- ... -->
    //User Table Attribs From UserRecipeContract class
    private static final String TABLE_USER_NAME = UserRecipeContract.UserEntry.TABLE_NAME;
    private static final String COL_USERNAME = UserRecipeContract.UserEntry.COLUMN_NAME_USERNAME;
    private static final String COL_PASSWORD = UserRecipeContract.UserEntry.COLUMN_NAME_PASSWORD;

    //Recipe Table Attribs From UserRecipeContract class
    private static final String TABLE_RECIPE_NAME = UserRecipeContract.RecipeEntry.TABLE_NAME;
    private static final String COL_RECIPE_NAME =  UserRecipeContract.RecipeEntry.COLUMN_NAME_RECIPE_NAME;
    private static final String COL_RECIPE_TYPE = UserRecipeContract.RecipeEntry.COLUMN_NAME_RECIPE_TYPE;
    private static final String COL_RECIPE_INGREDIENTS = UserRecipeContract.RecipeEntry.COLUMN_NAME_RECIPE_INGREDIENTS;
    private static final String COL_RECIPE_METHOD = UserRecipeContract.RecipeEntry.COLUMN_NAME_RECIPE_METHOD;
    private static final String COL_RECIPE_DESCRIPTION = UserRecipeContract.RecipeEntry.COLUMN_NAME_RECIPE_IMAGE_DATA;
    private static final String COL_RECIPE_IMAGE_DATA = UserRecipeContract.RecipeEntry.COLUMN_NAME_RECIPE_IMAGE_DATA;

    // <!-- ... -->
    //Create the user table query
    private static final String SQL_QUERY_CREATE_USER_TABLE =
            "CREATE TABLE " + TABLE_USER_NAME + " (" +
                    UserRecipeContract.UserEntry._ID + " INTEGER PRIMARY KEY," +
                    COL_USERNAME + " TEXT," +
                    COL_PASSWORD + " TEXT)";

    //Create the Recipe Table query
    private static final String SQL_QUERY_CREATE_RECIPE_TABLE = "CREATE TABLE " + TABLE_RECIPE_NAME + " (" +
            UserRecipeContract.RecipeEntry._ID + " INTEGER PRIMARY KEY," +
            COL_RECIPE_NAME + " TEXT," +
            COL_RECIPE_TYPE + " TEXT," +
            COL_RECIPE_INGREDIENTS + " TEXT," +
            COL_RECIPE_METHOD + " TEXT)";

    // <!-- ... -->
    //See if the user table exists query
    private static final String SQL_QUERY_CHECK_IF_USER_TABLE_EXIST =
            "DROP TABLE IF EXISTS " + TABLE_USER_NAME;
    //See if the recipe table exists query
    private static final String SQL_QUERY_CHECK_IF_RECIPE_TABLE_EXISTS = "DROP TABLE IF EXISTS " + TABLE_RECIPE_NAME;

    // <!-- ... -->
    //The context of current state of the application
    private Context CurrContext;
    //Password Encoder and Decoder Class
    private PasswordHandler PasswordHandler = new PasswordHandler();

    // <!-- ... -->
    //Arraylist for the user data from the database
    private ArrayList<User> ArrlUsers;
    //Arraylist for the recipe data from the database
    private ArrayList<Recipe> ArrlRecipes;

    // <!-- ... -->
    //Overloaded Constructor
    public DBHandler(Context inContext) {
        super(inContext, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        CurrContext = inContext;
    }



    // <!-- ... -->
    @Override
    public void onCreate(SQLiteDatabase db) {
       //Called on the creation of the database, called once
        db.execSQL(SQL_QUERY_CREATE_USER_TABLE);
        Log.i(TAG, "TABLE USER CREATED!");

        // <!-- ... -->
        db.execSQL(SQL_QUERY_CREATE_RECIPE_TABLE);
        Log.i(TAG, "TABLE RECIPE CREATED");

        //Whenever a new database is created add a dummy account for testing
        InsertUser(new User("admin", "password"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //On upgrade drop older tables
            //Check if the user table exists
            db.execSQL(SQL_QUERY_CHECK_IF_USER_TABLE_EXIST);
            //Check if the recipe table exists
            db.execSQL(SQL_QUERY_CHECK_IF_RECIPE_TABLE_EXISTS);

            //Call the onCreate Method again to reconstruct the database
            onCreate(db);
            Log.i(TAG, "TABLES UPDATED");

    }

    //Method for the AsyncTask to add an user
    public void InsertUser(User inUser){

        new NInsertUserIntoDBTask().execute(inUser);

    }

    //Method for the AsyncTask to delete an user
    public void DeleteUser(String inTest){

        new NDeleteUserFromDBTask().execute(inTest);

    }

    //Method for the AsyncTask to retrieve all user data from the database
    public ArrayList<User> GetUserData(){

        new NGetUserDataTask().doInBackground();
        return ArrlUsers;

    }

    //Method for the AsyncTask to Insert a recipe into the database
    public void InsertRecipe(Recipe inRecipe){

        new NInsertRecipeIntoDBTask().execute(inRecipe);

    }

    //Method for the AyncTask to retrieve all recipe data from the database
    public ArrayList<Recipe> GetRecipeData(){

        new NGetRecipeDataTask().doInBackground();
        return ArrlRecipes;

    }

    // <!-- ... -->
    private class NInsertUserIntoDBTask extends AsyncTask<User, Void, Void>{

        @Override
        protected Void doInBackground(User... users) {

            DBHandler dbHelper = new DBHandler(CurrContext);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COL_USERNAME, users[0].getsUsername());

            String sEncodedPassword = PasswordHandler.EncodePasswordBase64(users[0].getsPassword());
            values.put(COL_PASSWORD, sEncodedPassword);

            boolean bResult = true;

            try {
              db.insert(TABLE_USER_NAME, null, values);
            }catch (SQLException e){
                Log.e(TAG, "AN EXCEPTION HAS OCCURRED: " + e);
                bResult = false;
            }

            if (bResult == true) Log.i(TAG, "User Account has been created!");

            values.clear();

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i(TAG, "Async Task Insert User has been executed");

        }
    }

    private class NDeleteUserFromDBTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... inUsers) {

            DBHandler dbHandler = new DBHandler(CurrContext);
            SQLiteDatabase db = dbHandler.getWritableDatabase();

            boolean bResult = true;

            try {
                db.delete(TABLE_USER_NAME, "Name = ?", new String[]{inUsers[0]});
            }catch (SQLException e){
                Log.e(TAG, "AN EXCEPTION HAS OCCURRED: " + e);
                bResult = false;
            }

            if(bResult == true) Log.i(TAG, "User Account has been Deleted");

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i(TAG, "Async Task Delete User has been executed");
        }
    }

    private class NGetUserDataTask extends AsyncTask<Void, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(Void... voids) {

            DBHandler dbHandler = new DBHandler(CurrContext);

            SQLiteDatabase db = dbHandler.getReadableDatabase();

            String sQuery = "SELECT * FROM " + TABLE_USER_NAME + ";";

            Cursor res = db.rawQuery(sQuery, null);

            ArrayList<User> arrlData = new ArrayList<>();

            while(res.moveToNext()){

                arrlData.add(new User(res.getString(1), res.getString(2)));

                Log.i(TAG, res.getString(1));

            }

            ArrlUsers = arrlData;

            res.close();

            Log.i(TAG, "User Data Retrieved");

            return ArrlUsers;

        }

        @Override
        protected void onPostExecute(ArrayList<User> aVoid) {
            super.onPostExecute(aVoid);
            Log.i(TAG, "Async Task Get User Data has been executed");
        }
    }

    private class NGetRecipeDataTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            DBHandler dbHandler = new DBHandler(CurrContext);

            SQLiteDatabase db = dbHandler.getReadableDatabase();

            String sQuery = "SELECT * FROM " + TABLE_RECIPE_NAME + ";";

            Cursor res = db.rawQuery(sQuery, null);

            ArrayList<Recipe> arrlData = new ArrayList<>();

            while(res.moveToNext()){

                arrlData.add(new Recipe(res.getString(1), res.getString(2), res.getString(3), res.getString(4)));

            }

            ArrlRecipes = arrlData;

            res.close();

            Log.i(TAG, "All Recipe Data has been retrieved");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i(TAG, "Async Task Get Recipe Data has been executed");
        }
    }

    private class NInsertRecipeIntoDBTask extends AsyncTask<Recipe, Void, Void>{

        @Override
        protected Void doInBackground(Recipe... recipes) {

            DBHandler dbHelper = new DBHandler(CurrContext);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COL_RECIPE_NAME, recipes[0].getsRecipeName());

            values.put(COL_RECIPE_INGREDIENTS, recipes[0].getsIngredients());

            values.put(COL_RECIPE_TYPE, recipes[0].getsRecipeType());

            values.put(COL_RECIPE_METHOD, recipes[0].getsMethod());

            boolean bResult = true;

            try {
                db.insert(TABLE_RECIPE_NAME, null, values);
            }catch (SQLException e){
                Log.e(TAG, "AN EXCEPTION HAS OCCURRED: " + e);
                bResult = false;
            }

            if (bResult == true) Log.i(TAG, "Recipe Entry has been created!");

            //Clear memory
            values.clear();

            return null;
        }
    }

    /*
    Nested class for AsyncTask of getting the Current Instance of the Database
    The following tasks will be done outside the main UI thread as the methods are very resource
    intensive
    */
}
