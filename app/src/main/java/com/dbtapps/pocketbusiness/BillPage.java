package com.dbtapps.pocketbusiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class BillPage extends AppCompatActivity {

    ConstraintLayout sectionBillContainer;
    RecyclerView billItemRecyclerView;
    Context context;

    TextView totalAmount, amountPaid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_page);

        //INITIALIZATION ---------------------------------------------------------------------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        sectionBillContainer = (ConstraintLayout) findViewById(R.id.sectionBillContainer);
        billItemRecyclerView = (RecyclerView) findViewById(R.id.billPage_RecyclerView);
        context = this;

        totalAmount = findViewById(R.id.billPage_total_amount);
        amountPaid = findViewById(R.id.billPage_amount_paid);
        //INITIALIZATION END -----------------------------------------------------------------------

        //ANIMATION SECTION ------------------------------------------------------------------------
        sectionBillContainer.setAlpha(0);
        sectionBillContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(1);
        //ANIMATION SECTION END --------------------------------------------------------------------

        loadBillItems();
        calculateTotalPrice();
    }

    public void loadBillItems(){
        BillItemRecyclerViewAdapter adapter = new BillItemRecyclerViewAdapter(context);
        billItemRecyclerView.setAdapter(adapter);
        billItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("Debug","Bill Page: Bill item adapter loaded");
    }

    public void calculateTotalPrice(){

    }
}