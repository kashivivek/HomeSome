package com.avsoftsol.homesome;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static RecyclerView recyclerView;
    //String and Integer array for Recycler View Items
    public static final String[] SUBTITLES = {"Strawberries", "Green Grapes", "Black Grapes", "Apples", "Bananas", "Strawberries", "Green Grapes", "Black Grapes", "Apples", "Bananas", "Strawberries", "Green Grapes", "Black Grapes", "Apples", "Bananas", "Strawberries", "Green Grapes", "Black Grapes", "Apples", "Bananas"};
    public static final String[] TITLES = {"Strawberries", "Green Grapes", "Black Grapes", "Apples", "Bananas", "Strawberries", "Green Grapes", "Black Grapes", "Apples", "Bananas", "Strawberries", "Green Grapes", "Black Grapes", "Apples", "Bananas", "Strawberries", "Green Grapes", "Black Grapes", "Apples", "Bananas"};
    public static final Integer[] IMAGES = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five
    };

    private static String navigateFrom = "horizontal";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                initViews(R.id.recycler_view);
                populatRecyclerView();
                initViews(R.id.recyclerview_organicfruits);
                populatRecyclerView();
                initViews(R.id.recyclerview_vegetables);
                populatRecyclerView();
            }
        });
        t1.start();
        /*Thread t2 = new Thread(new Runnable() {
            public void run() {
                initViews(R.id.recycler_view_organicfruits);
                populatRecyclerView();
            }
        });
        t2.start();*/


    }

    // Initialize the view
    private void initViews(int recycler) {
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Set Back Icon on Activity*/

        recyclerView = (RecyclerView)
                findViewById(recycler);
        recyclerView.setHasFixedSize(true);

        //Set RecyclerView type according to intent value
        if (navigateFrom.equals("horizontal")) {
            recyclerView
                    .setLayoutManager(new LinearLayoutManager(HomePageActivity.this, LinearLayoutManager.HORIZONTAL, false));
        } else {
            getSupportActionBar().setTitle("Staggered GridLayout Manager");
            recyclerView
                    .setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));// Here 2 is no. of columns to be displayed

        }
    }


    // populate the list view by adding data to arraylist
    private void populatRecyclerView() {
        ArrayList<Data_Model> arrayList = new ArrayList<>();
        for (int i = 0; i < TITLES.length; i++) {
            arrayList.add(new Data_Model(TITLES[i], IMAGES[i], SUBTITLES[i]));
        }
        RecyclerView_Adapter adapter = new RecyclerView_Adapter(HomePageActivity.this, arrayList);
        recyclerView.setAdapter(adapter);// set adapter on recyclerview
        adapter.notifyDataSetChanged();// Notify the adapter

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_basket) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
