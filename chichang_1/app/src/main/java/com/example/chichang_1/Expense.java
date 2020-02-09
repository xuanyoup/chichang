package com.example.chichang_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Expense extends AppCompatActivity {
    ArrayList<String> selection = new ArrayList<String>();
    TextView final_text;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String current_user_id;
    private Button updatebutton,back;
    private boolean clean = false;
    private String firebasePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        final_text = (TextView) findViewById(R.id.final_result);
        final_text.setEnabled(false);
        back = findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Expense.this,CategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });
        updatebutton=findViewById(R.id.save_btn);
        updatebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clean =true;

                        firebaseFirestore = FirebaseFirestore.getInstance();
                        firebaseAuth = FirebaseAuth.getInstance();
                        current_user_id = firebaseAuth.getCurrentUser().getUid();
                        firebasePath = "Users/"+current_user_id+"/Expense";
                        if (clean){
                            firebaseFirestore.collection(firebasePath).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if (!queryDocumentSnapshots.isEmpty()){
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot l : list){
                                            Type type = l.toObject(Type.class);
                                            type.setTypename(l.getId());
                                            firebaseFirestore.collection(firebasePath).document(type.getTypename()).delete();
                                        }
                                    }
                                    finalSelection();
                                }
                            });

                        }
                clean = false;

            }
        });
    }

    public void selectItem(View view){
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.food:
                if (checked){
                    selection.add("Food");}
                else {
                    selection.remove("Food");
                }
                break;

            case R.id.clothing:
                if (checked){
                    selection.add("Clothing");}
                else {
                    selection.remove("Clothing");
                }
                break;

            case R.id.housing:
                if (checked){
                    selection.add("Housing");}
                else {
                    selection.remove("Housing");
                }
                break;

            case R.id.transportation:
                if (checked){
                    selection.add("Transportation");}
                else {
                    selection.remove("Transportation");
                }
                break;

            case R.id.education:
                if (checked){
                    selection.add("Education");}
                else {
                    selection.remove("Education");
                }
                break;

            case R.id.entertainment:
                if (checked){
                    selection.add("Entertainment");}
                else {
                    selection.remove("Entertainment");
                }
                break;
            case R.id.others:
                if (checked){
                    selection.add("Others");}
                else {
                    selection.remove("Others");
                }
                break;
        }
    }

    public void finalSelection(){

        String final_category_selection = "";

        for (String Selections : selection) {
            final_category_selection = final_category_selection + Selections + "\n";
            Map<String,Object> map = new HashMap<>();
            map.put("type",Selections);
            firebaseFirestore.collection(firebasePath).document(Selections).set(map);
        }



        final_text.setText(final_category_selection);
        final_text.setEnabled(true);
    }
}
