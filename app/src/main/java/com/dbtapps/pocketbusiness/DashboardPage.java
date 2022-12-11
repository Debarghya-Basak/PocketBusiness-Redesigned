package com.dbtapps.pocketbusiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

public class DashboardPage extends AppCompatActivity {

    ConstraintLayout sectionDashboardContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_page);

        //INITIALIZATION ---------------------------------------------------------------------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        sectionDashboardContainer = (ConstraintLayout) findViewById(R.id.sectionDashboardContainer);
        //INITIALIZATION END -----------------------------------------------------------------------

        //ANIMATION SECTION ------------------------------------------------------------------------
        sectionDashboardContainer.setAlpha(0);
        sectionDashboardContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(1);
        //ANIMATION SECTION END --------------------------------------------------------------------

    }

    public void startInventoryPage(View view){
        Intent intent = new Intent(DashboardPage.this, InventoryPage.class);

        //ANIMATION SECTION ------------------------------------------------------------------------
        sectionDashboardContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(0);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        },MainActivity.GLOBAL_FADE_ANIMATION_DURATION);
        //ANIMATION SECTION END --------------------------------------------------------------------

    }

    public void startSellPage(View v){
        Intent intent = new Intent(DashboardPage.this, SellPage.class);

        //ANIMATION SECTION ------------------------------------------------------------------------
        sectionDashboardContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(0);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        },MainActivity.GLOBAL_FADE_ANIMATION_DURATION);
        //ANIMATION SECTION END --------------------------------------------------------------------
    }

    @Override
    public void onBackPressed() {

        //ANIMATION SECTION ------------------------------------------------------------------------
        sectionDashboardContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(0);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },MainActivity.GLOBAL_FADE_ANIMATION_DURATION);
        //ANIMATION SECTION END --------------------------------------------------------------------
    }

    @Override
    protected void onResume() {
        super.onResume();

        sectionDashboardContainer.setAlpha(0);
        sectionDashboardContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(1);
    }
}