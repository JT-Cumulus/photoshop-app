package service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Create class for already saved orders
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
        currentOrder = new ArrayList<Item>();
    }
    
    public List<Item> getCurrentOrder() {
        return this.currentOrder;
    }

    public void setCurrentOrder(List<Item> currentOrder) {
        this.currentOrder = currentOrder;
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

    public LocalDate getPickupDate() {
        return this.pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    // Displays the order without changing any values
    public void displayOrder(){
        System.out.println(String.format("%-5s %-30s %15s %20s %10s" , "ID", "Item", "Price(EUR)", "Time to Make (min)", "Quantity"));
        for (Item object: this.currentOrder) {
            System.out.println(object + "\t   " + object.getQuantity());
        }
        System.out.println("Total Price: " + this.getTotalPrice() + " EUR");
        System.out.println("Total Time Required: " + ((this.totalTimeTaken / 60) + " working hours"));
    }

    public void displayPickupTime(Days days, List<Days> openingsTimes){
        LocalDateTime pickup = this.pickupDate.atTime(9, 0);
        System.out.println("You can pick up on: " + pickup.plusMinutes(days.calculatePickupTime(this.totalTimeTaken, openingsTimes)));
    }

    // Return the item within the cart such that the quantity can be updated in the shopping cart
    public Item getIteminCart(Item item){
        List<Item> temp = this.currentOrder;
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
            this.currentOrder.add(item);
        }        
    }

    // Remove item from shopping cart
    public void removeItem(Item item){
        if(getIteminCart(item) != null) {
            getIteminCart(item).addQuantity();
        } else {
            this.currentOrder.remove(item);
        }
    }



    
}
