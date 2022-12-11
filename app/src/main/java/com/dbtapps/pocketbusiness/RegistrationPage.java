package com.dbtapps.pocketbusiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

import org.w3c.dom.Text;

public class RegistrationPage extends AppCompatActivity {

    ConstraintLayout sectionLoginRegisterContainer;
    TextInputLayout editTextContainerName, editTextContainerPhoneNumber, editTextContainerBusinessName
            ,editTextContainerBusinessAddress, editTextContainerBusinessDescription, editTextContainerBusinessCity
            ,editTextContainerBusinessPincode, editTextContainerBusinessPhoneNumber, editTextContainerEmailAddress
            ,editTextContainerPassword;
    TextInputEditText editTextName, editTextPhoneNumber, editTextBusinessName
            ,editTextBusinessAddress, editTextBusinessDescription, editTextBusinessCity
            ,editTextBusinessPincode, editTextBusinessPhoneNumber, editTextEmailAddress
            ,editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        //INITIALIZATION ---------------------------------------------------------------------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        sectionLoginRegisterContainer = (ConstraintLayout) findViewById(R.id.sectionLoginRegisterContainer);

        editTextContainerName = (TextInputLayout) findViewById(R.id.editTextContainerName);
        editTextContainerPhoneNumber = (TextInputLayout) findViewById(R.id.editTextContainerPhoneNumber);
        editTextContainerBusinessName = (TextInputLayout) findViewById(R.id.editTextContainerBusinessName);
        editTextContainerBusinessAddress = (TextInputLayout) findViewById(R.id.editTextContainerBusinessAddress);
        editTextContainerBusinessDescription = (TextInputLayout) findViewById(R.id.editTextContainerBusinessDescription);
        editTextContainerBusinessCity = (TextInputLayout) findViewById(R.id.editTextContainerBusinessCity);
        editTextContainerBusinessPincode = (TextInputLayout) findViewById(R.id.editTextContainerBusinessPincode);
        editTextContainerBusinessPhoneNumber = (TextInputLayout) findViewById(R.id.editTextContainerBusinessPhoneNumber);
        editTextContainerEmailAddress = (TextInputLayout) findViewById(R.id.editTextContainerEmailAddress);
        editTextContainerPassword = (TextInputLayout) findViewById(R.id.editTextContainerPassword);

        editTextName = (TextInputEditText) findViewById(R.id.editTextName);
        editTextPhoneNumber = (TextInputEditText) findViewById(R.id.editTextPhoneNumber);
        editTextBusinessName = (TextInputEditText) findViewById(R.id.editTextBusinessName);
        editTextBusinessAddress = (TextInputEditText) findViewById(R.id.editTextBusinessAddress);
        editTextBusinessDescription = (TextInputEditText) findViewById(R.id.editTextBusinessDescription);
        editTextBusinessCity = (TextInputEditText) findViewById(R.id.editTextBusinessCity);
        editTextBusinessPincode = (TextInputEditText) findViewById(R.id.editTextBusinessPincode);
        editTextBusinessPhoneNumber = (TextInputEditText) findViewById(R.id.editTextBusinessPhoneNumber);
        editTextEmailAddress = (TextInputEditText) findViewById(R.id.editTextEmailAddress);
        editTextPassword = (TextInputEditText) findViewById(R.id.editTextPassword);

        //INITIALIZATION END -----------------------------------------------------------------------

        //ANIMATION SECTION ------------------------------------------------------------------------
        sectionLoginRegisterContainer.setAlpha(0);
        sectionLoginRegisterContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(1);
        //ANIMATION SECTION END --------------------------------------------------------------------
    }

    public boolean checkForError(){
        return true;
    }

    public void createUserWithAllDetails(View view){

        //TODO : The data below is sample data and needs to be replaced by the data in edit text
        String name = "Sample Data";
        int phone_number = 1234567890;
        String business_name = "Sample Data";
        String business_address = "Sample Sample Sample Sample";
        String business_description = "Sample Sample Sample Sample";
        String business_city = "Sample Data";
        int business_pincode = 123456;
        int business_phone_number = 1234567890;

        String email_address = editTextEmailAddress.getText().toString();
        String password = editTextPassword.getText().toString();

        MainActivity.firebaseAuth.createUserWithEmailAndPassword(email_address,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    MainActivity.userID = MainActivity.firebaseAuth.getUid();

                    UserData user_details = new UserData(name, phone_number,business_address, business_city, business_description, business_name, business_phone_number, business_pincode,email_address, password);
                    MainActivity.firebaseDatabase.getReference().child("users").child(MainActivity.userID).setValue(user_details);

                    Toast.makeText(RegistrationPage.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(RegistrationPage.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


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
}