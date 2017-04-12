package com.avsoftsol.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.avsoftsol.app.AndroidVersion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kashivivek on 03-26-2017.
 */
public class ViewInitiatorTask extends AsyncTask<String, String, String> {
    private Context mContext;
    public ArrayList<String> NameList;
    public ArrayList<String> CategoryList;
    public ArrayList PriceList;
    public static String[] android_version_names, android_image_urls;

    public ViewInitiatorTask(Context context) {
        mContext = context;
    }

    private String resp;
    public static final String MyPREFERENCES = "MyPrefs";
    ProgressDialog progressDialog;
    DatabaseHandler db;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    public String doInBackground(String... params) {
        pref = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = pref.edit();
        db = new DatabaseHandler(mContext);
        NameList = new ArrayList();
        CategoryList = new ArrayList();
        PriceList = new ArrayList();

        List<Product> products = db.getAllProducts();
        for (Product p : products) {
            NameList.add(p.getName());
            CategoryList.add(p.getCategory());
            PriceList.add(p.getPrice());
        }
        android_version_names = NameList.toArray(new String[NameList.size()]);
        android_image_urls = CategoryList.toArray(new String[CategoryList.size()]);
        StringBuilder android_version_names_string = new StringBuilder();
        StringBuilder android_image_urls_string = new StringBuilder();
        for (int i = 0; i < android_image_urls.length; i++) {
            android_image_urls_string.append(android_image_urls[i]).append("@");
            android_version_names_string.append(android_version_names[i]).append("@");
        }
        resp = String.valueOf(android_version_names[1]) + " record retrieved";
        editor.putString("android_version_names_string", android_version_names_string.toString());
        editor.putString("android_image_urls_string", android_image_urls_string.toString());
        editor.commit();
        return resp;
    }

    public static ArrayList<AndroidVersion> prepareData(String[] android_version_names, String[] android_image_urls) {

        ArrayList<AndroidVersion> android_version = new ArrayList<>();
        for (int i = 0; i < android_version_names.length; i++) {
            AndroidVersion androidVersion = new AndroidVersion();
            androidVersion.setAndroid_version_name(android_version_names[i]);
            androidVersion.setAndroid_image_url(android_image_urls[i]);
            android_version.add(androidVersion);
        }
        return android_version;
    }

    @Override
    protected void onPostExecute(String result) {
        // execution of result of Long time consuming operation
        progressDialog.dismiss();
        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(mContext, "", "Refreshing the products list");
    }


    @Override
    protected void onProgressUpdate(String... text) {


    }
}