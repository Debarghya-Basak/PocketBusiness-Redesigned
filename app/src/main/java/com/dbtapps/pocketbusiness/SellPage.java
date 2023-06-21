package com.dbtapps.pocketbusiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class SellPage extends AppCompatActivity {

    ConstraintLayout sectionSellContainer;
    Context context;
    RecyclerView bottomDialogItemsRecyclerView;
    SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_page);

        //INITIALIZATION ---------------------------------------------------------------------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        sectionSellContainer = (ConstraintLayout) findViewById(R.id.sectionSellContainer);

        context = this;
        //INITIALIZATION END -----------------------------------------------------------------------

        //ANIMATION SECTION ------------------------------------------------------------------------
        sectionSellContainer.setAlpha(0);
        sectionSellContainer.animate().setDuration(MainActivity.GLOBAL_FADE_ANIMATION_DURATION).alpha(1);
        //ANIMATION SECTION END --------------------------------------------------------------------

    }

    public void scanQRAddItemToSell(View v){

        //TODO: Make scanQRAddItemToSell and delete this temporary function
        Intent intent = new Intent(SellPage.this, DashboardPage.class);
        startActivity(intent);

    }

    public void addItemManually(View v){

        LayoutInflater inflater = getLayoutInflater();
        View bottomDialogBoxView = inflater.inflate(R.layout.add_manually_sell_page_dialog_box, null);

        BottomDialogBox bottomDialogBox = new BottomDialogBox(this, bottomDialogBoxView);
        bottomDialogBox.showDialogBox();

        MaterialButton cancelButton = (MaterialButton) bottomDialogBoxView.findViewById(R.id.cancelButton_addManuallySellPage);
        MaterialButton addButton = (MaterialButton) bottomDialogBoxView.findViewById(R.id.addButton_addManuallySellPage);

        bottomDialogItemsRecyclerView = (RecyclerView) bottomDialogBoxView.findViewById(R.id.recyclerView_SellPageBottomDialogBox_addManually);

        search = (SearchView) bottomDialogBoxView.findViewById(R.id.searchView_BottomDialogBox_SellPage);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialogBox.dismissDialogBox();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        loadBottomDialogBoxItems(LoadInventoryData.inventoryItems);

        startSearchListener();

    }

    private void loadBottomDialogBoxItems(ArrayList<InventoryItemModel> loadItems) {

        SellPageBottomDialogBoxItemRecyclerViewAdapter adapter = new SellPageBottomDialogBoxItemRecyclerViewAdapter(context, loadItems);
        bottomDialogItemsRecyclerView.setAdapter(adapter);
        bottomDialogItemsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        Toast.makeText(context, "Loaded Dialog Box Items", Toast.LENGTH_SHORT).show();

    }

    public void startSearchListener(){

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Debug","Search msg submit : " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("Debug","Search msg changed : " + newText);
                ArrayList<InventoryItemModel> searchedList = new ArrayList<InventoryItemModel>();
                for(InventoryItemModel model : LoadInventoryData.inventoryItems){

                    if(model.name.toLowerCase().contains(newText) || (model.id+"").contains(newText))
                        searchedList.add(model);

                }

                loadBottomDialogBoxItems(searchedList);

                return false;
            }
        });

    }
}