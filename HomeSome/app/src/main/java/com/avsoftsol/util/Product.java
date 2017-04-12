package com.avsoftsol.util;

/**
 * Created by kashivivek on 03-25-2017.
 */

public class Product {

    //private variables
    int _price;
    String _name;
    String _category;

    // Empty constructor
    public Product() {

    }

    // constructor
    public Product(String _name, String _category, int _price) {
        this._price = _price;
        this._name = _name;
        this._category = _category;
    }

    // constructor
    public Product(String name, String _category) {
        this._name = name;
        this._category = _category;
    }

    // getting Price
    public int getPrice() {
        return this._price;
    }

    // setting Price
    public void setPrice(int _price) {
        this._price = _price;
    }

    // getting name
    public String getName() {
        return this._name;
    }

    // setting name
    public void setName(String name) {
        this._name = name;
    }

    // getting Categrory
    public String getCategory() {
        return this._category;
    }

    public void setCategory(String _category) {
        this._category = _category;
    }
}
