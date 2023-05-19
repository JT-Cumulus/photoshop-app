import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import repository.Customer;
import repository.Employee;
import repository.UserHandler;
import service.Catalogue;
import service.Days;
import service.Invoice;
import service.Item;
import service.Order;
import service.ShoppingCart;

public class Main {

    // First instance catalogue so daily catalogue can be loaded in
    static Catalogue catalogue = new Catalogue();
    static ShoppingCart cart = new ShoppingCart();
    static UserHandler users = new UserHandler();
    static Scanner scan = new Scanner(System.in);

    // Tracker for user location in navigation
    static int userLocation = 0;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // Load in Opening Times of the shop
        List<Days> openingTimes = Days.loadDays();
        Days days = new Days();

        // Declare id of employer
        users.displayEmployees();
        System.out.println("Please select your employee ID: ");
        Employee currentEmployee = users.getEmployee(userNavigation());

        // Main store menu
        shopMenu();

        // Load daily prices
        catalogue = Item.loadItems(catalogue);

        // Set quit condition for terminating application
        while (userLocation > -1){
            // Check for user input
            int option = userNavigation();

            switch(option){
                case 1:
                // Add item to shopping cart here
                purchaseMenu(currentEmployee, days, openingTimes);
                break;

                // Print order details - provide date for order pickup
                case 2:
                checkOrder(days, openingTimes);
                break;

                // Print invoice details / allow user to access order details from invoice no.
                case 3:
                checkInvoice(users, catalogue);
                break;

                // Exit program condition
                case 0:
                userLocation = -1;

            }
        }
        
        // Close scanner at end
        scan.close();
    }

    // Function for user navigation
    public static int userNavigation(){
        int userChoice = 0;
        while (userChoice < 1){
            try {
                userChoice = scan.nextInt();
            } catch(Exception e) { 
                System.out.println("Invalid input, try again");
                break;
            }
        }
        return userChoice;
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
    public static void purchaseMenu(Employee employee, Days days, List<Days> openingTimes){

        String status = "c";
        while (status.equals("c")) {
            catalogue.printCatalogue();
            System.out.print("Please choose a number between 1 - " + catalogue.getLength() + ": ");
            int choice = scan.nextInt();
            Item item = catalogue.getItem(choice);
            cart.addItem(item);
            System.out.print("You have added: " + item.getName());
            System.out.print("\nTo add another item type 'c' ");
            System.out.print("\nTo remove an item type 'v' ");
            System.out.print("\nTo finalise your purchase type 'b': ");
            status = scan.next();

            // Check if user wants to remove item
            if (status.equals("v")){
                cart.displayCart();
                System.out.print("Please choose the item to remove: ");
                int choiceRemove = scan.nextInt();
                Item itemRemoved = cart.getIteminCart(catalogue.getItem(choiceRemove));
                cart.removeItem(itemRemoved);
                System.out.println("Your cart now:");
                cart.displayCart();
                status = "c";
            } 

            // Check if user wants to make purchase
            if (status.equals("b")){
                displayOrder(days, openingTimes);
                Customer saveCustomer = customerEntry();
                // Possibly merge these into one function
                cart.saveCart(cart, employee, saveCustomer);
                cart.exportJson(cart);
            } 
        }
    }

    // Function for entering customer information
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
            guest.setID(1);
            return guest;
        }

        return null;
    }

    // Function to check order for customer
    public static void displayOrder(Days days, List<Days> openingTimes){
        String menuDivider = "---------------------------";

        System.out.println(menuDivider);
        cart.displayCart();
        int workingDaysNeeded = days.calculatePickup(cart.getTotalTimeTaken(), openingTimes);
        LocalDate dateNow = LocalDate.now();
        LocalDate finalDate = dateNow.plus(workingDaysNeeded, DAYS);
        cart.setPickupDate(finalDate);
        System.out.println("Order can be picked up on: " + finalDate);
        System.out.println(menuDivider);

        userLocation = -1;
    }

    // Function to check an order from its ID
    public static void checkOrder(Days days, List<Days> openingTimes) throws FileNotFoundException, IOException{
        Invoice newInvoice = new Invoice();
        System.out.println("Please enter your order ID: ");
        int orderID = userNavigation();
        Order order = newInvoice.findInvoice(orderID, catalogue);

        order.displayOrder();
        order.displayPickupTime(days, openingTimes);
        userLocation = -1;
    }

    // Check invoice navigation - prints out the invoice of the order id
    public static void checkInvoice(UserHandler user, Catalogue catalogue) throws FileNotFoundException, IOException{
        Invoice newInvoice = new Invoice();
        System.out.println("Please enter your order ID: ");
        int orderID = userNavigation();
        String[] userInfo = users.getInfo(orderID);
        Order order = newInvoice.findInvoice(orderID, catalogue);

        userLocation = -1;
        Customer chosenCustomer = user.getCustomer(Integer.parseInt(userInfo[1]));
        Employee chosenEmployee = user.getEmployee(Integer.parseInt(userInfo[4]));
        String orderDate = userInfo[2];
        
        newInvoice = new Invoice(chosenEmployee, chosenCustomer, order);
        newInvoice.displayInvoice(orderDate);
        order.displayOrder();

    }

}
