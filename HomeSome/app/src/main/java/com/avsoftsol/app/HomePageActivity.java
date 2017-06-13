package com.avsoftsol.app;

import android.app.ProgressDialog;
import android.content.Context;
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
import java.util.HashMap;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute("5");


        mContext = getApplicationContext();
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews(R.id.fruit_recycler_view, "fruits");
        initViews(R.id.organicfruits_recyclerview, "organicfruits");
        initViews(R.id.vegetables_recyclerview, "vegetables");
        initViews(R.id.groceries_recyclerview, "groceries");


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
        initViews(R.id.fruit_recycler_view, "fruits");
        initViews(R.id.organicfruits_recyclerview, "organicfruits");
        initViews(R.id.vegetables_recyclerview, "vegetables");
        initViews(R.id.groceries_recyclerview, "groceries");

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

    private void initViews(int recyclerid, String category) {

        RecyclerView recyclerView = (RecyclerView) findViewById(recyclerid);
        recyclerView.setHasFixedSize(true);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        ViewInitiatorTask viTask = new ViewInitiatorTask(this);
       /* try {
            viTask.execute(category).get(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }*/
        HashMap<String, String[]> resp = new HashMap<String, String[]>();
        resp = viTask.execute(category);

        String[] android_version_names = resp.get("version");
        String[] android_image_urls = resp.get("image");

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
                String url = "http://www.mocky.io/v2/58f1c574240000e70df69712";
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
                        String imagepath = c.getString("imagepath");
                        Integer price = c.getInt("price");
                        db.addProducts(new Product(name, category, imagepath, price));
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
            /*Toast.makeText(HomePageActivity.getContext(), result, Toast.LENGTH_SHORT).show();*/

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
