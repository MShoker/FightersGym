package com.shoker.fightersgym.DB;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shoker.fightersgym.Food;
import com.shoker.fightersgym.R;

import java.util.List;

public class CaloriesAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<Food> mainDataList = null;

    public CaloriesAdapter(Context context, List<Food> mainDataList) {

        mContext = context;
        this.mainDataList = mainDataList;
        inflater = LayoutInflater.from(mContext);
    }
    static class ViewHolder {
        protected TextView name;
        protected TextView energy;
        protected TextView quantity;

    }
    @Override
    public int getCount() {
        return mainDataList.size();
    }
    @Override
    public Food getItem(int position) {
        return mainDataList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(final int position, View view, ViewGroup parent) {
        final CaloriesAdapter.ViewHolder holder;
        if (view == null) {
            holder = new CaloriesAdapter.ViewHolder();
            view = inflater.inflate(R.layout.calories_list_item, null);
            holder.name = (TextView) view.findViewById(R.id.tv_calories_item_name);
            holder.energy = (TextView) view.findViewById(R.id.tv_calories_item_energy);
            view.setTag(holder);
            view.setTag(R.id.tv_calories_item_name, holder.name);
            view.setTag(R.id.tv_calories_item_energy, holder.energy);

        } else {
            holder = (CaloriesAdapter.ViewHolder) view.getTag();
        }
        holder.name.setText(mainDataList.get(position).getName());
        holder.energy.setText(String.valueOf(mainDataList.get(position).calories));
        return view;
    }
}
