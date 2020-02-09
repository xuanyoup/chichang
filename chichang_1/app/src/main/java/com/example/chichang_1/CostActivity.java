package com.example.chichang_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CostActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    TabItem tabExpense;
    TabItem tabIncome;
    TabItem tabTotal;
    public String date;
    TextView title;
    RecyclerView recyclerView;
    private FirebaseFirestore db ;
    private FirebaseUser currentUser;
    private PostAdapter adapter;
    private ArrayList<Post> postArrayList ;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);

        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent  =new Intent(CostActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        date = getIntent().getStringExtra("date");
        title.setText(date);

        tabLayout = findViewById(R.id.tablayout);
        tabExpense = findViewById(R.id.tabExpense);
        tabIncome = findViewById(R.id.tabIncome);
        tabTotal = findViewById(R.id.tabTotal);
        viewPager = findViewById(R.id.viewPager);
        recyclerView = findViewById(R.id.recycler);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        postArrayList = new ArrayList<>();
        adapter = new PostAdapter(this, postArrayList);
        recyclerView.setAdapter(adapter);
        db= FirebaseFirestore.getInstance();
        db.collection("Posts").whereEqualTo("user_id",currentUser.getUid()).whereEqualTo("date",date).
                whereEqualTo("income",false).orderBy("item_name").
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    //PiePass = true;
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        Post p = d.toObject(Post.class);
                        p.setId(d.getId());
                        postArrayList.add(p);
                    }

                    adapter.notifyDataSetChanged();
                }
            }
        });

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout){

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setupList(position);
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));


    }

    private void setupList(int pos) {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        postArrayList = new ArrayList<>();
        adapter = new PostAdapter(this, postArrayList);
        recyclerView.setAdapter(adapter);
        db= FirebaseFirestore.getInstance();
        switch (pos){
            case 0:
                db.collection("Posts").whereEqualTo("user_id",currentUser.getUid()).whereEqualTo("date",date).
                        whereEqualTo("income",false).orderBy("item_name").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            //PiePass = true;
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Post p = d.toObject(Post.class);
                                p.setId(d.getId());
                                postArrayList.add(p);
                            }

                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                break;
            case 1:
                db.collection("Posts").whereEqualTo("user_id",currentUser.getUid()).whereEqualTo("date",date).
                        whereEqualTo("income",true).orderBy("item_name").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            //PiePass = true;
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Post p = d.toObject(Post.class);
                                p.setId(d.getId());
                                postArrayList.add(p);
                            }
                            adapter.notifyDataSetChanged();

                        }
                    }
                });
                break;
            case 2:
                db.collection("Posts").whereEqualTo("user_id",currentUser.getUid()).whereEqualTo("date",date).orderBy("item_name").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            //PiePass = true;
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Post p = d.toObject(Post.class);
                                p.setId(d.getId());
                                postArrayList.add(p);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                break;
        }

    }

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

}
