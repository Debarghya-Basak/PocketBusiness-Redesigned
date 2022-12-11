/*

MY NAME IS DEBARGHYA BASAK
DEVELOPER OF THIS APP
STUDYING IN UNIVERSITY OF ENGINEERING AND MANAGEMENT KOLKATA
2020-2024

I HAVE NOT MADE THIS APP TOTALLY PERFECT
I HAVE GIVEN MANY TYPES OF SOLUTION TO A PROBLEM
ALL THE SOLUTIONS ARE NOT PERFECTLY DONE BECAUSE I AM STILL LEARNING
I TRIED MY BEST TO SOLVE THE SOLUTIONS IN MY WAY

 */


package com.dbtapps.pocketbusiness;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    //GLOBAL VARIABLES -----------------------------------------------------------------------------
    public static int GLOBAL_FADE_ANIMATION_DURATION = 200;
    public static String DATABASELINK = "https://pocketbusiness-dbtapps-default-rtdb.firebaseio.com/";
    public static FirebaseAuth firebaseAuth;
    public static FirebaseDatabase firebaseDatabase;
    public static String userID = "";
    //GLOBAL VARIABLES END -------------------------------------------------------------------------

    Animation scaledFadeIn;
    ImageView pocketBusinessLogo, pocketBusinessLogoBackground;
    long SPLASH_SCREEN_DURATION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INITIALIZATION ---------------------------------------------------------------------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        pocketBusinessLogo = (ImageView) findViewById(R.id.pocketbusinesslogo);
        pocketBusinessLogoBackground = (ImageView) findViewById(R.id.pocketbusinesslogobackground);

        scaledFadeIn = AnimationUtils.loadAnimation(this,R.anim.scaled_fade_in);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance(DATABASELINK);

        //INITIALIZATION END -----------------------------------------------------------------------


        //ANIMATION SECTION ------------------------------------------------------------------------
        pocketBusinessLogo.setAnimation(scaledFadeIn);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, AuthenticationPage.class);

            Pair[] pairs = new Pair[2];
            pairs[1] = new Pair<View,String>(pocketBusinessLogo, "pocketbusinesslogoanimation");
            pairs[0] = new Pair<View, String>(pocketBusinessLogoBackground, "pocketbusinesslogoanimationbackground");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
            startActivity(intent, options.toBundle());
        }, SPLASH_SCREEN_DURATION);

        handler.postDelayed(() -> {
            finish();
        }, SPLASH_SCREEN_DURATION+1000);
        //ANIMATION SECTION END --------------------------------------------------------------------

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        headerImage = (ImageView) findViewById(R.id.headerImage);
//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
//        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
//        int pxHeight = displayMetrics.heightPixels;
//        int pxWidth = displayMetrics.widthPixels;
//        Toast.makeText(this, "Screen Height : " + dpHeight, Toast.LENGTH_SHORT).show();
//        //screen Height - 205 will be the marginBottom attribute of the image
//        headerImage.setTranslationY(256 - (displayMetrics.heightPixels/2));
//        headerImage = (ImageView) findViewById(R.id.headerImage);
    }
}