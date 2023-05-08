package service;

import user.*;
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
    private ShoppingCart soldCart;

     public Invoice(Employee employee, Customer customer, ShoppingCart shoppingCart){
        this.soldEmployee = employee;
        this.soldCustomer = customer;
        this.soldCart = shoppingCart;
     }
     
     public static displayInvoice(){
        this.soldEmployee.getFirstName();
     }
}
