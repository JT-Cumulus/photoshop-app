package service;

import java.util.List;
import java.util.LinkedList;

// Writing to json 
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// Store current items placed in shopping cart as treemap
public class ShoppingCart extends Catalogue{
    
    private List<Item> currentCart;
    private int orderID = 0;

    public ShoppingCart(){
        this.currentCart = new LinkedList<>();
        this.orderID += 1;
    }

    // Return the current cart
    public List<Item> getCart(){
        return this.currentCart;
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
            System.out.println(object + "\t" + object.getQuantity());
        }
        System.out.println("Total Price: " + totalPrice);
    }

    // Export purchase to .json file
    public void exportJson(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String orderNumber = Integer.toString(orderID);
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
