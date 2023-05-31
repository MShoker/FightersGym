package com.shoker.fightersgym;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

public class FoodAdapter extends BaseAdapter {
    Activity activity;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private LayoutInflater inflater;
    ArrayList<Food> singletons;

    public FoodAdapter(Activity activity, ArrayList<Food> singletons) {
        this.activity = activity;
        this.singletons = singletons;
    }

    public int getCount() {
        return this.singletons.size();
    }

    public Object getItem(int i) {
        return this.singletons.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (this.inflater == null) {
            this.inflater = (LayoutInflater) this.activity.getLayoutInflater();
            // getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.food_list_item, null);
        }
        if (this.imageLoader == null) {
            this.imageLoader = AppController.getInstance().getImageLoader();
        }
        NetworkImageView networkImageView = (NetworkImageView) convertView.findViewById(R.id.food_image);
        final TextView name = (TextView) convertView.findViewById(R.id.tv_food_name);
        final  TextView claories=(TextView)convertView.findViewById(R.id.tv_food_calories);

        Food singleton = (Food) this.singletons.get(i);
        networkImageView.setImageUrl(singleton.getImage_url(), this.imageLoader);
        name.setText(singleton.getName());
        String calories = "Energy : "+singleton.getCalories()+" kcal";
        claories.setText(calories);
        return convertView;
    }
}