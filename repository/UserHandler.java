package repository;

import java.util.ArrayList;
import java.util.Scanner;

public class UserHandler {
    
    private ArrayList<Customer> customerList;
    private ArrayList<Employee> employeeList;

    public UserHandler(){
        this.customerList = Customer.loadCustomers();
        this.employeeList = Employee.loadEmployees();
    }

    public static int checkEmployee(UserHandler handler){
        Scanner scan = new Scanner(System.in);
        System.out.println(String.format("%-5s %25s %25s" , "ID", "First Name", "Last Name"));
        for (Employee employee: handler.employeeList) {
            System.out.print(String.format("%-5s %25s %25s", employee.getEmployeeId(), employee.getFirstName(), employee.getLastName()));
        }
        // TODO
        System.out.print("\nPlease choose an integer between 1 - " + handler.employeeList.getLength() + ": ");
        int choice = scan.nextInt() - 1;
        Item item = catalogue.getItem(choice);
        cart.addItem(item);
        System.out.print("You have added: " + item.getName());
        System.out.print("\nTo add another item type 'c' ");
        System.out.print("\nTo finalise your purchase type 'b': ");
        status = scan.next();
        
    }

    // getEmployee()

    //
}
