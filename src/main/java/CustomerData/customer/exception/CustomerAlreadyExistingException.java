package CustomerData.customer.exception;

public class CustomerAlreadyExistingException extends RuntimeException {

    /**
     * constructor to initialize the CustomerAlreadyExisting exception
     * @param email String value representing the email, is appended to the exception's message
     */
    public CustomerAlreadyExistingException(String email){
        super("Following email is already taken: " + email);
    }
}
