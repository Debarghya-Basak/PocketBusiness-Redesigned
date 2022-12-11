package com.dbtapps.pocketbusiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage extends AppCompatActivity {

    ConstraintLayout sectionInventoryContainer;
    public static ArrayList<InventoryItemModel> inventoryItems;
    LoadingDialogBox loadingDialogBox;
    RecyclerView inventoryItemCardsContainer;
    SearchView searchInventoryItems;
    Context context;
    public static long totalInventoryItems;
    public static boolean searchFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_page);

        //INITIALIZATION ---------------------------------------------------------------------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        sectionInventoryContainer = (ConstraintLayout) findViewById(R.id.sectionInventoryContainer);

        inventoryItems = new ArrayList<>();
        loadingDialogBox = new LoadingDialogBox(this);

        inventoryItemCardsContainer = (RecyclerView) findViewById(R.id.inventoryItemCardsContainer);
        searchInventoryItems =  (SearchView) findViewById(R.id.searchInventoryItems);

        context = this;
        searchFlag = false;
        //INITIALIZATION END -----------------------------------------------------------------------

        //ANIMATION SECTION ------------------------------------------------------------------------
        sectionInventoryContainer.setAlpha(0);
        sectionInventoryContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(1);
        //ANIMATION SECTION END --------------------------------------------------------------------

        loadingDialogBox.showDialogBox();
        loadInventoryData();

        startSearchItemListener();


    }

    public void startSearchItemListener(){

        searchInventoryItems.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //TODO: new filtered array deletes the existing items too. FIX IT!!

                if(newText.length() != 0) {

                    newText = newText.toLowerCase();
                    ArrayList<InventoryItemModel> filteredInventoryItems = new ArrayList<>();

                    for (int i = 0; i < inventoryItems.size(); i++) {
                        if (inventoryItems.get(i).name.toLowerCase().contains(newText))
                            filteredInventoryItems.add(inventoryItems.get(i));

                    }

                    searchFlag = true;
                    Toast.makeText(context, searchFlag + "", Toast.LENGTH_SHORT).show();
                    InventoryItemRecyclerViewAdapter adapter = new InventoryItemRecyclerViewAdapter(context, filteredInventoryItems);
                    inventoryItemCardsContainer.setAdapter(adapter);
                    inventoryItemCardsContainer.setLayoutManager(new LinearLayoutManager(context));
                }
                else{

                    searchFlag = false;
                    Toast.makeText(context, searchFlag + "", Toast.LENGTH_SHORT).show();
                    InventoryItemRecyclerViewAdapter adapter = new InventoryItemRecyclerViewAdapter(context, inventoryItems);
                    inventoryItemCardsContainer.setAdapter(adapter);
                    inventoryItemCardsContainer.setLayoutManager(new LinearLayoutManager(context));

                }


                return false;
            }
        });

    }

    public void loadInventoryData() {

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

                    InventoryItemRecyclerViewAdapter adapter = new InventoryItemRecyclerViewAdapter(context, inventoryItems);
                    inventoryItemCardsContainer.setAdapter(adapter);
                    inventoryItemCardsContainer.setLayoutManager(new LinearLayoutManager(context));

                    loadingDialogBox.dismissDialogBox();
                }
                else{
                    Toast.makeText(InventoryPage.this, "Error in fetching data from server.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void addItemToInventory(View view){

        LayoutInflater inflater = getLayoutInflater();
        View box = inflater.inflate(R.layout.add_item_to_inventory_dialog_box, null);

        BottomDialogBox bottomDialogBox = new BottomDialogBox(this,box);
        bottomDialogBox.showDialogBox();

        List<String> spinnerUnits = new ArrayList<>();
        spinnerUnits.add("Kg");
        spinnerUnits.add("No Unit");
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,spinnerUnits);
        TextInputEditText nameAddItemDialogBox = (TextInputEditText) box.findViewById(R.id.nameAddItemDialogBox);
        TextInputEditText costPriceAddItemDialogBox = (TextInputEditText) box.findViewById(R.id.costPriceAddItemDialogBox);
        TextInputEditText sellPriceAddItemDialogBox = (TextInputEditText) box.findViewById(R.id.sellPriceAddItemDialogBox);
        TextInputEditText quantityAddItemDialogBox = (TextInputEditText) box.findViewById(R.id.quantityAddItemDialogBox);
        Spinner unitAddItemDialogBox = (Spinner) box.findViewById(R.id.unitAddItemDialogBox);
        unitAddItemDialogBox.setAdapter(spinnerAdapter);
        Button addItemToInventory = (Button) box.findViewById(R.id.addItemToInventory);

        addItemToInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //DATA SEND ------------------------------------------------------------------------
                String name = nameAddItemDialogBox.getText().toString();
                String unit = unitAddItemDialogBox.getSelectedItem().toString();
                List<Double> cost_price = new ArrayList<>();
                cost_price.add(Double.parseDouble(costPriceAddItemDialogBox.getText().toString()));
                List<Double> quantity = new ArrayList<>();
                quantity.add(Double.parseDouble(quantityAddItemDialogBox.getText().toString()));
                double sell_price = Double.parseDouble(sellPriceAddItemDialogBox.getText().toString());
                int id = (int)(Math.random()*1000000);


                InventoryItemModel newItemData = new InventoryItemModel(id,name,unit, cost_price,sell_price,quantity);
                inventoryItems.add(newItemData);
                MainActivity.firebaseDatabase.getReference().child("user_data").child(MainActivity.userID).child("inventory").child(totalInventoryItems + "").setValue(newItemData);
                totalInventoryItems = inventoryItems.size();

                //DATA SEND END --------------------------------------------------------------------

                InventoryItemRecyclerViewAdapter adapter = new InventoryItemRecyclerViewAdapter(context, inventoryItems);
                inventoryItemCardsContainer.setAdapter(adapter);
                inventoryItemCardsContainer.setLayoutManager(new LinearLayoutManager(context));

                Toast.makeText(InventoryPage.this, "Item added", Toast.LENGTH_SHORT).show();
                bottomDialogBox.dismissDialogBox();

            }
        });


    }

    @Override
    public void onBackPressed() {

        //ANIMATION SECTION ------------------------------------------------------------------------
        sectionInventoryContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(0);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },MainActivity.GLOBAL_FADE_ANIMATION_DURATION);
        //ANIMATION SECTION END --------------------------------------------------------------------

    }

    @Override
    protected void onResume() {
        super.onResume();

        sectionInventoryContainer.setAlpha(0);
        sectionInventoryContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(1);
    }
}