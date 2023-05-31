package com.shoker.fightersgym;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdabter extends BaseAdapter {
        Context mContext;
        LayoutInflater inflater;
        private List<Exercise> mainDataList = null;

    ArrayList<Integer> selectedExercies = new ArrayList<>();

    //This listener will be used on all your checkboxes, there's no need to
    //create a listener for every checkbox.
    CompoundButton.OnCheckedChangeListener checkChangedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int exerciseId = (int) buttonView.getTag();
            if(isChecked){
                selectedExercies.add(exerciseId);
            }else{
                selectedExercies.remove(exerciseId);
            }
        }
    };
    public ExerciseAdabter(Context context, List<Exercise> mainDataList) {

        mContext = context;
        this.mainDataList = mainDataList;
        inflater = LayoutInflater.from(mContext);
    }
        static class ViewHolder {
            protected TextView name;
            protected TextView muscle;
            protected TextView equip;
            protected CheckBox check;
            protected ImageView image;
        }
        @Override
        public int getCount() {
        return mainDataList.size();
    }
        @Override
        public Exercise getItem(int position) {
        return mainDataList.get(position);
    }
        @Override
        public long getItemId(int position) {
        return position;
    }
        public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.exercise_list_item, null);
            holder.name = (TextView) view.findViewById(R.id.tv_exercise_name);
            holder.muscle = (TextView) view.findViewById(R.id.tv_exercise_muscle);
            holder.equip = (TextView) view.findViewById(R.id.tv_exercise_equipment);
            holder.check = (CheckBox) view.findViewById(R.id.cb_exercise);
            holder.image = (ImageView) view.findViewById(R.id.iv_exercise_image);
            view.setTag(holder);
            view.setTag(R.id.tv_exercise_name, holder.name);
            view.setTag(R.id.tv_exercise_muscle, holder.muscle);
            view.setTag(R.id.tv_exercise_equipment, holder.equip);
            view.setTag(R.id.cb_exercise, holder.check);
            holder.check.setOnCheckedChangeListener(checkChangedListener);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.check.setTag(mainDataList.get(position).getId());

        holder.name.setText(mainDataList.get(position).getName());
        holder.muscle.setText(mainDataList.get(position).getSort());
        holder.equip.setText(mainDataList.get(position).getEquipment());
        ImageView imageView = holder.image;
            Drawable d1 = getImage(mainDataList.get(position).getRID());
            Drawable d2 = getImage(mainDataList.get(position).getRID()+"s");
            setAnimation(imageView,d1,d2);

            boolean bookSelected = false;
            if(selectedExercies.contains(mainDataList.get(position).getId())){
                bookSelected = true;
            }
            holder.check.setChecked(bookSelected);
        return view;





    }

    public int getResourceId(String id){
        int resID = mContext.getResources().getIdentifier(id, "drawable", mContext.getPackageName());
        return  resID;
    }

    public void setAnimation(ImageView i, Drawable d, Drawable b){
        AnimationDrawable animation = new AnimationDrawable();
        animation.addFrame(d,750);
        animation.addFrame(b,750);
        animation.setOneShot(false);
        i.setImageDrawable(animation);
        animation.start();
    }

    public Drawable getImage(String id ){
        int i = getResourceId(id);
        Drawable d = mContext.getResources().getDrawable(i);
        return d;
    }

    public void clearData() {
        mainDataList.clear();
    }

    public void setData(List<Exercise> list){
        mainDataList = list;
    }
}
