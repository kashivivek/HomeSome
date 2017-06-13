package com.avsoftsol.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.avsoftsol.app.AndroidVersion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kashivivek on 03-26-2017.
 */
public class ViewInitiatorTask {
    private Context mContext;
    public ArrayList<String> NameList;
    public ArrayList<String> CategoryList;
    public ArrayList<String> ImagePathList;
    public ArrayList PriceList;
    public static String[] android_version_names, android_image_urls;

    public ViewInitiatorTask(Context context) {
        mContext = context;
    }


    public static final String MyPREFERENCES = "MyPrefs";
    ProgressDialog progressDialog;
    DatabaseHandler db;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    public HashMap<String, String[]> execute(String params) {
        //onPreExecute();
        HashMap<String, String[]> resp = new HashMap<String, String[]>();
        String category = params;
        db = new DatabaseHandler(mContext);
        NameList = new ArrayList();
        CategoryList = new ArrayList();
        ImagePathList = new ArrayList();
        PriceList = new ArrayList();

        List<Product> products = db.getAllProducts(category);
        for (Product p : products) {
            NameList.add(p.getName());
            CategoryList.add(p.getCategory());
            ImagePathList.add(p.getImagepath());
            PriceList.add(p.getPrice());
        }
        android_version_names = NameList.toArray(new String[NameList.size()]);
        android_image_urls = ImagePathList.toArray(new String[ImagePathList.size()]);
        resp.put("version", android_version_names);
        resp.put("image", android_image_urls);
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


    protected void onPostExecute(String result) {
        // execution of result of Long time consuming operation
        progressDialog.dismiss();
        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();

    }


    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(mContext, "", "Refreshing the products list");
    }

}