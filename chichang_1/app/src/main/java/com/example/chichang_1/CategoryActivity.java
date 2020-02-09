package com.example.chichang_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CategoryActivity extends AppCompatActivity {
    private Button expense;
    private Button income;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        expense = (Button) findViewById(R.id.expense);
        income = (Button) findViewById(R.id.income);
        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(CategoryActivity.this, Expense.class);
                startActivity(intent);
            }
        });
        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(CategoryActivity.this, Income.class);
                startActivity(intent);
            }
        });

        // initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_nav);
        // set home select
        bottomNavigationView.setSelectedItemId(R.id.setup);
        // perform itemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.graph:
                        startActivity(new Intent(getApplicationContext()
                                , GraphActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.setup:
                        startActivity(new Intent(getApplicationContext()
                                , SetupActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}