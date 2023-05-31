package com.shoker.fightersgym;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.ListFragment;

import com.shoker.fightersgym.DB.DatabaseHelper;

import java.io.IOException;
import java.util.List;

public class ExerciseFragment extends ListFragment {

    DatabaseHelper helper ;
    ListView listView;
    List<Training> trainings;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exercise_fragment, container, false);

        trainings = getList();
        WorkoutAdapter adapter = new WorkoutAdapter(getContext(),trainings);
        setListAdapter(adapter);
        return view;

    }


    public List<Training> getList(){
        try {
            helper = new DatabaseHelper(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Training> list = helper.getAllTrainings();
        helper.close();
        return list;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        String title = trainings.get(position).getName();
        String ids =trainings.get(position).getExercisesId();
        String [] ex_id = convertStringToArray(ids);
        List<Exercise> exerciselist = getCertenExercise(ex_id);

        showDialog(title,exerciselist);
    }

    public void showDialog(String title,List<Exercise> list){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(title);
        final ListView lv = new ListView(getContext());
        ExerciseAdabter cAdapter = new ExerciseAdabter(getContext(),list);
        lv.setAdapter(cAdapter);
        dialog.setView(lv);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public static String[] convertStringToArray(String str){
        String strSeparator=",";
        String[] arr = str.split(strSeparator);
        return arr;
    }

    public List<Exercise> getCertenExercise(String[] ex_id){
        try {
            helper = new DatabaseHelper(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Exercise> list = helper.getCertinExercise(ex_id);
        return list;
    }
}
