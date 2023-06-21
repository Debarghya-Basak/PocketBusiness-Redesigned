package com.dbtapps.pocketbusiness;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import java.util.ArrayList;

public class InventoryItemRecyclerViewAdapter extends RecyclerView.Adapter<InventoryItemRecyclerViewAdapter.MyViewHolder>{

    ArrayList<InventoryItemModel> inventoryItems;
    Context context;

    public InventoryItemRecyclerViewAdapter(Context context, ArrayList<InventoryItemModel> inventoryItems){

        this.context = context;
        this.inventoryItems = inventoryItems;

    }

    public void printQrCodeToStorage(String qrCodeText){
        return;
    }

    @NonNull
    @Override
    public InventoryItemRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.inventory_item_card,parent, false);

        return new InventoryItemRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryItemRecyclerViewAdapter.MyViewHolder holder, int position) {

        int pos = position;

        holder.nameInventoryItemCard.setText(inventoryItems.get(position).name);
        holder.costPriceInventoryItemCard.setText(inventoryItems.get(position).cost_price.get(inventoryItems.get(position).cost_price.size()-1) + "");
        holder.sellPriceInventoryItemCard.setText(inventoryItems.get(position).sell_price + "");
        holder.quantityInventoryItemCard.setText(inventoryItems.get(position).quantity.get(inventoryItems.get(position).quantity.size()-1) + "");
        holder.unitInventoryItemCard.setText(inventoryItems.get(position).unit);
        holder.idInventoryItemCard.setText(inventoryItems.get(position).id + "");

        MultiFormatWriter mWriter = new MultiFormatWriter();
        try {

            BitMatrix mMatrix = mWriter.encode(inventoryItems.get(pos).id + "", BarcodeFormat.QR_CODE, 200,200);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(mMatrix);
            holder.qrCodeInventoryItemCard.setImageBitmap(mBitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }


        holder.qrCodeInventoryItemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = LayoutInflater.from(context);
                View qrCodeDialogBoxView = inflater.inflate(R.layout.view_and_print_qr_code_dialog_box, null);

                BottomDialogBox qrCodeDialogBox = new BottomDialogBox(context, qrCodeDialogBoxView);

                Button printButtonQrCodeInventoryDialogBox = (Button) qrCodeDialogBoxView.findViewById(R.id.printButtonQrCodeInventoryDialogBox);
                Button cancelButtonQrCodeInventoryDialogBox = (Button) qrCodeDialogBoxView.findViewById(R.id.cancelButtonQrCodeInventoryDialogBox);
                ShapeableImageView qrCodeInventoryDialogBox = (ShapeableImageView) qrCodeDialogBoxView.findViewById(R.id.qrCodeInventoryDialogBox);

                qrCodeDialogBox.showDialogBox();

                MultiFormatWriter mWriter = new MultiFormatWriter();
                try {
                    BitMatrix mMatrix = mWriter.encode(inventoryItems.get(pos).id + "", BarcodeFormat.QR_CODE, 500, 500);
                    BarcodeEncoder mEncoder = new BarcodeEncoder();
                    Bitmap mBitmap = mEncoder.createBitmap(mMatrix);
                    qrCodeInventoryDialogBox.setImageBitmap(mBitmap);
                }
                catch (WriterException e) {
                    e.printStackTrace();
                }

                printButtonQrCodeInventoryDialogBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //TODO: Print QR CODE

                        printQrCodeToStorage(inventoryItems.get(pos).id + "");

                        qrCodeDialogBox.dismissDialogBox();
                    }
                });

                cancelButtonQrCodeInventoryDialogBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qrCodeDialogBox.dismissDialogBox();
                    }
                });

            }
        });

        holder.deleteButtonInventoryItemCard.setOnClickListener(new View.OnClickListener() {
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

                        if(InventoryPage.searchFlag) {

                            LoadInventoryData.inventoryItems.remove(inventoryItems.get(pos));

                            inventoryItems.remove(holder.getAdapterPosition());
                            notifyDataSetChanged();

                            MainActivity.firebaseDatabase.getReference().child("user_data").child(MainActivity.userID).child("inventory").removeValue();
                            for (int i = 0; i < LoadInventoryData.inventoryItems.size(); i++) {
                                MainActivity.firebaseDatabase.getReference().child("user_data").child(MainActivity.userID).child("inventory").child(i + "").setValue(LoadInventoryData.inventoryItems.get(i));
                            }

                            LoadInventoryData.totalInventoryItems = LoadInventoryData.inventoryItems.size();
                            bottomDialogBox.dismissDialogBox();
                            Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            inventoryItems.remove(holder.getAdapterPosition());
                            notifyDataSetChanged();

                            MainActivity.firebaseDatabase.getReference().child("user_data").child(MainActivity.userID).child("inventory").removeValue();
                            for (int i = 0; i < inventoryItems.size(); i++) {
                                MainActivity.firebaseDatabase.getReference().child("user_data").child(MainActivity.userID).child("inventory").child(i + "").setValue(inventoryItems.get(i));
                            }

                            LoadInventoryData.totalInventoryItems = inventoryItems.size();

                            bottomDialogBox.dismissDialogBox();
                            Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                        }
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

        //TODO: EDIT BUTTON INVENTORY CARDS
        holder.editButtonInventoryItemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Edit button is clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return inventoryItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ShapeableImageView qrCodeInventoryItemCard;
        TextView nameInventoryItemCard, costPriceInventoryItemCard, sellPriceInventoryItemCard, quantityInventoryItemCard, unitInventoryItemCard, idInventoryItemCard;
        Button deleteButtonInventoryItemCard, editButtonInventoryItemCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            qrCodeInventoryItemCard = (ShapeableImageView) itemView.findViewById(R.id.qrCodeInventoryItemCard);

            nameInventoryItemCard = (TextView) itemView.findViewById(R.id.nameInventoryItemCard);
            costPriceInventoryItemCard = (TextView) itemView.findViewById(R.id.costPriceInventoryItemCard);
            sellPriceInventoryItemCard = (TextView) itemView.findViewById(R.id.sellPriceInventoryItemCard);
            quantityInventoryItemCard = (TextView) itemView.findViewById(R.id.quantityInventoryItemCard);
            unitInventoryItemCard = (TextView) itemView.findViewById(R.id.unitInventoryItemCard);
            idInventoryItemCard = (TextView) itemView.findViewById(R.id.idInventoryItemCard);

            deleteButtonInventoryItemCard = (Button) itemView.findViewById(R.id.deleteButtonInventoryItemCard);
            editButtonInventoryItemCard = (Button) itemView.findViewById(R.id.editButtonInventoryItemCard);

        }
    }
}
