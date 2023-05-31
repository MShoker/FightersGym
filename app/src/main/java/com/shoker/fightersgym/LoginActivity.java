package com.shoker.fightersgym;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements OnCompleteListener<AuthResult> {

    String userEmail ;
    String password ;

    EditText userEmailField ;
    EditText passwordField ;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        mAuth = FirebaseAuth.getInstance();


    }

    public void startRegisterActivity(View view) {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivityForResult(registerIntent, 1);
    }

    public void login(View view) {

      /*  userEmailField = findViewById(R.id.login_et_username);
        passwordField = findViewById(R.id.login_et_password);

        userEmail = userEmailField.getText().toString();
        password = passwordField.getText().toString();

        if(check()) {
            mAuth.signInWithEmailAndPassword(userEmail,password).addOnCompleteListener(this,this);
        }

       */
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        
        if (!task.isSuccessful()){
            Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
        }else {
            checkIfEmailVerified();
        }
    }

    private void checkIfEmailVerified() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user.isEmailVerified()) {
            Intent intent = new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else{
            mAuth.signOut();
            Toast.makeText(this, "Please verify your Email", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean check() {
        if(TextUtils.isEmpty(userEmail)||!(Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())){
            userEmailField.setError("Email is required");
            userEmailField.requestFocus();
            Toast.makeText(this, "Enter Your Email.", Toast.LENGTH_LONG).show();
            return false;
        }

        if(TextUtils.isEmpty(password)){
            passwordField.setError("Enter Password");
            passwordField.requestFocus();
            Toast.makeText(this, "Enter Password.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

  /*  @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }

   */
}
