package com.shoker.fightersgym;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.shoker.fightersgym.PlanActivity.IS_NEW;

public class ScheduleActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    AlertDialog.Builder dialog;
    String m_Text ;
    BottomNavigationView bn ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        bn = findViewById(R.id.bn_schedule);

        bn.setOnNavigationItemSelectedListener(this);

    }


    public void addTraining(View view) {
        Intent i = new Intent(this,PlanActivity.class);
        i.putExtra(IS_NEW,true);
        startActivityForResult(i,0);
    }


    public void showDialog(String title){
         dialog = new AlertDialog.Builder(this);
         dialog.setTitle(title);
        final EditText input = new EditText(this);
        dialog.setView(input);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.navigation_workout_item :
                fragment = new ExerciseFragment();
                break;
            case R.id.navigation_calculator_item:
                fragment = new CaloriesFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container_schedule,fragment).commit();
        return true;
    }


}