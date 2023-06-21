package com.dbtapps.pocketbusiness;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class LoadInventoryData {

    public static ArrayList<InventoryItemModel> inventoryItems;
    public static long totalInventoryItems;

    static LoadingDialogBox loadingDialogBox;

    public static void loadInventoryData(Context context) {

        loadingDialogBox = new LoadingDialogBox(context);

        loadingDialogBox.showDialogBox();

        inventoryItems = new ArrayList<>();

        MainActivity.firebaseDatabase.getReference().child("user_data").child(MainActivity.userID).child("inventory").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){

                    DataSnapshot dataSnapshot = task.getResult();
                    totalInventoryItems = dataSnapshot.getChildrenCount();
                    Iterable<DataSnapshot> iterableItemDataArray = dataSnapshot.getChildren();

                    for (DataSnapshot itemDataArray : iterableItemDataArray){

                        List<Double> cost_price = new ArrayList<>();
                        List<Double> quantity = new ArrayList<>();
                        int id = Integer.parseInt(itemDataArray.child("id").getValue().toString());
                        String name = itemDataArray.child("name").getValue().toString();
                        double sell_price = Double.parseDouble(itemDataArray.child("sell_price").getValue().toString());
                        String unit = itemDataArray.child("unit").getValue().toString();

                        Iterable<DataSnapshot> iterableCPArray =  itemDataArray.child("cost_price").getChildren();
                        for (DataSnapshot itemDataCP : iterableCPArray){
                            cost_price.add(Double.parseDouble(itemDataCP.getValue().toString()));
                        }

                        Iterable<DataSnapshot> iterableQuantityArray =  itemDataArray.child("quantity").getChildren();
                        for (DataSnapshot itemDataQuantity : iterableQuantityArray){
                            quantity.add(Double.parseDouble(itemDataQuantity.getValue().toString()));
                        }

                        Log.d("GlobalDebug", id + ", "+ name + ", "+ unit + ", "+ cost_price + ", "+ sell_price + ", "+ quantity);

                        inventoryItems.add(new InventoryItemModel(id,name, unit, cost_price, sell_price, quantity));

                    }

                }
                else{
                    Toast.makeText(context, "Error in fetching data from server.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        loadingDialogBox.dismissDialogBox();

    }


}
