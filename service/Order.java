package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public Order(ArrayList<String> arrayList){
        this.currentOrder = arrayList.get(0);
        this.orderID = arrayList.get(0);
        this.totalPrice = arrayList.get(0);
        this.totalTimeTaken = arrayList.get(0);
        this.pickupDate = arrayList.get(0);
    }

    // Find an order from its id within the invoices TODO
    public ArrayList<String> getOrder(int orderID){
        String fileName = "./invoices/order_" + orderID + ".json";
        File file = new File(fileName);
  
        // this gives you a 2-dimensional array of strings
        Scanner inputStream;
        ArrayList<String> cob = new ArrayList<>();
        try{
            inputStream = new Scanner(file);
            while(inputStream.hasNext()){
                String line = inputStream.nextLine();
                String[] values = line.split(",");
                // this adds the currently parsed line to the 2-dimensional string array
                cob.add(values[0]);
            }
            inputStream.close();
            
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cob;
    }

    public Order retrieveOrder(int orderID){
        String[] values = findOrder(orderID);
        int id = Integer.getInteger(values[0]);
        


    }
    
}
