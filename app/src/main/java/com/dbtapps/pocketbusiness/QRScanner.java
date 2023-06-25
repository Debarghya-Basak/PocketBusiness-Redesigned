package com.dbtapps.pocketbusiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class QRScanner extends AppCompatActivity {

    private CodeScanner mCodeScanner;

    private boolean permissionFLAG = false;

    private final int PERMISSION_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);

        //INITIALIZATION----------------------------------------------------------------------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //INITIALIZATION END------------------------------------------------------------------------


        checkCameraPermission();
        createQRScanner();

    }

    private void createQRScanner() {

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(QRScanner.this, result.getText(), Toast.LENGTH_SHORT).show();
                        Log.d("Debug" , "QR Code message : " + result.getText());
                        if(checkValidIntQR(result.getText())) {
                            Log.d("Debug", "QRScanner Page: Valid QR");
                            SellPage.QRCODE = Integer.parseInt(result.getText());
                        }
                        onBackPressed();
                    }
                });
            }
        });

    }

    private boolean checkValidIntQR(String text) {
        for(int i=0;i<text.length();i++){
            if("0123456789".indexOf(text.charAt(i)) == -1)
                return false;
        }
        return true;
    }

    private void checkCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            permissionFLAG = true;
        else
            askUserPermission();

    }

    public void askUserPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    permissionFLAG = true;
                }
                else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    permissionFLAG = false;
                }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(permissionFLAG){
            Log.d("Debug", "OnResume");
            mCodeScanner.startPreview();
        }

    }

    @Override
    protected void onPause() {
        if(permissionFLAG){
            Log.d("Debug", "OnPause");
            mCodeScanner.releaseResources();
        }
        else{
            Log.d("Debug","OnPause permissionFlag false");
        }
        super.onPause();
    }

}