package CustomerData.customer.exception;

import java.util.Date;

public class CustomerNotFoundException extends RuntimeException{

    /**
     * constructor to initialize the CustomerNotFoundException
     * message concerning in non-valid Id input
     * @param id Long value representing a customer's id
     */
    public CustomerNotFoundException(Long id){
        super("No customer found with following ID: " + id);
    }

    /**
     * constructor to initialize the CustomerNotFoundException
     * message concerning a non-existing name input
     * @param firstName String value representing the firstname
     * @param lastName String value representing the lastname
     */
    public CustomerNotFoundException(String firstName, String lastName){
        super("No customer found with the follwing first name: " + firstName + " and the following name: " + lastName);
    }

    /**
     * constructor to initialize the CustomerNotFoundException
     * message concerning a non-existing email input
     * @param email String value representing the email
     */
    public CustomerNotFoundException(String email){
        super("No customer found with the following email: " + email);
    }

    /**
     * constructor to initialize the CustomerNotFoundException
     * message concerning a non-existing date of birth input
     * @param dob Date value representing the date of birth
     */
    public CustomerNotFoundException(Date dob){
        super("No customer found with the following date of birth: " + dob.toString());
    }

    /**
     * constructor to initialize the CustomerNotFoundException
     * message concerning a non-existing age input
     * @param age int value representing the customer's age
     */
    public CustomerNotFoundException(int age){
        super("No customer found with the following age: " + age);
    }
}
