package com.dbtapps.pocketbusiness;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomDialogBox{

    Dialog bottomDialogBox;

    public BottomDialogBox(Context context, View v){

        bottomDialogBox = new Dialog(context);
        bottomDialogBox.setContentView(v);
        bottomDialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomDialogBox.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomDialogBox.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialogBox.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation;
        bottomDialogBox.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        bottomDialogBox.setCancelable(true);

    }

    public void showDialogBox(){bottomDialogBox.show();}

    public void dismissDialogBox(){bottomDialogBox.dismiss();}

}
