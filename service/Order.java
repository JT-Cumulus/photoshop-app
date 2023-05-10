package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

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

    public Order(ShoppingCart cart){
        this.currentOrder = cart.getCurrentCart();
        this.orderID = cart.getOrderID();
        this.totalPrice = cart.getTotalPrice();
        this.totalTimeTaken = cart.getTotalTimeTaken();
        this.pickupDate = cart.getPickupDate();
    }

    public String[] findOrder(int orderID){
        String fileName = "database/PhotoShop_Orders.csv";
        File file = new File(fileName);
  
        // this gives you a 2-dimensional array of strings
        Scanner inputStream;
        int id = 0;
        try{
            inputStream = new Scanner(file);
            while(inputStream.hasNext()){
                String line = inputStream.nextLine();
                String[] values = line.split(";");
                // this adds the currently parsed line to the 2-dimensional string array
                id = Integer.parseInt(values[0]);
                if(id == orderID){
                    inputStream.close();
                    return values;
                }
            }
            inputStream.close();
            
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public Order retrieveOrder(int orderID){
        String[] values = findOrder(orderID);

    }
    
}
