package com.example.chichang_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ColorActivity extends AppCompatActivity {

    Toolbar mToolbar;
    Button mRed;
    Button mBlack;
    Button mDefault;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        mToolbar = findViewById(R.id.toolbar);
        mRed = findViewById(R.id.btnRed);
        mBlack = findViewById(R.id.btnBlack);
        mDefault = findViewById(R.id.btnDefault);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.mipmap.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getParent().onBackPressed();
            }
        });

        //mToolbar.setTitle(getResources().getString(R.string.app_name));

        if (getColor() != getResources().getColor(R.color.colorPrimary)) {
            mToolbar.setBackgroundColor(getColor());
            getWindow().setStatusBarColor(getColor());
        }

        mRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorRed));
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorRed));
                storeColor(getResources().getColor(R.color.colorRed));
            }
        });

        mBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorBlack));
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorBlack));
                storeColor(getResources().getColor(R.color.colorBlack));
            }
        });

        mDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                storeColor(getResources().getColor(R.color.colorPrimary));
            }
        });
    }

    private void storeColor(int color) {
        SharedPreferences mSharedPreferences = getSharedPreferences("ToolbarColor", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt("color", color);
        mEditor.apply();
    }

    private int getColor() {
        SharedPreferences mSharedPreferences = getSharedPreferences("ToolbarColor", MODE_PRIVATE);
        int selectedColor = mSharedPreferences.getInt("color", getResources().getColor(R.color.colorPrimary));
        return selectedColor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logging_out:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                Logout();
                break;
            case R.id.choice:
                //Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
    public void Logout(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(ColorActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
