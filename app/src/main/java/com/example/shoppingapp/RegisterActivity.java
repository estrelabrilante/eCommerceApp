package com.example.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccount;
    private EditText PhoneNumber, Password, UserName;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAccount = (Button) findViewById(R.id.registerBtn);
        PhoneNumber = (EditText) findViewById(R.id.register_phone);
       Password = (EditText) findViewById(R.id.register_password);
        UserName = (EditText) findViewById(R.id.register_username);
    }
}