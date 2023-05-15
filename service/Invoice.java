package service;

import user.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import repository.Customer;
import repository.Employee;
import service.ShoppingCart;

public class Invoice {
    /*
     * Proposed format
     * 
     * Invoice Order ID: orderID
     * 
     * Customer:                        Employee:
     * firstname lastname               firstname lastname
     * address                          employee id
     * postcode
     * city
     * email
     * mobile
     * 
     * Order Specifications:
     * orderID
     * orderDate
     * orderHours
     * pickupDate
     * 
     * Photo type               Price           Quantity
     * name                     price           quantity
     * 
     * Total costs              sum(price)
     */

    private Employee soldEmployee;
    private Customer soldCustomer;
    private Order soldOrder;

     public Invoice(Employee employee, Customer customer, Order order){
        this.soldEmployee = employee;
        this.soldCustomer = customer;
        this.soldOrder = order;
     }

     public Invoice(){

     }
     
     public void displayInvoice(int orderID){
        this.soldCustomer.displayCustomers();
     }

     // Find an order from its id within the invoices TODO
    public Order findInvoice(int orderID, Catalogue catalogue) throws FileNotFoundException, IOException{
      String fileName = "./invoices/order_" + orderID + ".json";
      Order newOrder = new Order();

      ArrayList<Integer> itemIndex = new ArrayList<>();
      ArrayList<Integer> itemQuantity = new ArrayList<>();

      try (JsonReader reader = new JsonReader(new FileReader(fileName))) {

        reader.beginObject();
        while (reader.hasNext()) {

            String name = reader.nextName();

            if (name.equals("id")) {
                newOrder.setOrderID(reader.nextInt());
            } else if (name.equals("items")) {
                // read array
                reader.beginArray();
                while (reader.hasNext()) {
                    int currentInt = reader.nextInt();
                    itemIndex.add(currentInt);
                }
                reader.endArray();
            } else if (name.equals("quantity")) {
                // read array
                reader.beginArray();
                while (reader.hasNext()) {
                    int currentInt = reader.nextInt();
                    itemQuantity.add(currentInt);
                }
                reader.endArray();

            } else if (name.equals("total price")) {
                newOrder.setTotalPrice(reader.nextDouble());
            } else if (name.equals("time taken")) {
                newOrder.setTotalTimeTaken(reader.nextLong());
            } else if (name.equals("pickup date")) {
                newOrder.setPickupDate(LocalDate.parse(reader.nextString(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
            } else {
                reader.skipValue(); //avoid some unhandle events
            }
        }
        reader.endObject();

        for(int i = 0; i<itemIndex.size();i++){
            newOrder.addItem(catalogue.getItem(itemIndex.get(i)));
            newOrder.getIteminCart(catalogue.getItem(itemIndex.get(i))).setQuantity(itemQuantity.get(i));;;

        }

        return newOrder;
  }

}
}
