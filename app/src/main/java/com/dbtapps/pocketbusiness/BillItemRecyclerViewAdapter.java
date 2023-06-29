package com.dbtapps.pocketbusiness;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BillItemRecyclerViewAdapter extends RecyclerView.Adapter<BillItemRecyclerViewAdapter.MyViewHolder>{

    Context context;
    public BillItemRecyclerViewAdapter(Context context) {

        this.context = context;
        Log.d("Debug", "BillItemRecyclerViewAdapter: Constructor");

    }

    @NonNull
    @Override
    public BillItemRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bill_item_card, parent, false);

        Log.d("Debug","BillItemRecyclerViewAdapter: View Created");

        return new BillItemRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillItemRecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.itemName.setText(SellPage.sellList.get(position).name);
        holder.itemQuantity.setText(SellPage.sellListQuantityUnit.get(position).quantity + " " + SellPage.sellListQuantityUnit.get(position).unit);
        holder.itemPrice.setText("Rs." + SellPage.sellList.get(position).sell_price);

        Log.d("Debug","BillItemRecyclerViewAdapter: View Bind at position - "+ position);

    }

    @Override
    public int getItemCount() {
        Log.d("Debug","BillItemRecyclerViewAdapter: Get Item Count");
        return SellPage.sellList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView itemName, itemQuantity, itemPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemQuantity = (TextView) itemView.findViewById(R.id.item_quantity);
            itemPrice = (TextView) itemView.findViewById(R.id.item_price);
        }
    }
}
