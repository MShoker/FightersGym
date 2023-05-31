package com.shoker.fightersgym;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class ClassesListAdapter extends BaseAdapter {

    List<DocumentSnapshot> list ;
    Activity activity;
    private LayoutInflater inflater;

    private static final String CLASSNAME ="name";
    private static final String STARTDAY ="start";
    private static final String ENDDAY="end";
    private static final String DAYS ="days";

    ClassesListAdapter( List<DocumentSnapshot> list, Activity activity){
        this.list = list;
        this.activity = activity;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (this.inflater == null) {
            this.inflater = (LayoutInflater) this.activity.getLayoutInflater();
            // getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.classes_item, null);
        }

        TextView tv_name = convertView.findViewById(R.id.tv_class_name);
        TextView tv_start = convertView.findViewById(R.id.tv_class_start);
        TextView tv_end = convertView.findViewById(R.id.tv_class_end);
        TextView tv_days = convertView.findViewById(R.id.tv_classes_days);

        DocumentSnapshot d = list.get(position);

        String name = (String) d.get(CLASSNAME);
        String start = (String) d.get(STARTDAY);
        String end = (String) d.get(ENDDAY);
        String days = (String) d.get(DAYS);

        tv_name.setText("Class : "+name);
        tv_start.setText("Start Day : "+start);
        tv_end.setText("End Day : "+end);
        tv_days.setText("DAYs : "+days);
        return  convertView;



    }
}
