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
    RecyclerView inventoryItemCardsContainer;
    SearchView searchInventoryItems;
    Context context;
    public static boolean searchFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_page);

        //INITIALIZATION ---------------------------------------------------------------------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        sectionInventoryContainer = (ConstraintLayout) findViewById(R.id.sectionInventoryContainer);

        inventoryItemCardsContainer = (RecyclerView) findViewById(R.id.inventoryItemCardsContainer);
        searchInventoryItems =  (SearchView) findViewById(R.id.searchInventoryItems);

        context = this;
        searchFlag = false;
        //INITIALIZATION END -----------------------------------------------------------------------

        //ANIMATION SECTION ------------------------------------------------------------------------
        sectionInventoryContainer.setAlpha(0);
        sectionInventoryContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(1);
        //ANIMATION SECTION END --------------------------------------------------------------------

        loadRecyclerView();

        startSearchItemListener();


    }

    public void loadRecyclerView(){

        InventoryItemRecyclerViewAdapter adapter = new InventoryItemRecyclerViewAdapter(context, LoadInventoryData.inventoryItems);
        inventoryItemCardsContainer.setAdapter(adapter);
        inventoryItemCardsContainer.setLayoutManager(new LinearLayoutManager(context));
        Log.d("Debug","Data loaded to inventory");

    }

    public void startSearchItemListener(){

        searchInventoryItems.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //TODO: new filtered array deletes the existing items too. FIX IT!! ------> DONE

                if(newText.length() != 0) {

                    newText = newText.toLowerCase();
                    ArrayList<InventoryItemModel> filteredInventoryItems = new ArrayList<>();

                    for (int i = 0; i < LoadInventoryData.inventoryItems.size(); i++) {
                        if (LoadInventoryData.inventoryItems.get(i).name.toLowerCase().contains(newText))
                            filteredInventoryItems.add(LoadInventoryData.inventoryItems.get(i));

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
                    InventoryItemRecyclerViewAdapter adapter = new InventoryItemRecyclerViewAdapter(context, LoadInventoryData.inventoryItems);
                    inventoryItemCardsContainer.setAdapter(adapter);
                    inventoryItemCardsContainer.setLayoutManager(new LinearLayoutManager(context));

                }


                return false;
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
                LoadInventoryData.inventoryItems.add(newItemData);
                MainActivity.firebaseDatabase.getReference().child("user_data").child(MainActivity.userID).child("inventory").child(LoadInventoryData.totalInventoryItems + "").setValue(newItemData);
                LoadInventoryData.totalInventoryItems = LoadInventoryData.inventoryItems.size();

                //DATA SEND END --------------------------------------------------------------------

                InventoryItemRecyclerViewAdapter adapter = new InventoryItemRecyclerViewAdapter(context, LoadInventoryData.inventoryItems);
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