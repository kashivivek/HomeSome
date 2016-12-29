package com.avsoftsol.homesome;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.avsoftsol.homesome.util.Cache;

import java.util.ArrayList;


public class RecyclerView_Adapter extends
        RecyclerView.Adapter<RecyclerViewHolder> {// Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<Data_Model> arrayList;
    private Context context;

    public RecyclerView_Adapter(Context context,
                                ArrayList<Data_Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        final Data_Model model = arrayList.get(position);

        RecyclerViewHolder mainHolder = (RecyclerViewHolder) holder;// holder

        Bitmap image = BitmapFactory.decodeResource(context.getResources(),
                model.getImage());// This will convert drawbale image into
        // bitmap

        //Saving bitmap to cache. it will later be retrieved using the bitmap_image key
        Cache.getInstance().getLru().put("bitmap_image", image);

        // setting title
        mainHolder.title.setText(model.getTitle());
        mainHolder.subtitle.setText(model.getSubtitle());

        //To get bitmap from cache using the key. Must cast retrieved cache Object to Bitmap
        Bitmap bitmap = (Bitmap) Cache.getInstance().getLru().get("bitmap_image");

        mainHolder.imageview.setImageBitmap(bitmap);


    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.item_row, viewGroup, false);
        RecyclerViewHolder listHolder = new RecyclerViewHolder(mainGroup);
        return listHolder;

    }

}