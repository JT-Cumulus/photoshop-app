package service;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
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
    private Integer orderID = 1;

    // Set global orderID based on current value in csv
    this.loadItems();

    public ShoppingCart(){
        this.currentCart = new LinkedList<>();
        this.orderID += 1;
    }

    // Return the current cart
    public List<Item> getCart(){
        return this.currentCart;
    }

    // Load items already saved as orders
    public void loadItems(){
        String fileName = "database/PhotoShop_Orders.csv";
        File file = new File(fileName);

        // this gives you a 2-dimensional array of strings
        Scanner inputStream;

        try{
            inputStream = new Scanner(file);
            while(inputStream.hasNext()){
                String line = inputStream.nextLine();
                String[] values = line.split(";");
                // this adds the currently parsed line to the 2-dimensional string array
                this.orderID = Integer.parseInt(values[0]);
            }
            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
        double totalPrice = 0;
        System.out.println(String.format("%-5s %-30s %15s %20s %20s" , "ID", "Item", "Price(EUR)", "Time to Make (min)", "Quantity" ));
        for (Item object: this.currentCart) {
            totalPrice += object.getPrice() * object.getQuantity();
            System.out.println(object + "\t\t" + object.getQuantity());
        }
        System.out.println("Total Price: " + totalPrice);
    }

    public void saveCart(){
    // first create file object for file placed at location
    // specified by filepath
    File file = new File("./database/PhotoShop_Orders.csv");
    try {
        // create FileWriter object with file as parameter
        FileWriter outputfile = new FileWriter(file);
  
        // create CSVWriter object filewriter object as parameter
        CSVWriter writer = new CSVWriter(outputfile);
  
        // adding header to csv
        //String[] header = { "OrderID", "Products", "Price" };
        //writer.writeNext(header);
  
        // add data to csv
        String[] data = {this.orderID.toString(), this.currentCart.toString()};
        writer.writeNext(data);
  
        // closing writer connection
        writer.close();
    }
    catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
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
    
}
