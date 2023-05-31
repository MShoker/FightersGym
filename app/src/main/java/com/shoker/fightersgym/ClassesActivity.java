package com.shoker.fightersgym;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ClassesActivity extends AppCompatActivity {

    private static final String COLLECTIONPATH ="classes";
    private static final String CLASSNAME ="name";
    private static final String STARTDAY ="start";
    private static final String ENDDAY="end";
    private static final String DAYS ="days";
    FirebaseFirestore db;

    List<DocumentSnapshot> list ;
    ListView listView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        db = FirebaseFirestore.getInstance();
        listView = findViewById(R.id.lv_classes);
        progressBar = findViewById(R.id.progressbar_classes);
        progressBar.setVisibility(View.VISIBLE);
        if(isNetworkConnected()) {
            readData();
        }else {
            findViewById(R.id.tv_internet_error_classes).setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }

    }

    private void readData(){
        db.collection(COLLECTIONPATH) .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    list = task.getResult().getDocuments();
                    ClassesListAdapter adapter = new ClassesListAdapter(list,ClassesActivity.this);
                    listView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);

                } else {
                    Log.w("TAG", "Error getting documents.", task.getException());
                }
            }
        });

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


}