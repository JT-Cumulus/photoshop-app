
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

import service.Catalogue;
import service.Days;
import service.Item;
import service.ShoppingCart;

public class Main {

    // First instance catalogue so daily catalogue can be loaded in
    static Catalogue catalogue = new Catalogue();
    static ShoppingCart cart = new ShoppingCart();
    static Scanner scan = new Scanner(System.in);

    // Load in Opening Times of the shop
    static List<Days> openingTimes = Days.loadDays();

    // Userlocation variable to figure out where the user is in global navigation
    // 0 = Welcome
    // 1 = Purchase Menu
    // 2 = Check order status (duration until done)
    // 3 = See invoice detail (display full invoice)
    // -1 = Quit condition (exit the program)

    static int userLocation = 0;
    public static void main(String[] args) {

        // Main store menu
        shopMenu();

        // Load daily prices
        loadItems();

        // Set quit condition for terminating application
        while (userLocation != -1){
            // Check for user input
            int option = userNavigation();

            switch(option){
                // Print catalogue here
                case 1:
                displayCatalogue();

                // Add item to shopping cart here
                purchaseMenu();

                // Print order details
                case 2:
                checkOrder();

                // Print invoice details / allow user to access order details from invoice no.
                case 3:
                checkInvoice();

                // Exit program condition
                case 0:
                userLocation = -1;

            }

        }
        
        // Pickup time provided for the day the pickup is allowed

        // Close scanner at end
        scan.close();
    }

    // Function displaying the shop menu for user navigation
    public static void shopMenu(){
        String menuWelcomeMessage = "Hello, welcome to PhotoShop \n" + "Please type a number to navigate the menu \n";
        String menuDivider = "---------------------------";

        System.out.println(menuDivider);
        System.out.print(menuWelcomeMessage);
        System.out.println(menuDivider);
        System.out.println("Press 1 - Make a purchase");
        System.out.println("Press 2 - See order status");
        System.out.println("Press 3 - See invoice details");
        System.out.println("Press 0 - Exit program");
        System.out.println(menuDivider);
        
    }

    //Function for making purchase
    public static void purchaseMenu(){

        String status = "c";
        while (status.equals("c")) {
            System.out.print("\nPlease choose an integer between 1 - " + catalogue.getLength() + ": ");
            int choice = scan.nextInt() + 1;
            Item item = catalogue.getItem(choice);
            cart.addItem(item);
            System.out.print("You have added " + item.getName());
            System.out.print("\nTo add another item, type: 'c' or type 'b' to make your purchase ");
            status = scan.next();

            // Check if user wants to make purchase
            if (status.equals("b")){
                userLocation = 2;
                cart.saveCart(cart);
                //cart.exportJson();
            }
        }
    }

    public static void checkOrder(){
        cart.displayCart();
    }

    public static void checkInvoice(){

    }

    // Function for user navigation
    public static int userNavigation(){
        System.out.println("Please input a valid number: ");
        int userChoice = 0;
        while (userChoice < 1){
            try {
                userChoice = scan.nextInt();
            } catch(Exception e) { 
                System.out.println("Invalid input, try again");
            }
        }
        return userChoice;
    }

    // Function to break up items 
    private static Item createItem(String[] metadata) {
        int id = Integer.parseInt(metadata[0]);
        String name = metadata[1];
        double price = Double.parseDouble(metadata[2]);
        int hours = toMins(metadata[3]);

        return new Item(id, name, price, hours);
    }


    private static int toMins(String s) {
    /**
     * @param s H:m timestamp, i.e. [Hour in day (0-23)]:[Minute in hour (0-59)]
     * @return total minutes after 00:00
     */
        String[] hourMin = s.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;
        return hoursInMins + mins;
    }

    // Load items from pricelist csv
    public static void loadItems(){
        String fileName = "database/PhotoShop_PriceList.csv";
        File file = new File(fileName);

        // this gives you a 2-dimensional array of strings
        Scanner inputStream;

        try{
            inputStream = new Scanner(file);
            while(inputStream.hasNext()){
                String line = inputStream.nextLine();
                String[] values = line.split(";");
                // this adds the currently parsed line to the 2-dimensional string array
                Item items = createItem(values);
                catalogue.addItem(items);
            }
            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void displayCatalogue(){
        // Display the catalogue of items with the ID, price in euros, and time to complete the order
        System.out.println(String.format("%-5s %-30s %15s %20s" , "ID", "Item", "Price(EUR)", "Time to Make (min)"));
        System.out.println(catalogue.toString());
    }
}

public static void calculatePickup(Item item){
    LocalDateTime timeNow = LocalDateTime.now();
    int workDuration = item.getHours();
    System.out.println(timeNow.plus(workDuration, ChronoUnit.MINUTES));
    System.out.println(Days.calculateDayOfWeek());

    

    while(workDuration > 0){
        // Start now and cycle through days of the week csv
        
        
    }

}




