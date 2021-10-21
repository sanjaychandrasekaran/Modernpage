package com.sanjays.newmodernpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

Button callSignUp;
ImageView image;
TextView logoText,sloganText;
TextInputLayout username,password;
Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
//hooks
        callSignUp = findViewById(R.id.signup_screen);
        image = findViewById(R.id.imageView);
        logoText = findViewById(R.id.logoName);
        sloganText = findViewById(R.id.slogan);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.button_login);
    }
    private Boolean validateUserName() {
        String val = username.getEditText().getText().toString();
        if (val.isEmpty()) {
            username.setError("Fields Cannot be Empty");
            return false;
        }
        else {
//                this null will remove the empty error if the user entered theis fst time wrongly
         username.setError(null);
            username .setErrorEnabled(false);
            return true;
        }
    }
    private  Boolean validatePassword(){

            String val = password.getEditText().getText().toString();
            if (val.isEmpty()) {
                password.setError("Fields Cannot be Empty");
                return false;
            } else {
//                this null will remove the empty error if the user entered theis fst time wrongly
                password.setError(null);
                return true;
            }
        }


    public void loginUser(View view) {
//        validate login info
        if (!validateUserName() | !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    private  void isUser() {
        String userEnteredUserName = username.getEditText().getText().toString().trim();
        String userEnteredPassword = password.getEditText().getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");


        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUserName);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                our datas are inside the datasnapshot
                if(dataSnapshot.exists()){

                    username.setError(null);
                    username.setErrorEnabled(false);



                    String passwordFromDB = dataSnapshot.child(userEnteredUserName).child("password").getValue(String.class);

                    if(passwordFromDB.equals(userEnteredPassword)){

                      username.setError(null);
                      username.setErrorEnabled(false);




                        String nameFromDB = dataSnapshot.child(userEnteredUserName).child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(userEnteredUserName).child("username").getValue(String.class);
                        String phoneNoFromDB = dataSnapshot.child(userEnteredUserName).child("phoneNo").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredUserName).child("email").getValue(String.class);


                        Intent intent = new Intent(getApplicationContext(),UserProfiles.class);
                        intent.putExtra("name",nameFromDB);
                        intent.putExtra("username",usernameFromDB);
                        intent.putExtra("email",emailFromDB);
                        intent.putExtra("phoneNo",phoneNoFromDB);
                        intent.putExtra("password",passwordFromDB);


                        startActivity(intent);
                    }
                    else{
//                        if the password went wrong
                        password.setError("Wrong password");
                        password.requestFocus();
                    }
                }
                else{
                    username.setError("No such user exists");
                    username.requestFocus();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                if the password went wrong


            }
        });
    }


    //       callSignUp.setOnClickListener(new View.OnClickListener() {
//           @Override
           public void SignUpUser(View view) {
               Intent intent = new Intent(Login.this,signup.class);
               startActivity(intent);
           }




    }
