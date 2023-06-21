package com.dbtapps.pocketbusiness;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SellPageItemRecyclerViewAdapter extends RecyclerView.Adapter<SellPageItemRecyclerViewAdapter.MyViewHolder>{

    ArrayList<InventoryItemModel> inventoryItems;
    Context context;

    public SellPageItemRecyclerViewAdapter(Context context, ArrayList<InventoryItemModel> inventoryItems){
        this.context = context;
        this.inventoryItems = inventoryItems;
    }

    @NonNull
    @Override
    public SellPageItemRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SellPageItemRecyclerViewAdapter.MyViewHolder holder, int position) {

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
