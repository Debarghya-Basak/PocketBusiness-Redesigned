package com.dbtapps.pocketbusiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class AuthenticationPage extends AppCompatActivity {

    Animation  fadeIn, scrollInFromBottom;
    ConstraintLayout sectionLoginRegisterContainer;
    View appNameBackgroundBlur, backgroundBlur, sectionLoginRegister;
    TextView appName, welcomeText;
    Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_page);

        //INITIALIZATION ---------------------------------------------------------------------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        sectionLoginRegisterContainer = (ConstraintLayout) findViewById(R.id.sectionLoginRegisterContainer);

        appNameBackgroundBlur = (View) findViewById(R.id.appNameBackgroundBlur);
        backgroundBlur = (View) findViewById(R.id.backgroundBlur);
        sectionLoginRegister = (View) findViewById(R.id.sectionLoginRegister);

        appName = (TextView) findViewById(R.id.appName);
        welcomeText = (TextView) findViewById(R.id.welcomeText);

        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);

        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        scrollInFromBottom = AnimationUtils.loadAnimation(this, R.anim.faded_slide_in_from_bottom);

        //INITIALIZATION END -----------------------------------------------------------------------


        //ANIMATION SECTION ------------------------------------------------------------------------
        appNameBackgroundBlur.setAlpha(0);
        backgroundBlur.setAlpha(0);
        sectionLoginRegister.setAlpha(0);
        appName.setAlpha(0);
        welcomeText.setAlpha(0);
        loginButton.setAlpha(0);
        registerButton.setAlpha(0);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation();
            }
        },300);
        //ANIMATION SECTION END --------------------------------------------------------------------
    }

    public void startLoginPage(View view){
        Intent intent = new Intent(AuthenticationPage.this, LoginPage.class);

        //ANIMATION SECTION ------------------------------------------------------------------------
        sectionLoginRegisterContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(0);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        },MainActivity.GLOBAL_FADE_ANIMATION_DURATION);
        //ANIMATION SECTION END --------------------------------------------------------------------
    }

    public void startRegistrationPage(View view){
        Intent intent = new Intent(AuthenticationPage.this, RegistrationPage.class);

        //ANIMATION SECTION ------------------------------------------------------------------------
        sectionLoginRegisterContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(0);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        },MainActivity.GLOBAL_FADE_ANIMATION_DURATION);
        //ANIMATION SECTION END --------------------------------------------------------------------
    }

    public void startAnimation(){
        //ANIMATION SECTION ------------------------------------------------------------------------
        appNameBackgroundBlur.setAlpha(1);
        backgroundBlur.setAlpha(1);
        appNameBackgroundBlur.startAnimation(fadeIn);
        backgroundBlur.startAnimation(fadeIn);

        sectionLoginRegister.setAlpha(1);
        appName.setAlpha(1);
        welcomeText.setAlpha(1);
        loginButton.setAlpha(1);
        registerButton.setAlpha(1);

        appName.startAnimation(scrollInFromBottom);
        sectionLoginRegister.startAnimation(scrollInFromBottom);
        welcomeText.startAnimation(scrollInFromBottom);
        loginButton.startAnimation(scrollInFromBottom);
        registerButton.startAnimation(scrollInFromBottom);
        //ANIMATION SECTION END --------------------------------------------------------------------
    }

    @Override
    protected void onResume() {
        super.onResume();
        sectionLoginRegisterContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}