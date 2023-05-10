package service;

import user.*;

import java.io.File;
import java.io.FileNotFoundException;
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

     public Invoice(Employee employee, Customer customer, ShoppingCart shoppingCart){
        this.soldEmployee = employee;
        this.soldCustomer = customer;
        this.soldOrder = new Order(shoppingCart);
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
     
     public void displayInvoice(int orderID){

        this.soldEmployee.getFirstName();

        this.soldCart.displayCart();
     }
}
