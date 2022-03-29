package CustomerData.customer.exception;

public class CustomerPasswordTooWeakException extends Exception{

    public CustomerPasswordTooWeakException(String password){
        super("The following password is too weak: " + password + "\n choose a stronger one");
    }
}
