package com.dbtapps.pocketbusiness;

import android.content.Context;
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

    ArrayList<InventoryItemModel> inventoryItems;
    ArrayList<SellListQuantityUnitModel> sellQuantityUnitModel;
    Context context;

    public SellPageItemRecyclerViewAdapter(Context context, ArrayList<InventoryItemModel> inventoryItems, ArrayList<SellListQuantityUnitModel> sellQuantityUnitModel){
        this.context = context;
        this.inventoryItems = inventoryItems;
        this.sellQuantityUnitModel = sellQuantityUnitModel;
    }

    @NonNull
    @Override
    public SellPageItemRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.sell_page_item_card,parent, false);

        return new SellPageItemRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellPageItemRecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.nameSellPageItemCard.setText(inventoryItems.get(position).name);
        holder.costPriceSellPageItemCard.setText(inventoryItems.get(position).cost_price.get(inventoryItems.get(position).cost_price.size()-1) + "");
        holder.sellPriceSellPageItemCard.setText(inventoryItems.get(position).sell_price + "");
        holder.quantitySellPageItemCard.setText(inventoryItems.get(position).quantity.get(inventoryItems.get(position).cost_price.size()-1) + "");
        holder.unitSellPageItemCard.setText(inventoryItems.get(position).unit+"");
        holder.idSellPageItemCard.setText(inventoryItems.get(position).id+"");
        holder.sellPageSellQuantity.setText(sellQuantityUnitModel.get(position).quantity+"");
        holder.sellPageSellUnit.setText(sellQuantityUnitModel.get(position).unit);

        holder.deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, inventoryItems.get(position).name + " : Delete Button is pressed", Toast.LENGTH_SHORT).show();
            }
        });

        holder.editCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, inventoryItems.get(position).name + " : Edit Button is Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        
    }

    @Override
    public int getItemCount() {
        return inventoryItems.size();
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
