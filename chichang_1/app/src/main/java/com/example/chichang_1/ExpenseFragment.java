package com.example.chichang_1;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {

    ListView listView;
    String date;
    CostActivity costActivity ;
    //RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 為了讓每個fragment有不同的optionmenu
        setHasOptionsMenu(true);



        return inflater.inflate(R.layout.fragment_expense, container, false);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_addexpense, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.addExpense){
            Intent intent = new Intent(this.getActivity(), AddActivity.class);
            boolean expense = false;
            intent.putExtra("isIncome",expense );
            costActivity= (CostActivity)this.getActivity();
            date = costActivity.date;
            intent.putExtra("date",date);
            startActivity(intent);
            //Toast.makeText(this.getActivity(),date,Toast.LENGTH_LONG).show();

        }
        return true;
    }






}
