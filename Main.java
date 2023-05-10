import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.List;
import java.util.Scanner;

import repository.Customer;
import repository.Employee;
import repository.UserHandler;
import service.Catalogue;
import service.Days;
import service.Item;
import service.ShoppingCart;

public class Main {

    // First instance catalogue so daily catalogue can be loaded in
    static Catalogue catalogue = new Catalogue();
    static ShoppingCart cart = new ShoppingCart();
    static UserHandler users = new UserHandler();
    static Scanner scan = new Scanner(System.in);

    // Load in Opening Times of the shop
    static List<Days> openingTimes = Days.loadDays();

    // Tracker for user location in navigation
    static int userLocation = 0;

    public static void main(String[] args) {
        // Declare id of employer
        users.displayEmployees();
        System.out.println("Please select your employee ID: ");
        Employee currentEmployee = users.getEmployee(userNavigation() - 1);

        // Main store menu
        shopMenu();

        // Load daily prices
        loadItems();

        // Set quit condition for terminating application
        while (userLocation > -1){
            // Check for user input
            int option = userNavigation();

            switch(option){
                // Print catalogue here
                case 1:
                catalogue.printCatalogue();

                // Add item to shopping cart here
                purchaseMenu(currentEmployee);
                break;

                // Print order details - provide date for order pickup
                case 2:
                checkOrder();
                break;

                // Print invoice details / allow user to access order details from invoice no.
                case 3:
                checkInvoice();
                break;

                // Exit program condition
                case 0:
                userLocation = -1;

            }

        }
        
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
    public static void purchaseMenu(Employee employee){

        String status = "c";
        while (status.equals("c")) {
            System.out.print("Please choose a number between 1 - " + catalogue.getLength() + ": ");
            int choice = scan.nextInt() - 1;
            Item item = catalogue.getItem(choice);
            cart.addItem(item);
            System.out.print("You have added: " + item.getName());
            System.out.print("\nTo add another item type 'c' ");
            System.out.print("\nTo finalise your purchase type 'b': ");
            status = scan.next();

            // Check if user wants to make purchase
            if (status.equals("b")){
                checkOrder();
                Customer saveCustomer = customerEntry();
                cart.saveCart(cart, employee, saveCustomer);
                cart.exportJson();
            }
        }
    }

    public static Customer customerEntry(){
        String menuDivider = "---------------------------";

        System.out.println(menuDivider);
        System.out.println("Existing customer, new customer or guest?");
        System.out.println("Press 1 - for return customer");
        System.out.println("Press 2 - for new customer");
        System.out.println("Press 3 - for guest");
        System.out.println(menuDivider);

        int userChoice = userNavigation();
        switch(userChoice){
            case 1:
            users.displayCustomers();
            System.out.println("Please select the customer id: ");
            int option = userNavigation();
            Customer customer = users.getCustomer(option);
            return customer;
            
            case 2:
            Customer newCustomer = users.makeNewCustomer();
            return newCustomer;

            case 3:
            Customer guest = users.getCustomer(1);
            return guest;
        }

        return null;
    }

    // Function to check order for customer
    public static void checkOrder(){
        String menuDivider = "---------------------------";

        System.out.println(menuDivider);
        cart.displayCart();
        int workingDaysNeeded = Days.calculatePickup(cart.getTotalTimeTaken(), openingTimes);
        LocalDate dateNow = LocalDate.now();
        LocalDate finalDate = dateNow.plus(workingDaysNeeded, DAYS);
        cart.setPickupDate(finalDate);
        System.out.println("Order can be picked up on: " + finalDate);
        System.out.println(menuDivider);

        userLocation = -1;
    }

    public static void checkInvoice(){

    }

    // Function for user navigation
    public static int userNavigation(){
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

    

}
