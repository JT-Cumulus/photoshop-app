package repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer {

    private int ID;
    private String firstName;
    private String lastName;
    private String address;
    private String postcode;
    private String city;
    private String email;
    private String mobile;

    public Customer(int ID, String firstName, String lastName, String address, String postcode, String city, String email, String mobile) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postcode = postcode;
        this.city = city;
        this.email = email;
        this.mobile = mobile;
    }

    private static Customer createCustomer(String[] metadata) {
        int id = Integer.parseInt(metadata[0]);
        String firstName = metadata[1];
        String lastName = metadata[2];
        String address = metadata[3];
        String postcode = metadata[4];
        String city = metadata[5];
        String email = metadata[6];
        String mobile = metadata[7];

        return new Customer(id, firstName, lastName, address, postcode, city, email, mobile);
    }

    public static ArrayList<Customer> loadCustomers(){
        String fileName = "user/PhotoShop_Customers.csv";
        File file = new File(fileName);
        ArrayList<Customer> customerContainer = new ArrayList<>();
        Scanner inputStream;

        try{
            inputStream = new Scanner(file);
            while(inputStream.hasNext()){
                String line = inputStream.nextLine();
                String[] values = line.split(";");
                Customer customer = createCustomer(values);
                customerContainer.add(customer);
            }
            inputStream.close();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return customerContainer;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


}
