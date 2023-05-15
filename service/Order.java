package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.ProcessBuilder.Redirect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class Order {
    private List<Item> currentOrder;
    private Integer orderID;
    private double totalPrice;
    private long totalTimeTaken;
    private LocalDate pickupDate;

    public Order(List<Item> currentOrder, Integer orderID, double totalPrice, long totalTimeTaken, LocalDate pickupDate) {
        this.currentOrder = currentOrder;
        this.orderID = orderID;
        this.totalPrice = totalPrice;
        this.totalTimeTaken = totalTimeTaken;
        this.pickupDate = pickupDate;
    }

    public Order(){
        currentOrder = new ArrayList<Item>();

    }

    /*
    public Order(ArrayList<String> arrayList){
        this.orderID = Integer.parseInt(arrayList.get(0));
        this.currentOrder = new List<Item>(Arrays.asList(arrayList.get(1).split(";")));
        this.totalPrice = Double.parseDouble(arrayList.get(2));
        this.totalTimeTaken = Long.parseLong(arrayList.get(3));
        this.pickupDate = LocalDate.parse(arrayList.get(4));
    }*/

    // Find an order from its id within the invoices TODO
    public ArrayList<String> getOrder(int orderID){
        String fileName = "./database/PhotoShop_Orders.csv";
        File file = new File(fileName);
  
        // this gives you a 2-dimensional array of strings
        Scanner inputStream;
        ArrayList<String> cob = new ArrayList<>();
        try{
            inputStream = new Scanner(file);

            while(inputStream.hasNext()){
                String line = inputStream.nextLine();
                String[] values = line.split(";");
                // this adds the currently parsed line to the 2-dimensional string array
                if(Integer.parseInt(values[0]) == orderID){
                    System.out.println(values);
                }
            }
            inputStream.close();
            
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cob;
    }

    public void displayCart(){
        System.out.println(String.format("%-5s %-30s %15s %20s %10s" , "ID", "Item", "Price(EUR)", "Time to Make (min)", "Quantity"));
        for (Item object: this.currentOrder) {
            this.totalPrice += object.getPrice() * object.getQuantity();
            this.totalTimeTaken += object.getMinutes() * object.getQuantity();
            System.out.println(object + "\t   " + object.getQuantity());
        }
        System.out.println("Total Price: " + this.getTotalPrice() + " EUR");
        System.out.println("Total Time Required: " + ((this.totalTimeTaken / 60) + " working hours"));
    }

    // Return the item within the cart such that the quantity can be updated in the shopping cart
    public Item getIteminCart(Item item){
        List<Item> temp = this.currentOrder;
        for (Item object: temp) {
            if (object.getId() == item.getId()){
                return object;
            } 
        }
        return null;
    }

    // Add item to the shopping cart
    public void addItem(Item item){
        if(getIteminCart(item) != null) {
            getIteminCart(item).addQuantity();
        } else {
            this.currentOrder.add(item);
        }        
    }

    // Remove item from shopping cart
    public void removeItem(Item item){
        if(getIteminCart(item) != null) {
            getIteminCart(item).addQuantity();
        } else {
            this.currentOrder.remove(item);
        }
    }


    public List<Item> getCurrentOrder() {
        return this.currentOrder;
    }

    public void setCurrentOrder(List<Item> currentOrder) {
        this.currentOrder = currentOrder;
    }

    public Integer getOrderID() {
        return this.orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getTotalTimeTaken() {
        return this.totalTimeTaken;
    }

    public void setTotalTimeTaken(long totalTimeTaken) {
        this.totalTimeTaken = totalTimeTaken;
    }

    public LocalDate getPickupDate() {
        return this.pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    
}
