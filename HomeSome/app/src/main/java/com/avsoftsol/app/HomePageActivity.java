package com.avsoftsol.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.avsoftsol.util.DatabaseHandler;
import com.avsoftsol.util.HttpHandler;
import com.avsoftsol.util.Product;
import com.avsoftsol.util.ViewInitiatorTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static Context mContext;
    public ArrayList<String> NameList;
    public ArrayList<String> CategoryList;
    public ArrayList PriceList;
    public static final String MyPREFERENCES = "MyPrefs";

   /* public String android_version_names[]= {
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow",
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow"
    };

    public String android_image_urls[]= {
            "https://s27.postimg.org/fycc1zqkz/one.jpg",
            "https://s24.postimg.org/if12osp9x/two.jpg",
            "https://s27.postimg.org/4n20cr4zn/three.jpg",
            "https://s29.postimg.org/ft4tbyerr/four.jpg",
            "https://s24.postimg.org/54nbpn13p/five.jpg",
            "https://s27.postimg.org/fycc1zqkz/one.jpg",
            "https://s24.postimg.org/if12osp9x/two.jpg",
            "https://s27.postimg.org/4n20cr4zn/three.jpg",
            "https://s29.postimg.org/ft4tbyerr/four.jpg",
            "https://s24.postimg.org/54nbpn13p/five.jpg",
            "https://s27.postimg.org/fycc1zqkz/one.jpg",
            "https://s24.postimg.org/if12osp9x/two.jpg",
            "https://s27.postimg.org/4n20cr4zn/three.jpg",
            "https://s29.postimg.org/ft4tbyerr/four.jpg",
            "https://s24.postimg.org/54nbpn13p/five.jpg",
            "https://s27.postimg.org/fycc1zqkz/one.jpg",
            "https://s24.postimg.org/if12osp9x/two.jpg",
            "https://s27.postimg.org/4n20cr4zn/three.jpg",
            "https://s29.postimg.org/ft4tbyerr/four.jpg",
            "https://s24.postimg.org/54nbpn13p/five.jpg"

    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute("5");


        mContext = getApplicationContext();
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initViews(R.id.fruit_recycler_view);
        initViews(R.id.organicfruits_recyclerview);
        initViews(R.id.vegetables_recyclerview);
        initViews(R.id.groceries_recyclerview);


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

    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    protected void onStart() {
        super.onStart();
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute("5");
        ViewInitiatorTask viewInitiatorTask = new ViewInitiatorTask(this);
        viewInitiatorTask.execute("5");

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

    private void initViews(int recyclerid) {
        RecyclerView recyclerView = (RecyclerView) findViewById(recyclerid);
        recyclerView.setHasFixedSize(true);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        ViewInitiatorTask viTask = new ViewInitiatorTask(this);
        viTask.execute("5");

        SharedPreferences pref = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String android_version_names_string = pref.getString("android_version_names_string", null);
        String android_image_urls_string = pref.getString("android_image_urls_string", null);
        String[] android_version_names = android_version_names_string.split("@");
        String[] android_image_urls = android_image_urls_string.split("@");

        ArrayList<AndroidVersion> androidVersions = ViewInitiatorTask.prepareData(android_version_names, android_image_urls);
        DataAdapter adapter = new DataAdapter(getApplicationContext(), androidVersions);
        recyclerView.setAdapter(adapter);

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
       /* if (id == R.id.action_basket) {
            Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }*/

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

    public class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;
        DatabaseHandler db;

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpHandler sh = new HttpHandler();
                // Making a request to url and getting response
                String url = "http://www.mocky.io/v2/58d827470f0000ae10dcc65b";
                String jsonStr = sh.makeServiceCall(url);
                if (jsonStr != null) {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("products");
                    db = new DatabaseHandler(HomePageActivity.this);
                    db.deleteAll();
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String name = c.getString("name");
                        String category = c.getString("category");
                        Integer price = c.getInt("price");
                        db.addProducts(new Product(name, category, price));
                    }
                    Integer count = db.getProductsCount();
                    resp = String.valueOf(count) + " Records Inserted";
                }

            } catch (final JSONException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            Toast.makeText(HomePageActivity.getContext(), result, Toast.LENGTH_SHORT).show();

        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(HomePageActivity.this, "", "Refreshing the products list");
        }


        @Override
        protected void onProgressUpdate(String... text) {

        }
    }
}
