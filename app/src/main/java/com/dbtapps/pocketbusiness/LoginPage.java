package com.dbtapps.pocketbusiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

import java.util.Objects;

public class LoginPage extends AppCompatActivity {

    ConstraintLayout sectionLoginRegisterContainer;
    Button loginSubmitButton;
    TextInputEditText editTextEmailAddress;
    TextInputEditText editTextPassword;
    TextInputLayout editTextContainerEmailAddress;
    TextInputLayout editTextContainerPassword;

    LoadingDialogBox loadingDialogBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //INITIALIZATION ---------------------------------------------------------------------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        loginSubmitButton = (Button) findViewById(R.id.loginSubmitButton);
        editTextEmailAddress = (TextInputEditText) findViewById(R.id.editTextEmailAddress);
        editTextPassword = (TextInputEditText) findViewById(R.id.editTextPassword);
        editTextContainerEmailAddress = (TextInputLayout) findViewById(R.id.editTextContainerEmailAddress);
        editTextContainerPassword = (TextInputLayout) findViewById(R.id.editTextContainerPassword);

        sectionLoginRegisterContainer = (ConstraintLayout) findViewById(R.id.sectionLoginRegisterContainer);

        loadingDialogBox = new LoadingDialogBox(this);
        //INITIALIZATION END -----------------------------------------------------------------------

        //ANIMATION SECTION ------------------------------------------------------------------------
        sectionLoginRegisterContainer.setAlpha(0);
        sectionLoginRegisterContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(1);
        //ANIMATION SECTION END --------------------------------------------------------------------

        //RUNNING ----------------------------------------------------------------------------------
        editTextEmailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextContainerEmailAddress.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextContainerPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //RUNNING END-------------------------------------------------------------------------------
    }

    public boolean checkForError(String email, String password){

        boolean flag = true;
        String[] errorText = {"Field cannot be empty"
                ,"Password too small"
                ,"Not a valid email address"
        };

        if(email.length() == 0){
            editTextContainerEmailAddress.setError(errorText[0]);
            flag=false;
        }

        if(password.length() == 0){
            editTextContainerPassword.setError(errorText[0]);
            flag=false;
        }
        else if (password.length()<8) {
            editTextContainerPassword.setError(errorText[1]);
            flag=false;
        }

        return flag;
    }

    public void checkAllDetails(View view){

        String email = editTextEmailAddress.getText().toString();
        String password = editTextPassword.getText().toString();

        if(!checkForError(email,password)){
            return;
        }

        loadingDialogBox.showDialogBox();

        MainActivity.firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(LoginPage.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                    MainActivity.userID = MainActivity.firebaseAuth.getUid();
                    loadingDialogBox.dismissDialogBox();
                    startDashboardPage();
                }
                else {
                    loadingDialogBox.dismissDialogBox();
                    Log.d("GlobalDebugLog", task.getException().getLocalizedMessage());
                    Toast.makeText(LoginPage.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void startDashboardPage(){
        Intent intent = new Intent(LoginPage.this, DashboardPage.class);

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

    @Override
    public void onBackPressed() {
        sectionLoginRegisterContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(0);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, MainActivity.GLOBAL_FADE_ANIMATION_DURATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sectionLoginRegisterContainer.setAlpha(0);
        sectionLoginRegisterContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(1);
    }
}