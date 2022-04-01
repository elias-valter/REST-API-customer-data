package CustomerData.customer.exception;

public class CustomerAlreadyExistingException extends RuntimeException {

    public CustomerAlreadyExistingException(String email){
        super("Following email is already taken: " + email);
    }
}
