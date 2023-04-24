package service;

// For items in the catalogue
// With ID, name, price for the item
public class Item {
    private int id;
    private String name;
    private double price;
    private int hours;
    private int quantity;

    public Item(int id, String name, double price, int hours){
        this.id = id;
        this.name = name;
        this.price = price;
        this.hours = hours;
        this.quantity = 1;
    }

    public Item(Item source){
        this.id = source.id;
        this.name = source.name;
        this.price = source.price;
        this.hours = source.hours;
        this.quantity = 1;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Name cannot be blank");
        }
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    public int getHours(){
        return this.hours;
    }

    public void setHours(int hours){
        this.hours = hours;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void addQuantity(){
        this.quantity += 1;
    }
    
    public String toString() {
       // Format print to be in readable columns with correct decimal points
        return String.format("%-5s %-30s %15.2f %20s" , this.id, this.name, this.price, this.hours );
    }    


}
