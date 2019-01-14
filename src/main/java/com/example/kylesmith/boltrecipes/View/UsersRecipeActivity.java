package com.example.kylesmith.boltrecipes.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kylesmith.boltrecipes.Controller.DBHandler;
import com.example.kylesmith.boltrecipes.Model.Recipe;
import com.example.kylesmith.boltrecipes.R;

import java.util.ArrayList;

public class UsersRecipeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //TAG
    private static final String TAG = UsersRecipeActivity.class.getSimpleName();

    //View component Vars
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ListView listView;
    TextView txtNavBarSubTitle;
    ImageView imgUserProfilePicture;
    View navHeaderView;

    //Member Objects
    DBHandler db;
    RecipeAdapter recipeAdapter;

    //Vars
    ArrayList<Recipe> ArrlRecipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_recipe);
        InitViewComponents();
        AllocateVars();
        AttachAdapter();
        PopulateDataIntoListView();
        SetNavHeaderAttribs();

    }

    @Override
    public void onBackPressed() {
         //Ensure that the drawerLayout is closed when leaving the activity
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.users_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_create_recipe) {
            // Handle the create recipe action
            Intent toCreateRecipeActivity = new Intent(this, CreateRecipeBaseActivity.class);
            startActivity(toCreateRecipeActivity);
        }

        //Close drawer once an item has been selected
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recipeAdapter.clear();
    }

    private void InitViewComponents(){

        SetToolbarAttribs();
        SetDrawerLayoutAttribs();
        SetNavigationViewAttribs();
        listView = (ListView) findViewById(R.id.list_view_users_recipe_activity);
        //Get the header of the drawer(rectangle at the top of the drawer) at the first instance of it
        // i.e. index 0
        navHeaderView = navigationView.getHeaderView(0);
        txtNavBarSubTitle = (TextView) navHeaderView.findViewById(R.id.txtNavBarSubtitle);
        imgUserProfilePicture = (ImageView) navHeaderView.findViewById(R.id.imgUserProfilePicture);

    }

    private void AllocateVars(){

        db = new DBHandler(getApplicationContext());
        ArrlRecipes = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(this, ArrlRecipes);

    }

    private void SetToolbarAttribs(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void SetDrawerLayoutAttribs(){

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void SetNavigationViewAttribs(){

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }



    private void AttachAdapter(){

        listView.setAdapter(recipeAdapter);

    }

    private void PopulateDataIntoListView(){

        ArrlRecipes = db.GetRecipeData();
        recipeAdapter = new RecipeAdapter(this, ArrlRecipes);
        listView.setAdapter(recipeAdapter);

    }

    private void SetNavHeaderAttribs(){

        txtNavBarSubTitle.setText(getIntent().getStringExtra("USERNAME"));
        imgUserProfilePicture.setImageResource(R.drawable.ic_user_profile_picture_filler);

    }


}
