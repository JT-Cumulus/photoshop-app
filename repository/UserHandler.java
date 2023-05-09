package repository;

import java.util.ArrayList;

public class UserHandler {
    
    private ArrayList<Customer> customerList;
    private ArrayList<Employee> employeeList;

    public UserHandler(){
        this.customerList = Customer.loadCustomers();
        this.employeeList = Employee.loadEmployees();
    }

    public Employee getEmployee(int index){
        return new Employee(employeeList.get(index));
    }

    public Customer getCustomer(int index){
        return new Customer(customerList.get(index));
    }

    public void displayEmployees(){
        System.out.println(String.format("%-5s %30s %30s" , "ID", "First name", "Last name"));
        for (Employee person: this.employeeList) {
            System.out.println(person.toString());
        }
    }

    public void displayCustomers(){
        System.out.println(String.format("%-5s %30s %30s" , "ID", "First name", "Last name"));
        for (Customer person: this.customerList) {
            System.out.println(person.toString());
        }
    }
}
