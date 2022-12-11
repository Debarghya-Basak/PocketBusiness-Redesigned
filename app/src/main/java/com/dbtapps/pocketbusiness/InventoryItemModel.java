package com.dbtapps.pocketbusiness;

import java.util.List;

public class InventoryItemModel {

    public String name, unit;
    public List<Double> cost_price, quantity;
    public double sell_price;
    public int id;

    public InventoryItemModel(int id, String name, String unit, List<Double> cost_price, double sell_price, List<Double> quantity) {
        this.name = name;
        this.unit = unit;
        this.cost_price = cost_price;
        this.sell_price = sell_price;
        this.quantity = quantity;
        this.id = id;
    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getUnit() {
//        return unit;
//    }
//
//    public double[] getCost_price() {
//        return cost_price;
//    }
//
//    public double getSell_price() {
//        return sell_price;
//    }
//
//    public double[] getQuantity() {
//        return quantity;
//    }
//
//    public int getId() {
//        return id;
//    }
}
