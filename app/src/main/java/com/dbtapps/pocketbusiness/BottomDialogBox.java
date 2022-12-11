package com.dbtapps.pocketbusiness;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

public class BottomDialogBox {

    Dialog bottomDialogBox;

    public BottomDialogBox(Context context, View v){

        bottomDialogBox = new Dialog(context);
        bottomDialogBox.setContentView(v);
        bottomDialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomDialogBox.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomDialogBox.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialogBox.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation;
        bottomDialogBox.setCancelable(true);

    }

    public void showDialogBox(){
        bottomDialogBox.show();
    }

    public void dismissDialogBox(){
        bottomDialogBox.dismiss();
    }

}
