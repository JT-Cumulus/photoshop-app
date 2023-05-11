package service;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVWriter;

import repository.Customer;
import repository.Employee;

// Store current items placed in shopping cart
public class ShoppingCart{
    
    private List<Item> currentCart;
    private Integer orderID;
    private double totalPrice;
    private long totalTimeTaken;
    private LocalDate pickupDate;

    public ShoppingCart(){
        this.orderID = loadItems();
        this.currentCart = new LinkedList<>();
        this.totalPrice = 0;
        this.totalTimeTaken = 0;
    }

    // Return the current cart
    public List<String> getCart(){
        ArrayList<String> container = new ArrayList<String>();
        container.add(Integer.toString(orderID));
        container.add(this.convertString());
        container.add(Double.toString(totalPrice));
        container.add(Long.toString(totalTimeTaken));
        container.add(pickupDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));

        return container;
    }

    // Load items already saved as orders
    public int loadItems(){
        String fileName = "database/PhotoShop_Orders.csv";
        File file = new File(fileName);
        int id = 0;

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

        return id + 1;
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
        System.out.println("Total Time Required: " + ((this.totalTimeTaken / 60) + " working hours"));
    }

    public void saveCart(ShoppingCart cart, Employee employee, Customer customer){
        try {
            // create FileWriter object with file as parameter
            Writer outputfile;
            outputfile = new BufferedWriter(new FileWriter("./database/PhotoShop_Orders.csv", true));
    
            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile, ';', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
    
            // add data to csv
            String[] data = {
                cart.orderID.toString(), 
                Integer.toString(customer.getID()),
                Days.getDateToday(), 
                Days.dateToString(cart.pickupDate),
                Integer.toString(employee.getEmployeeId())};
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

    // Function for iterating over items in shopping cart for invoice purposes TODO
    public String convertString(){
        String temp = "";
        for(Item items : this.currentCart){
            temp += items.getId() + ":" + items.getQuantity() + ";";
        }
        return temp;
    }

    public List<Item> getCurrentCart(){
        return this.currentCart;
    }
    
    public void setCurrentCart(List<Item> currentCart) {
        this.currentCart = currentCart;
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

    public LocalDate getPickupDate(){
        return this.pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate){
        this.pickupDate = pickupDate;
    }


}
