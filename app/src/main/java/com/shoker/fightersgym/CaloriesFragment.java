package com.shoker.fightersgym;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.shoker.fightersgym.DB.CaloriesAdapter;
import com.shoker.fightersgym.DB.DatabaseHelper;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;


public class CaloriesFragment extends Fragment implements View.OnClickListener {

    DatabaseHelper helper ;

    ConstraintLayout expandableViewB;
    Button arrowBtnB;
    CardView cardViewB;
    Button addBreakfast;
    ListView lv_breakfast;
    TextView tv_desc_b;

    List<Food> brekfastlist;
    CaloriesAdapter breakfastAdapter;

    ConstraintLayout expandableViewL;
    Button arrowBtnL;
    CardView cardViewL;
    Button addlaunch;
    ListView lv_launch;
    TextView tv_desc_l;
    List<Food> launchlist;
    CaloriesAdapter launchAdapter;

    ConstraintLayout expandableViewD;
    Button arrowBtnD;
    CardView cardViewD;
    Button addDinner;
    ListView lv_dinner;
    TextView tv_desc_d;

    List<Food> dinnerlist;
    CaloriesAdapter dinnerAdapter;

    ConstraintLayout expandableViewS;
    Button arrowBtnS;
    CardView cardViewS;
    Button addSnacks;
    ListView lv_snacks;
    TextView tv_desc_s;

    List<Food> snacklist;
    CaloriesAdapter snackAdapter;

    public final static String CATEGORY ="category";
    public final static String BREAKFAST="breakfast";
    public final static String DINEER ="dinner";
    public final static String LAUNCH ="launch";
    public final static String SNACKS ="SNACK";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calories, container, false);

        if(isNewDay()){
            delete();
        }
        expandableViewB = v.findViewById(R.id.expandableView_calories_b);
        arrowBtnB = v.findViewById(R.id.btn_colaps_calories_b);
        cardViewB = v.findViewById(R.id.cardView_calories_b);
        addBreakfast =v.findViewById(R.id.btn_add_breakfast);
        lv_breakfast = v.findViewById(R.id.lv_calories_fragment_b);
        tv_desc_b = v.findViewById(R.id.tv_desc_calories_b);
        tv_desc_b.setText("Energy : "+getEnergySum(BREAKFAST)+" Kcal");

        brekfastlist =getList(BREAKFAST);
        breakfastAdapter = new CaloriesAdapter(getContext(),brekfastlist);
        lv_breakfast.setAdapter(breakfastAdapter);

        addBreakfast.setOnClickListener(this);
        arrowBtnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableViewB.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardViewB, new AutoTransition());
                    expandableViewB.setVisibility(View.VISIBLE);
                    arrowBtnB.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                } else {
                    TransitionManager.beginDelayedTransition(cardViewB, new AutoTransition());
                    expandableViewB.setVisibility(View.GONE);
                    arrowBtnB.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }
            }
        });

        expandableViewD = v.findViewById(R.id.expandableView_calories_d);
        arrowBtnD = v.findViewById(R.id.btn_colaps_calories_d);
        cardViewD = v.findViewById(R.id.cardView_calories_d);
        addDinner =v.findViewById(R.id.btn_add_dinner);
        lv_dinner = v.findViewById(R.id.lv_calories_fragment_d);
        tv_desc_d = v.findViewById(R.id.tv_desc_calories_d);
        tv_desc_d.setText("Energy : "+getEnergySum(DINEER)+" Kcal");

        dinnerlist =getList(DINEER);
        dinnerAdapter = new CaloriesAdapter(getContext(),dinnerlist);
        lv_dinner.setAdapter(dinnerAdapter);

        addDinner.setOnClickListener(this);
        arrowBtnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableViewD.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardViewD, new AutoTransition());
                    expandableViewD.setVisibility(View.VISIBLE);
                    arrowBtnD.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                } else {
                    TransitionManager.beginDelayedTransition(cardViewD, new AutoTransition());
                    expandableViewD.setVisibility(View.GONE);
                    arrowBtnD.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }
            }
        });

        expandableViewL = v.findViewById(R.id.expandableView_calories_l);
        arrowBtnL = v.findViewById(R.id.btn_colaps_calories_l);
        cardViewL = v.findViewById(R.id.cardView_calories_l);
        addlaunch =v.findViewById(R.id.btn_add_launch);
        lv_launch = v.findViewById(R.id.lv_calories_fragment_l);
        tv_desc_l = v.findViewById(R.id.tv_desc_calories_l);
        tv_desc_l.setText("Energy : "+getEnergySum(LAUNCH)+" Kcal");

        launchlist =getList(LAUNCH);
        launchAdapter = new CaloriesAdapter(getContext(),launchlist);
        lv_launch.setAdapter(launchAdapter);

        addlaunch.setOnClickListener(this);
        arrowBtnL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableViewL.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardViewL, new AutoTransition());
                    expandableViewL.setVisibility(View.VISIBLE);
                    arrowBtnL.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                } else {
                    TransitionManager.beginDelayedTransition(cardViewL, new AutoTransition());
                    expandableViewL.setVisibility(View.GONE);
                    arrowBtnL.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }
            }
        });

        expandableViewS = v.findViewById(R.id.expandableView_calories_s);
        arrowBtnS = v.findViewById(R.id.btn_colaps_calories_s);
        cardViewS = v.findViewById(R.id.cardView_calories_s);
        addSnacks =v.findViewById(R.id.btn_add_snacks);
        lv_snacks = v.findViewById(R.id.lv_calories_fragment_s);
        tv_desc_s = v.findViewById(R.id.tv_desc_calories_s);
        tv_desc_s.setText("Energy : "+getEnergySum(SNACKS)+" Kcal");

        snacklist =getList(SNACKS);
        snackAdapter = new CaloriesAdapter(getContext(),snacklist);
        lv_snacks.setAdapter(snackAdapter);

        addSnacks.setOnClickListener(this);
        arrowBtnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableViewS.getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(cardViewS, new AutoTransition());
                    expandableViewS.setVisibility(View.VISIBLE);
                    arrowBtnS.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                } else {
                    TransitionManager.beginDelayedTransition(cardViewS, new AutoTransition());
                    expandableViewS.setVisibility(View.GONE);
                    arrowBtnS.setBackgroundResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                }
            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getActivity(),Test.class);
        switch (v.getId()){
            case R.id.btn_add_launch :
            i.putExtra(CATEGORY,LAUNCH);
            break;
            case R.id.btn_add_breakfast :
                i.putExtra(CATEGORY,BREAKFAST);
                break;
            case R.id.btn_add_dinner :
                i.putExtra(CATEGORY,DINEER);
                break;
            case R.id.btn_add_snacks :
                i.putExtra(CATEGORY,SNACKS);
                break;
        }
        getActivity().startActivity(i);
    }

    private List<Food> getList(String cat){
        try {
            helper = new DatabaseHelper(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Food> list = helper.getFoods(cat);
        helper.close();
        return list;
    }

    private int getEnergySum(String cat){
        int sum =0;
        try {
            helper = new DatabaseHelper(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        sum = helper.energySum(cat);
        helper.close();
        return sum;
    }

    private Boolean isNewDay(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        int lastTimeStarted = settings.getInt("last_time_started", -1);
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_YEAR);

        if (today != lastTimeStarted) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("last_time_started", today);
            editor.commit();
            return true;
        }
        else{
            return false;
        }
    }

    private void delete(){
        try {
            helper = new DatabaseHelper(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        helper.deleteFoods();
        helper.close();
    }

}
