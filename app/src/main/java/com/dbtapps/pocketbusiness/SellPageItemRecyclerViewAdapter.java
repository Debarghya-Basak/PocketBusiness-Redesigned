package com.dbtapps.pocketbusiness;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SellPageItemRecyclerViewAdapter extends RecyclerView.Adapter<SellPageItemRecyclerViewAdapter.MyViewHolder>{
    Context context;

    public SellPageItemRecyclerViewAdapter(Context context){
        this.context = context;

    }

    @NonNull
    @Override
    public SellPageItemRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.sell_page_item_card,parent, false);

        return new SellPageItemRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellPageItemRecyclerViewAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.nameSellPageItemCard.setText(SellPage.sellList.get(position).name);
        holder.costPriceSellPageItemCard.setText(SellPage.sellList.get(position).cost_price.get(SellPage.sellList.get(position).cost_price.size()-1) + "");
        holder.sellPriceSellPageItemCard.setText(SellPage.sellList.get(position).sell_price + "");
        holder.quantitySellPageItemCard.setText(SellPage.sellList.get(position).quantity.get(SellPage.sellList.get(position).cost_price.size()-1) + "");
        holder.unitSellPageItemCard.setText(SellPage.sellList.get(position).unit+"");
        holder.idSellPageItemCard.setText(SellPage.sellList.get(position).id+"");
        holder.sellPageSellQuantity.setText(SellPage.sellListQuantityUnit.get(position).quantity+"");
        holder.sellPageSellUnit.setText(SellPage.sellListQuantityUnit.get(position).unit);

        holder.deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogBoxView = inflater.inflate(R.layout.confirm_delete_inventory_item_dialog_box, null);

                Button confirmDelete = (Button) dialogBoxView.findViewById(R.id.confirmDelete);
                Button rejectDelete = (Button) dialogBoxView.findViewById(R.id.rejectDelete);

                BottomDialogBox bottomDialogBox = new BottomDialogBox(context, dialogBoxView);
                bottomDialogBox.showDialogBox();

                confirmDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Debug", "Holder position : " + holder.getAdapterPosition() + ", position : " + position + ", SellList Item name : " + SellPage.sellList.get(position).name);

                        SellPage.sellList.remove(position);
                        SellPage.sellListQuantityUnit.remove(position);
                        notifyDataSetChanged();

                        Log.d("Debug", "Removed");

                        bottomDialogBox.dismissDialogBox();
                    }
                });

                rejectDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomDialogBox.dismissDialogBox();
                    }
                });



            }
        });

        holder.editCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, SellPage.sellList.get(position).name + " : Edit Button is Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        
    }

    @Override
    public int getItemCount() {
        return SellPage.sellList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nameSellPageItemCard, costPriceSellPageItemCard, sellPriceSellPageItemCard, quantitySellPageItemCard, unitSellPageItemCard, idSellPageItemCard, sellPageSellQuantity, sellPageSellUnit;

        MaterialButton deleteCard, editCard;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameSellPageItemCard = (TextView) itemView.findViewById(R.id.sellPage_ItemName);
            costPriceSellPageItemCard = (TextView) itemView.findViewById(R.id.sellPage_ItemCostPrice);
            sellPriceSellPageItemCard = (TextView) itemView.findViewById(R.id.sellPage_ItemSellPrice);
            quantitySellPageItemCard = (TextView) itemView.findViewById(R.id.sellPage_ItemQuantity);
            unitSellPageItemCard = (TextView) itemView.findViewById(R.id.sellPage_ItemUnit);
            idSellPageItemCard = (TextView) itemView.findViewById(R.id.sellPage_ItemID);
            sellPageSellQuantity = (TextView) itemView.findViewById(R.id.sellPage_ItemSellQuantity);
            sellPageSellUnit = (TextView) itemView.findViewById(R.id.sellPage_ItemSellUnit);

            deleteCard = (MaterialButton) itemView.findViewById(R.id.sellPage_ItemDeleteButton);
            editCard = (MaterialButton) itemView.findViewById(R.id.sellPage_ItemEditButton);

        }
    }

}
