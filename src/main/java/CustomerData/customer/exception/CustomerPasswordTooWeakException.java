package CustomerData.customer.exception;

public class CustomerPasswordTooWeakException extends Exception{

    /**
     * constructor to initialize the CustomerPasswordToWeakException
     * @param password String value representing the customer's password
     */
    public CustomerPasswordTooWeakException(String password){
        super("The following password is too weak: " + password + "\n choose a stronger one");
    }
}
