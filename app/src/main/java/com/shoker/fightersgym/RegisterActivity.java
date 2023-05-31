package com.shoker.fightersgym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements OnCompleteListener<AuthResult>{
    String TAG ="shoker";

    String userName ;
    String userEmail ;
    String userPhone ;
    String password ;
    String confirmPassword ;

    EditText usernameField ;
    EditText userEmailField ;
    EditText userPhoneField ;
    EditText passwordField ;
    EditText confirmPasswordField ;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
    }

    public void registerAccount(View view) {
        // validate the form
        usernameField = (EditText)findViewById(R.id.userFullName);
        userEmailField = (EditText)findViewById(R.id.userEmail);
        userPhoneField = (EditText)findViewById(R.id.userphone);
        passwordField = (EditText)findViewById(R.id.password);
        confirmPasswordField = (EditText)findViewById(R.id.passwordConfirm);

        userName = usernameField.getText().toString();
        userEmail = userEmailField.getText().toString();
        userPhone = userPhoneField.getText().toString();
        password = passwordField.getText().toString();
        confirmPassword = confirmPasswordField.getText().toString();

        if(check()) {
            mAuth.createUserWithEmailAndPassword(userEmail, password)
                    .addOnCompleteListener(this, this);
        }

    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            // Sign in is successful
            User user = new User(userName,userEmail,userPhone);
            FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendVerificationEmail();
                        Toast.makeText(getBaseContext(),"authentication successful please verify your Email ", Toast.LENGTH_LONG).show();
                    } else {

                    }
                }
            });
            setResult(Activity.RESULT_OK, getIntent());
            this.finish();


        } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, "createUserWithEmail:failure", task.getException());
            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                Toast.makeText(getBaseContext(), "This Email is already registered.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getBaseContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendVerificationEmail() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    mAuth.getInstance().signOut();
                }
            }
        });
    }

    private boolean check() {
        if(TextUtils.isEmpty(userName)){
            usernameField.setError("Full name is required");
            usernameField.requestFocus();
            Toast.makeText(this, "Enter Your Name.", Toast.LENGTH_LONG).show();
            return false;
        }

        if(TextUtils.isEmpty(userEmail)||!(Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())){
            userEmailField.setError("Email is required");
            userEmailField.requestFocus();
            Toast.makeText(this, "Enter Your Email.", Toast.LENGTH_LONG).show();
            return false;
        }

        if(TextUtils.isEmpty(userPhone)){
            userPhoneField.setError("Phone is required");
            userPhoneField.requestFocus();
            Toast.makeText(this, "Enter Your Phone.", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!(userPhone.length()==11)||!(userPhone.charAt(0)=='0')||!(userPhone.charAt(1)=='1')){
            userPhoneField.requestFocus();
            Toast.makeText(this, "Enter Correct Phone Number.", Toast.LENGTH_LONG).show();
            return false;
        }


        if(TextUtils.isEmpty(password)){
            passwordField.setError("Enter Password");
            passwordField.requestFocus();
            Toast.makeText(this, "Enter Password.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!confirmPassword.equals(password)) {
            confirmPasswordField.setError("Passwords do not match.");
            confirmPasswordField.requestFocus();
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
