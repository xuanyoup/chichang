package com.example.chichang_1;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {
    private String date;
    private Button AddNewIncome;
    CostActivity costActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_income, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_addincome, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.addIncome){
            Intent intent = new Intent(this.getActivity(), AddActivity.class);
            boolean expense = true;
            intent.putExtra("isIncome",expense );
            costActivity= (CostActivity)this.getActivity();
            date = costActivity.date;
            intent.putExtra("date",date);
            startActivity(intent);
            return true;
        }
        else{
            Toast.makeText(getActivity(), "Clicked on" + item.getItemId(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
