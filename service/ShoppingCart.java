package service;

import java.util.List;
import java.util.Scanner;
import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
// Writing to json 
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVWriter;


// Store current items placed in shopping cart
public class ShoppingCart extends Catalogue{
    
    private List<Item> currentCart;
    private Integer orderID;
    private double totalPrice;
    private long totalTimeTaken;

    public ShoppingCart(){
        this.orderID = loadItems();
        this.currentCart = new LinkedList<>();
        this.orderID += 1;
        this.totalPrice = 0;
        this.totalTimeTaken = 0;
    }

    // Return the current cart
    public List<Item> getCart(){
        return this.currentCart;
    }

    // Load items already saved as orders
    public int loadItems(){
        String fileName = "database/PhotoShop_Orders.csv";
        File file = new File(fileName);
        int id = 1;

        // this gives you a 2-dimensional array of strings
        Scanner inputStream;

        try{
            inputStream = new Scanner(file);
            while(inputStream.hasNext()){
                String line = inputStream.nextLine();
                String[] values = line.split(";");
                // this adds the currently parsed line to the 2-dimensional string array
                id = Integer.parseInt(values[0]);
            }
            inputStream.close();
            
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return id;
    }

    // Return the item within the cart such that the quantity can be updated in the shopping cart
    public Item getIteminCart(Item item){
        List<Item> temp = this.currentCart;
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
            this.currentCart.add(item);
        }        
    }

    // Remove item from shopping cart
    public void removeItem(Item item){
        if(getIteminCart(item) != null) {
            getIteminCart(item).addQuantity();
        } else {
            this.currentCart.remove(item);
        }
    }

    // Display purchased items in cart and print total price
    public void displayCart(){
        System.out.println(String.format("%-5s %-30s %15s %20s %10s" , "ID", "Item", "Price(EUR)", "Time to Make (min)", "Quantity"));
        for (Item object: this.currentCart) {
            this.totalPrice += object.getPrice() * object.getQuantity();
            this.totalTimeTaken += object.getMinutes() * object.getQuantity();
            System.out.println(object + "\t   " + object.getQuantity());
        }
        System.out.println("Total Price: " + this.returnTotalPrice() + " EUR");
        //System.out.println("Total Time Required: " + ((this.totalTimeTaken / 60) / 24 + " days"));
        System.out.println("Total Time Required: " + ((this.totalTimeTaken / 60) + " working hours"));
    }

    public void saveCart(ShoppingCart cart){
    // first create file object for file placed at location
    // specified by filepath
    File file = new File("./database/PhotoShop_Orders.csv");
    try {
        // create FileWriter object with file as parameter
        FileWriter outputfile = new FileWriter(file);
  
        // create CSVWriter object filewriter object as parameter
        CSVWriter writer = new CSVWriter(outputfile, ';', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
  
        // add data to csv
        String[] data = {cart.orderID.toString(), cart.convertString()};
        writer.writeNext(data);
  
        // closing writer connection
        writer.close();
    }
    catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
    }

    // Print the total price as a string with two decimals
    public String returnTotalPrice(){
        String price = String.format(java.util.Locale.US,"%.2f", this.totalPrice);
        return price;
    }

    public long getTotalTime(){
        return this.totalTimeTaken;
    }

    // Export purchase to .json file
    public void exportJson(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String orderNumber = Integer.toString(this.orderID);
        // Java Object to String
        // String json = gson.toJson(this.getCart());
  
        // Java Object to a file
        try (FileWriter writer = new FileWriter(
                 "./invoices/order" + orderNumber + ".json")) {
            gson.toJson(this.currentCart, writer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function for iterating over items in shopping cart for invoice purposes
    public String convertString(){
        String temp = "";
        for(Item items : this.currentCart){
            temp += items.getId() + ";" + items.getQuantity() + ";" + items.getPrice() + ";";
        }
        return temp;
    }
    
}
