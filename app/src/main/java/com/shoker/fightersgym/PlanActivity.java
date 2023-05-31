package com.shoker.fightersgym;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shoker.fightersgym.DB.DatabaseHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class PlanActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText et_Date, et_Time ,et_Name;
    ImageView iv_Date,iv_Time;
    String name,date,time;
    ListView listView;
    Spinner sp_Muscle,sp_equip;

    private int mYear, mMonth, mDay, mHour, mMinute;
    private int musclePsition =0;
    private int equipmentPosition=0;
    ExerciseAdabter adabter;
    DatabaseHelper helper ;

    ArrayList<String> muscles;
    ArrayList<String> equipments;
    ArrayList<Integer> selectedExercies;
    public static final String IS_NEW="is_new";
    public static final String ID_UPDATE ="id_update";
    public static final String NAME_UPDATE ="name_update";
    public static final String DATE_UPDATE ="date_update";
    public static final String TIME_UPDATE ="time_update";
    public static final String EX_ID_UPDATE ="ex_id_update";

    Boolean isNew;
    int id_update;

    final Calendar c = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        et_Date =findViewById(R.id.et_plan_date);
        et_Time =findViewById(R.id.et_plan_time);
        et_Name=findViewById(R.id.et_plan_name);

        iv_Date = findViewById(R.id.iv_plane_date);
        iv_Time=findViewById(R.id.iv_plane_time);

        listView = findViewById(R.id.lv_plan);
        sp_Muscle = findViewById(R.id.sp_plan_muscle);
        sp_equip = findViewById(R.id.sp_plan_equipment);
        populateSpinner();

        fillList(getAllExercies());

        isNew=getIntent().getExtras().getBoolean(IS_NEW);
        if(!isNew){
            id_update=getIntent().getExtras().getInt(ID_UPDATE);
            et_Name.setText(getIntent().getExtras().getString(NAME_UPDATE).toString());
            et_Date.setText(getIntent().getExtras().getString(DATE_UPDATE).toString());
            et_Time.setText(getIntent().getExtras().getString(TIME_UPDATE).toString());
        }
    }

    public void fillList(List<Exercise> list){
        adabter = new ExerciseAdabter(this,list);
        listView.setAdapter(adabter);
    }

    public List<Exercise> getAllExercies(){

        try {
            helper = new DatabaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Exercise> list = helper.getAllExercies();
        helper.close();
        return list;
    }

    public List<Exercise> getFilteredExercies(String col , String selection){
        try {
            helper = new DatabaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Exercise> list = helper.getFilteredExercies(col,selection);
        helper.close();
        return list;
    }

    private  List<Exercise> getExtendedFilteriedExercies(String muscle,String equip){
        try {
            helper = new DatabaseHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Exercise> list = helper.getExtenderFilteriedExercies(muscle,equip);
        helper.close();
        return list;
    }



    public void selectTime(View view) {
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String sHour;
                        if(hourOfDay<10){
                            sHour = "0"+hourOfDay;
                        }else{
                            sHour=String.valueOf(hourOfDay);
                        }

                        String sMin;
                        if(minute<10){
                            sMin="0"+minute;
                        }else{
                            sMin = String.valueOf(minute);
                        }
                        et_Time.setText(sHour+":"+sMin);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void selectDate(View view) {

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String sYear = String.valueOf(year);

                String sMonth;
                if(month<10){
                   sMonth="0"+month;
                }else{
                  sMonth= String.valueOf(month);
                }

                String sDay;
                if (dayOfMonth<10){
                    sDay="0"+dayOfMonth;
                }else{
                    sDay = String.valueOf(dayOfMonth);
                }
                et_Date.setText(sYear+"-"+sMonth+"-"+sDay);
            }
        },mYear,mMonth,mDay);
        datePickerDialog.show();
    }



    private void populateSpinner(){

         muscles = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.spinner_muscle)));
         equipments = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.spinner_equipment)));

        ArrayAdapter<String> musclesAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,muscles);
        ArrayAdapter<String> equipmentAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,equipments);

        musclesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        equipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_Muscle.setAdapter(musclesAdapter);
        sp_equip.setAdapter(equipmentAdapter);
        sp_Muscle.setOnItemSelectedListener(this);
        sp_equip.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId()==R.id.sp_plan_muscle){
            musclePsition = position;
            Log.d("show","muscle position ="+musclePsition);
        }else{
            equipmentPosition=position;
        }

        if (musclePsition==0&&equipmentPosition==0){
            notifyAdapterChange(getAllExercies());
        }else if(musclePsition==0&&equipmentPosition!=0){
            notifyAdapterChange(getFilteredExercies("equip",equipments.get(equipmentPosition)));
        }else if(equipmentPosition==0&&musclePsition!=0){
            notifyAdapterChange(getFilteredExercies("sort",muscles.get(musclePsition)));
        }else{
            notifyAdapterChange(getExtendedFilteriedExercies(muscles.get(musclePsition),equipments.get(equipmentPosition)));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void notifyAdapterChange(List<Exercise> list){
        adabter.clearData();
        adabter.setData(list);
        adabter.notifyDataSetChanged();
    }

    public void save(View view) {

        name = et_Name.getText().toString();
        date = et_Date.getText().toString();
        time = et_Time.getText().toString();
        selectedExercies = adabter.selectedExercies;
        StringBuilder s = new StringBuilder();
        if(check()){
            for (int ex_id:selectedExercies) {
                s.append(ex_id).append(",");
            }
            String ex_id = s.toString();
            int n_of_exercies = selectedExercies.size();
            try {
                helper = new DatabaseHelper(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Boolean b;
            if(isNew){
             b =helper.insertTraining(name,date,time,n_of_exercies,ex_id);
            }else {
                b=helper.updateTraining(id_update,name,date,time,n_of_exercies,ex_id);
            }
            if(b){
                Toast.makeText(this,"Succesfuly Saved your Training",Toast.LENGTH_LONG).show();
                finish();
            }else{
                Toast.makeText(this,"Faild to Save your Training",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void cancel(View view) {
        finish();
    }

    private boolean check() {
        if(TextUtils.isEmpty(name)){
            et_Name.setError("Full name is required");
            et_Name.requestFocus();
            Toast.makeText(this, "Enter Name of Training.", Toast.LENGTH_LONG).show();
            return false;
        }

        if(TextUtils.isEmpty(date)||!date.matches("\\d{4}-\\d{2}-\\d{2}")){
            et_Date.setError("Date is required");
            et_Date.requestFocus();
            Toast.makeText(this, "Select Day.", Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(time)||!time.matches("\\d\\d:\\d\\d")){
            et_Time.setError("Time is required");
            et_Time.requestFocus();
            Toast.makeText(this, "Select Time.", Toast.LENGTH_LONG).show();
            return false;
        }
        if(selectedExercies.size()==0){
            Toast.makeText(this, "Please select  Exercies.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}