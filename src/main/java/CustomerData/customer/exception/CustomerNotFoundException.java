package CustomerData.customer.exception;

import java.util.Date;

public class CustomerNotFoundException extends Exception{

    public CustomerNotFoundException(Long id){
        super("No customer found with following ID: " + id);
    }

    public CustomerNotFoundException(String firstName, String lastName){
        super("No customer found with the follwing first name: " + firstName + " and the following name: " + lastName);
    }

    public CustomerNotFoundException(String email){
        super("No customer found with the following email: " + email);
    }

    public CustomerNotFoundException(Date dob){
        super("No customer found with the following date of birth: " + dob.toString());
    }

    public CustomerNotFoundException(int age){
        super("No customer found with the following age: " + age);
    }
}
