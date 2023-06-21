package com.dbtapps.pocketbusiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

public class SellPage extends AppCompatActivity {

    ConstraintLayout sectionSellContainer;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_page);

        //INITIALIZATION ---------------------------------------------------------------------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        sectionSellContainer = (ConstraintLayout) findViewById(R.id.sectionSellContainer);

        context = this;
        //INITIALIZATION END -----------------------------------------------------------------------

        //ANIMATION SECTION ------------------------------------------------------------------------
        sectionSellContainer.setAlpha(0);
        sectionSellContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(1);
        //ANIMATION SECTION END --------------------------------------------------------------------

    }

    public void scanQRAddItemToSell(View v){

        //TODO: Make scanQRAddItemToSell and delete this temporary function
        Intent intent = new Intent(SellPage.this, DashboardPage.class);
        startActivity(intent);

    }

    public void addItemManually(View v){
        LayoutInflater inflater = getLayoutInflater();
        View bottomDialogBoxView = inflater.inflate(R.layout.add_manually_sell_page_dialog_box, null);

        BottomDialogBox bottomDialogBox = new BottomDialogBox(this, bottomDialogBoxView);
        bottomDialogBox.showDialogBox();

    }
}