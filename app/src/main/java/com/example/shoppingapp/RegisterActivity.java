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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccount;
    private EditText PhoneNumber, Password, UserName;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//reference according to id;
        CreateAccount = (Button) findViewById(R.id.registerBtn);
        PhoneNumber = (EditText) findViewById(R.id.register_phone);
       Password = (EditText) findViewById(R.id.register_password);
        UserName = (EditText) findViewById(R.id.register_username);
        loadingBar =new ProgressDialog (this);

        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {

        String name = UserName.getText().toString();
        String phoneNumber = PhoneNumber.getText().toString();
        String password = Password.getText().toString();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(this, "Enter your phone number", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
        }
        else{
         loadingBar.setTitle("Create Account");
         loadingBar.setMessage("Please wait...we are processing");
         loadingBar.setCanceledOnTouchOutside(false);
         loadingBar.show();
         validateInputData(name,phoneNumber,password);
        }
    }

    private void validateInputData( String name, String phoneNumber, String password ) {
        //database reference

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
                if(!snapshot.child("Users").child(phoneNumber).exists()){
            HashMap<String,Object> userdataMap = new HashMap<>();
            userdataMap.put("phone",phoneNumber);
            userdataMap.put("name",name);
            userdataMap.put("password",password);
            RootRef.child("Users").child(phoneNumber).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete( @NonNull Task<Void> task ) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Your account registered successfully", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                        Intent i = new Intent(RegisterActivity.this, loginActivity.class);
                        startActivity(i);

                    }
                    else{

                        loadingBar.dismiss();
                        Toast.makeText(RegisterActivity.this, "Try again..Something happened", Toast.LENGTH_SHORT).show();
                    }
                }
            });

                }
                else{
                    Toast.makeText(RegisterActivity.this, "This " + phoneNumber + "already exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try with another Phone Number", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled( @NonNull DatabaseError error ) {

            }
        });

    }
}