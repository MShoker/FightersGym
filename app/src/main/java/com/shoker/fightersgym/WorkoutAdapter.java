package com.shoker.fightersgym;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener;

import java.util.List;

import static com.shoker.fightersgym.PlanActivity.DATE_UPDATE;
import static com.shoker.fightersgym.PlanActivity.EX_ID_UPDATE;
import static com.shoker.fightersgym.PlanActivity.ID_UPDATE;
import static com.shoker.fightersgym.PlanActivity.IS_NEW;
import static com.shoker.fightersgym.PlanActivity.NAME_UPDATE;
import static com.shoker.fightersgym.PlanActivity.TIME_UPDATE;

public class WorkoutAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<Training> mainDataList = null;



    WorkoutAdapter(Context context, List<Training> mainDataList) {
        mContext = context;
        this.mainDataList = mainDataList;
        inflater = LayoutInflater.from(mContext);
    }

    static class ViewHolder {
        protected TextView name;
        protected TextView date;
        protected TextView time;
        protected ImageView menu;
    }

    @Override
    public int getCount() {
        return mainDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mainDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final  ViewHolder holder;
        final int pos = position;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.workout_list_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name_workout);
            holder.date = (TextView) convertView.findViewById(R.id.tv_date_workout);
            holder.time = (TextView) convertView.findViewById(R.id.tv_time_workout);
            holder.menu = (ImageView)convertView.findViewById(R.id.iv_menu_workout);
            convertView.setTag(holder);
            convertView.setTag(R.id.tv_name_workout, holder.name);
            convertView.setTag(R.id.tv_date_workout, holder.date);
            convertView.setTag(R.id.tv_time_workout, holder.time);
            convertView.setTag(R.id.iv_menu_workout, holder.menu);
            holder.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu(holder.menu, pos);
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(mainDataList.get(position).getName());
        holder.date.setText(mainDataList.get(position).getDate());
        holder.time.setText(mainDataList.get(position).getTime());

        return convertView;
    }

    private void showPopupMenu(View view, final int position) {
        PopupMenu popup = new PopupMenu(mContext, view, Gravity.END);
        MenuInflater inflater = popup.getMenuInflater();

        inflater.inflate(R.menu.popup_menu, popup.getMenu());

        //set menu item click listener here
        popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_push_workout:
                        // ...
                        return true;
                    case R.id.menu_edit_workout:
                        Training tr =mainDataList.get(position);
                        Intent i = new Intent(mContext,PlanActivity.class);
                        i.putExtra(IS_NEW,false);
                        i.putExtra(ID_UPDATE,tr.getId());
                        i.putExtra(NAME_UPDATE,tr.getName());
                        i.putExtra(DATE_UPDATE,tr.getDate());
                        i.putExtra(TIME_UPDATE,tr.getTime());
                        i.putExtra(EX_ID_UPDATE,tr.getExercisesId());
                        mContext.startActivity(i);
                        return true;
                }
                return false;
            }
        });
        popup.show();
    }
}
