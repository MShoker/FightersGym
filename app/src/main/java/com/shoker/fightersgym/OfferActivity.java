package com.shoker.fightersgym;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class OfferActivity extends AppCompatActivity {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private Bitmap my_image;
    ImageView iv_offer;
    File localFile;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        progressBar = findViewById(R.id.progressbar_offer);
        progressBar.setVisibility(View.VISIBLE);

        if(isNetworkConnected()){

            iv_offer =findViewById(R.id.iv_offer_class);
            try {
            localFile = File.createTempFile("Images", "bmp");
            StorageReference storageRef = storage.getReference();
            StorageReference islandRef = storageRef.child("offer.jpg");
            final long ONE_MEGABYTE = 1024 * 1024;
            islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                my_image = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                iv_offer.setImageBitmap(my_image);
                iv_offer.setScaleType(ImageView.ScaleType.FIT_XY);
                progressBar.setVisibility(View.INVISIBLE);
            }
            }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
            });
            } catch (IOException e) {
            e.printStackTrace();
            }
        }else{
            findViewById(R.id.tv_internet_error_offer).setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}