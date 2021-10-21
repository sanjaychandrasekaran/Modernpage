package com.sanjays.newmodernpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    TextInputLayout regName, regUserName, regEmail, regPhoneNo, regPassword;
    Button regBtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        //        hooks
        regName = findViewById(R.id.name);
        regUserName = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPhoneNo = findViewById(R.id.phone);
        regPassword = findViewById(R.id.password);
        regBtn = findViewById(R.id.btn_signup);
//        regToLoginButton = findViewById(R.id.btn_login);

    }




//        validation
        private Boolean validateName() {
            String val = regName.getEditText().getText().toString();


            if (val.isEmpty()) {
                regName.setError("Fields Cannot be Empty");
                return false;
            } else {
//                this null will remove the empty error if the user entered theis fst time wrongly
                regName.setError(null);
                return true;
            }
        }
    private Boolean validateUserName() {
        String val = regUserName.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";


        if (val.isEmpty()) {
            regUserName.setError("Fields Cannot be Empty");
            return false;
        } else if( val.length() >=15) {
            regUserName.setError("userName too long");
            return false;
        }
        else if(!val.matches(noWhiteSpace)) {
            regUserName.setError("White spaces are not allowed");
            return false;

        }
        else {
//                this null will remove the empty error if the user entered theis fst time wrongly
            regUserName.setError(null);
            regUserName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9. -]+@[a-z]+\\.+[a-z]+";


        if (val.isEmpty()) {
            regEmail.setError("Fields Cannot be Empty");
            return false;
        }else if(!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        }
        else {
//                this null will remove the empty error if the user entered theis fst time wrongly
            regEmail.setError(null);
            return true;
        }
    }
    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString();


        if (val.isEmpty()) {
            regPhoneNo.setError("Fields Cannot be Empty");
            return false;
        } else {
//                this null will remove the empty error if the user entered theis fst time wrongly
            regPhoneNo.setError(null);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        if (val.isEmpty()) {
            regPassword.setError("Fields Cannot be Empty");
            return false;
        } else {
//                this null will remove the empty error if the user entered theis fst time wrongly
            regPassword.setError(null);
            return true;
        }
    }
    public void regBtn(View view){
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

        if(!validateName() | !validateUserName() | !validateEmail() |!validatePassword() |!validatePhoneNo()) {
            return;
        }



//                rootNode = FirebaseDatabase.getInstance();
//                reference = rootNode.getReference("users");
//                get all the values from  the text fields
                String name = regName.getEditText().getText().toString();
                String username = regUserName.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneNo = regPhoneNo.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();
                UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
                reference.child(username).setValue(helperClass);

            }
        };






