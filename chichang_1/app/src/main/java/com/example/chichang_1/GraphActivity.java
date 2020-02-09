package com.example.chichang_1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.achartengine.GraphicalView;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;

    private boolean income;
    private String date;
    private FirebaseFirestore db ;
    private FirebaseUser currentUser;
    private ArrayList<Post> postArrayList ;
    private ProgressBar progressBar;
    private LinearLayout chartContainer;
    private Context context;
    private ToggleButton IE_switch;
    private TextView pieDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        income =  getIntent().getBooleanExtra("Income",false);
        date = getIntent().getStringExtra("date");
        pieDate = findViewById(R.id.pieDate);
        pieDate.setText(date);
        progressBar = findViewById(R.id.PieProgress);
        progressBar.setVisibility(View.VISIBLE);
        context = this;
        db= FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        postArrayList = new ArrayList<Post>();
        chartContainer = findViewById(R.id.pielayout);
        //postArrayList = getList(db,currentUser,date,income);
        showPie();
        IE_switch=findViewById(R.id.IE_swtich);
        IE_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IE_switch.isChecked()){
                    progressBar.setVisibility(View.VISIBLE);
                    income = true;
                    showPie();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    income = false;
                    showPie();
                }
            }
        });


        // initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_nav);
        // set home select
        bottomNavigationView.setSelectedItemId(R.id.graph);
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tap again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    public void showPie(){
        db.collection("Posts").whereEqualTo("user_id",currentUser.getUid()).whereEqualTo("date",date).
                whereEqualTo("income",income).orderBy("item_name").
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        Post p = d.toObject(Post.class);
                        p.setId(d.getId());
                        postArrayList.add(p);
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(context,"Pie is ready!",Toast.LENGTH_LONG).show();
                    GraphicalView chartView  = PieChartView.getNewInstance(context, postArrayList );

                    //GraphicalView chartView  = PieChartView.getNewInstance(this, 100,60 );
                    chartContainer.removeAllViews();
                    chartContainer.addView(chartView);

                }else {
                    chartContainer.removeAllViews();
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(context,"God damn it. U still haven't add anytihing!",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }
}
