package repository;

import java.util.ArrayList;

public class UserHandler {
    
    private ArrayList<Customer> customerList;
    private ArrayList<Employee> employeeList;

    public UserHandler(){
        this.customerList = Customer.loadCustomers();
        this.employeeList = Employee.loadEmployees();
    }

    
}
