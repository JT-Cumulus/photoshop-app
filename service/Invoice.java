package service;

import user.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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
     
     public void displayInvoice(int orderID){

     }

     // Find an order from its id within the invoices TODO
    public ArrayList<String> findInvoice(int orderID){
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
}
