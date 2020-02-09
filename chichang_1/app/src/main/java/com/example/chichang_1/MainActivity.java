package com.example.chichang_1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;



    TextView label_top;
    TextView label_middle;
    private  String Date;
    TextView invisible;

    BottomNavigationView bottomNavigationView;

    TextView date;
    DatePickerDialog.OnDateSetListener setListener;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        label_top = findViewById(R.id.label_top);
        label_middle = findViewById(R.id.lable_middle);
        invisible = findViewById(R.id.invisible_date);
        //invisible.setVisibility(View.INVISIBLE);

        bottomNavigationView = findViewById(R.id.main_nav);


        // show instant date
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMM/d", Locale.US);

        String strDate = sdf.format(cal.getTime());

        // split() 分割字串
        String[] values1 = strDate.split("/");

        label_top.setText(values1[1] + " " + values1[0]);
        label_middle.setText(values1[2]);
        sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        strDate = sdf.format(cal.getTime());
        invisible.setText(strDate);



        // set home select
        bottomNavigationView.setSelectedItemId(R.id.home);
        // perform itemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        return true;

                    case R.id.graph:
                        Intent intent = new Intent(getApplicationContext(), GraphActivity.class);
                        intent.putExtra("date",invisible.getText().toString());
                        startActivity(intent);

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



        // create datepicker
        date = findViewById(R.id.select_date);


        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "/" + month + "/" + dayOfMonth;
                Date = date;
                String[] values2 = date.split("/");

                switch (month){
                    case 1:
                        label_top.setText("Jan" + " "+ values2[0]);
                        break;
                    case 2:
                        label_top.setText("Feb" + " "+ values2[0]);
                        break;
                    case 3:
                        label_top.setText("Mar" + " "+ values2[0]);
                        break;
                    case 4:
                        label_top.setText("Apr" + " "+ values2[0]);
                        break;
                    case 5:
                        label_top.setText("May" + " "+ values2[0]);
                        break;
                    case 6:
                        label_top.setText("Jun" + " "+ values2[0]);
                        break;
                    case 7:
                        label_top.setText("Jul" + " "+ values2[0]);
                        break;
                    case 8:
                        label_top.setText("Aug" + " "+ values2[0]);
                        break;
                    case 9:
                        label_top.setText("Sep" + " "+ values2[0]);
                        break;
                    case 10:
                        label_top.setText("Oct" + " "+ values2[0]);
                        break;
                    case 11:
                        label_top.setText("Nov" + " "+ values2[0]);
                        break;
                    case 12:
                        label_top.setText("Dec" + " "+ values2[0]);
                        break;
                }
                label_middle.setText(values2[2]);
                invisible.setText(Date);

            }
        };



        label_middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCostActivity();
            }
        });
    }
    public void openCostActivity(){
        Intent intent = new Intent(this, CostActivity.class);
        intent.putExtra("date",invisible.getText().toString());
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    // 覆寫Activity
    @Override
    protected void onStop(){
        super.onStop();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            // No user is signed in

            sendToLogin();
        } else {
            // User is  signed in
        }
    }

    private void sendToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
