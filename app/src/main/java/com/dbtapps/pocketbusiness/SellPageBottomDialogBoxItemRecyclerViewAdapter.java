package com.dbtapps.pocketbusiness;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class SellPageBottomDialogBoxItemRecyclerViewAdapter extends RecyclerView.Adapter<SellPageBottomDialogBoxItemRecyclerViewAdapter.MyViewHolder>{

    ArrayList<InventoryItemModel> inventoryItems;
    Context context;

    RecyclerView recyclerView;
    SearchView searchView;
    InventoryItemModel inventoryItemModel;

    public SellPageBottomDialogBoxItemRecyclerViewAdapter(Context context, ArrayList<InventoryItemModel> inventoryItems, RecyclerView recyclerView, SearchView searchView){
        this.context = context;
        this.inventoryItems = inventoryItems;
        this.recyclerView = recyclerView;
        this.searchView = searchView;
    }


    @NonNull
    @Override
    public SellPageBottomDialogBoxItemRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.sell_page_bottomdialogbox_item_card,parent, false);

        return new SellPageBottomDialogBoxItemRecyclerViewAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SellPageBottomDialogBoxItemRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.nameSellPageBottomDialogItemCard.setText(inventoryItems.get(position).name);
        holder.costPriceSellPageBottomDialogItemCard.setText(inventoryItems.get(position).cost_price.get(inventoryItems.get(position).cost_price.size()-1) + "");
        holder.sellPriceSellPageBottomDialogItemCard.setText(inventoryItems.get(position).sell_price + "");
        holder.quantitySellPageBottomDialogItemCard.setText(inventoryItems.get(position).quantity.get(inventoryItems.get(position).cost_price.size()-1) + "");
        holder.unitSellPageBottomDialogItemCard.setText(inventoryItems.get(position).unit+"");
        holder.idSellPageBottomDialogItemCard.setText(inventoryItems.get(position).id+"");

        holder.addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewGroup.LayoutParams recyclerViewLayoutEdit = recyclerView.getLayoutParams();
                recyclerViewLayoutEdit.height = 1;
                recyclerView.setLayoutParams(recyclerViewLayoutEdit);

                ViewGroup.LayoutParams searchViewLayoutEdit = searchView.getLayoutParams();
                searchViewLayoutEdit.height = 1;
                searchView.setLayoutParams(searchViewLayoutEdit);


                Log.d("Debug", "Clicked : " + holder.nameSellPageBottomDialogItemCard.getText().toString());
            }
        });

        Log.d("Debug", "Bottom Dialog Box : " + inventoryItems.get(position).name);
    }

    @Override
    public int getItemCount() {
        return inventoryItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nameSellPageBottomDialogItemCard, costPriceSellPageBottomDialogItemCard, sellPriceSellPageBottomDialogItemCard, quantitySellPageBottomDialogItemCard, unitSellPageBottomDialogItemCard, idSellPageBottomDialogItemCard;
        Button addCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nameSellPageBottomDialogItemCard = (TextView) itemView.findViewById(R.id.sellPage_bottomDialog_name);
            costPriceSellPageBottomDialogItemCard = (TextView) itemView.findViewById(R.id.sellPage_bottomDialog_costPrice);
            sellPriceSellPageBottomDialogItemCard = (TextView) itemView.findViewById(R.id.sellPage_bottomDialog_sellPrice);
            quantitySellPageBottomDialogItemCard = (TextView) itemView.findViewById(R.id.sellPage_bottomDialog_quantity);
            unitSellPageBottomDialogItemCard = (TextView) itemView.findViewById(R.id.sellPage_bottomDialog_unit);
            idSellPageBottomDialogItemCard = (TextView) itemView.findViewById(R.id.sellPage_bottomDialog_id);

            addCard = (Button) itemView.findViewById(R.id.sellPage_BottomDialogBox_AddButton);
        }
    }

}
