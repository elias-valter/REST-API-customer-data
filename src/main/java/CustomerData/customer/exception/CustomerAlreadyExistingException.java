package CustomerData.customer.exception;

public class CustomerAlreadyExistingException extends Exception {

    public CustomerAlreadyExistingException(String email){
        super("Following email is already taken: " + email);
    }
}
