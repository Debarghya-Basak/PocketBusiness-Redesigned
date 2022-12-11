package com.dbtapps.pocketbusiness;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;

public class LoadingDialogBox {
    Dialog loadingDialogBox;

    public LoadingDialogBox(Context context){

        loadingDialogBox = new Dialog(context);
        loadingDialogBox.setContentView(R.layout.loading_dialog_box);
        loadingDialogBox.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.global_container_border));
        loadingDialogBox.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialogBox.getWindow().getAttributes().windowAnimations = R.style.loadingDialogAnimation;
        loadingDialogBox.setCancelable(false);

    }

    public void showDialogBox(){
        loadingDialogBox.show();
    }

    public void dismissDialogBox(){
        loadingDialogBox.dismiss();
    }

}
