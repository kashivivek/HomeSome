package com.avsoftsol.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kashivivek on 03-25-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "homesomedb";

    // Contacts table name
    private static final String TABLE_PRODUCTS = "products";

    // Contacts Table Columns names
    private static final String KEY_NAME = "name";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_PRICE = "price";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_NAME + " TEXT PRIMARY KEY," + KEY_CATEGORY + " TEXT,"
                + KEY_PRICE + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);

        // Create tables again
        onCreate(db);
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_NAME + " TEXT PRIMARY KEY," + KEY_CATEGORY + " TEXT,"
                + KEY_PRICE + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
        db.close();
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new product
    public void addProducts(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getName()); // Product Name
        values.put(KEY_CATEGORY, product.getCategory()); // Product Category
        values.put(KEY_PRICE, product.getPrice()); // Product price

        // Inserting Row
        db.insert(TABLE_PRODUCTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single product
    Product getproduct(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCTS, new String[]{KEY_NAME,
                        KEY_CATEGORY, KEY_PRICE}, KEY_NAME + "=?",
                new String[]{name}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Product product = new Product(cursor.getString(0),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)));
        // return product
        return product;
    }

    // Getting All Products
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<Product>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setName(cursor.getString(0));
                product.setCategory(cursor.getString(1));
                product.setPrice(Integer.parseInt(cursor.getString(2)));
                // Adding product to list
                productList.add(product);
            } while (cursor.moveToNext());
        }

        // return Products list
        return productList;
    }

    // Updating single product
    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getName());
        values.put(KEY_CATEGORY, product.getCategory());
        values.put(KEY_PRICE, product.getPrice());

        // updating row
        return db.update(TABLE_PRODUCTS, values, KEY_NAME + " = ?",
                new String[]{product.getName()});
    }

    // Deleting single product
    public void deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, KEY_NAME + " = ?",
                new String[]{product.getName()});
        db.close();
    }


    // Getting Products Count
    public int getProductsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        Integer count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
}