package repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import com.opencsv.CSVWriter;

public class UserHandler {
    
    private ArrayList<Customer> customerList;
    private ArrayList<Employee> employeeList;

    public UserHandler(){
        this.customerList = Customer.loadCustomers();
        this.employeeList = Employee.loadEmployees();
    }

    public Employee getEmployee(int index){
        return this.employeeList.get(index);
    }

    public Customer getCustomer(int index){
        return this.customerList.get(index);
    }

    public void displayEmployees(){
        System.out.println(String.format("%-5s %30s %30s" , "ID", "First name", "Last name"));
        for (Employee person: this.employeeList) {
            System.out.println(person.toString());
        }
    }

    public void displayCustomers(){
        // expand this TODO
        System.out.println(String.format("%-5s %30s %30s" , "ID", "First name", "Last name"));
        for (Customer person: this.customerList) {
            System.out.println(person.toString());
        }
    }

    public Customer makeNewCustomer(){
        Scanner scan = new Scanner(System.in);
        String[] customerInfo = new String[8];
        String[] customerInfoHeader = {"ID", "firstName", "lastName", "address", "postcode", "city", "email", "mobile"};
        for(int i = 1;i < 8; i++){
            System.out.println("Please enter your " + customerInfoHeader[i] + ": ");
            customerInfo[i] = scan.nextLine();
        }
        scan.close();
        Customer newCustomer = Customer.createNewCustomer(customerInfo);
        UserHandler.saveCustomer(newCustomer);

        return newCustomer;
    }

    public static void saveCustomer(Customer newCustomer){
        try {
            // create FileWriter object with file as parameter
            Writer outputfile;
            outputfile = new BufferedWriter(new FileWriter("./user/PhotoShop_Customers.csv", true));
    
            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile, ';', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
    
            // add data to csv
            String[] data = {
                Integer.toString(newCustomer.getID()),
                newCustomer.getFirstName(),
                newCustomer.getLastName(),
                newCustomer.getAddress(),
                newCustomer.getPostcode(),
                newCustomer.getCity(),
                newCustomer.getEmail(),
                newCustomer.getMobile()
            };

            writer.writeNext(data);
    
            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            }
        }
    }


