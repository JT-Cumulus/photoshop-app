package service;

import java.time.LocalDate;
import java.util.List;

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

    public Order(ShoppingCart cart){
        this.currentOrder = cart.getCart();
        this.orderID = cart.getOrderID();
        this.totalPrice = cart.getTotalPrice();
        this.totalTimeTaken = cart.getTotalTimeTaken();
        this.pickupDate = cart.getPickupDate();
    }
    
}
