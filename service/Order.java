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

    public void retrieveOrder(ArrayList<String> arrayList, Catalogue catalogue){
        this.orderID = Integer.parseInt(arrayList.get(0));
        List<String> container = Arrays.asList(arrayList.get(1).split(";"));
        List<Item> temp = new ArrayList<Item>();
        for (String line : container){
            List<String> indexAndQuantity = Arrays.asList(line.split(":"));
            int index = Integer.parseInt(indexAndQuantity.get(0));
            int quantity = Integer.parseInt(indexAndQuantity.get(1));
            temp.add(catalogue.getItem(index));
            temp.get(index).setQuantity(quantity);
        }
        this.currentOrder = temp;
        this.totalPrice = Double.parseDouble(arrayList.get(2));
        this.totalTimeTaken = Long.parseLong(arrayList.get(3));
        this.pickupDate = LocalDate.parse(arrayList.get(4));

    }
    
}
