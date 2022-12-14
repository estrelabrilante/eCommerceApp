package com.example.shoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shoppingapp.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {

    private Button LoginButton;
    private EditText InputPhoneNumber , InputPassword;
    private ProgressDialog loadingBar;

    private String parentDbName = "Users";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = (Button) findViewById(R.id.login) ;
        InputPhoneNumber = (EditText) findViewById(R.id.login_phone);
        InputPassword = (EditText) findViewById(R.id.login_password);
        loadingBar = new ProgressDialog(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
           loginUser();
            }
        });



    }

    private void loginUser() {


        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Enter your phone number", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait...we are processing");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            AllowAccess(phone,password);
        }
    }

    private void AllowAccess( String phone, String password ) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
                //match
                if (snapshot.child(parentDbName).child(phone).exists()){
                  //retrieve data from Firebase.
                    Users userData = snapshot.child(parentDbName).child(phone).getValue(Users.class);
                  if (userData.getPhone().equals(phone)){
                      if (userData.getPassword().equals(password)){
                          Toast.makeText(loginActivity.this,"LoggedIn successfully",Toast.LENGTH_LONG).show();
                          loadingBar.dismiss();
                          Intent intent = new Intent(loginActivity.this, HomeActivity.class);
                          startActivity(intent);
                      }
                      else{
                          Toast.makeText(loginActivity.this,"Password is incorrect",Toast.LENGTH_LONG).show();
                          loadingBar.dismiss();
                      }

                  }
                }
                else {
                    Toast.makeText(loginActivity.this, "Account" + phone + "does not exist.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }
            }

            @Override
            public void onCancelled( @NonNull DatabaseError error ) {

            }
        });

    }
}