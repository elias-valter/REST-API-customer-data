package CustomerData.customer.exception;

public class CustomerAlreadyExistingExceptions extends Exception {

    public CustomerAlreadyExistingExceptions(String email){
        super("Following email is already taken: " + email);
    }
}
