package com.dbtapps.pocketbusiness;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SellPageBottomDialogBoxItemRecyclerViewAdapter extends RecyclerView.Adapter<SellPageBottomDialogBoxItemRecyclerViewAdapter.MyViewHolder>{

    ArrayList<InventoryItemModel> inventoryItems;
    Context context;

    public SellPageBottomDialogBoxItemRecyclerViewAdapter(Context context, ArrayList<InventoryItemModel> inventoryItems){
        this.context = context;
        this.inventoryItems = inventoryItems;
    }


    @NonNull
    @Override
    public SellPageBottomDialogBoxItemRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SellPageBottomDialogBoxItemRecyclerViewAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
