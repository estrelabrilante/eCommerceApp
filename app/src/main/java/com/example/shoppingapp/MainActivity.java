package com.example.shoppingapp;

import    androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
  private Button joinNowButton, loginButton;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinNowButton = findViewById(R.id.main_join_now_btn);
        loginButton = (Button) findViewById(R.id.main_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent i = new Intent(MainActivity.this, loginActivity.class);
                startActivity(i);
            }
        });
    }
}