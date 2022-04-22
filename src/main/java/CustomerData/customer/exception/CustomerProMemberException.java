package CustomerData.customer.exception;

public class CustomerProMemberException extends RuntimeException {
    /**
     * constructor to initialize the CustomerProMemberException, calls helper method to generate the message
     * @param b
     */
    public CustomerProMemberException(boolean b){
        super(CustomerProMemberException.getMessage(b));
    }

    /**
     * helper method to generate the message for the CustomerProMemberException
     * @param b boolean decide which String should be returned
     * @return String value representing the exception's message
     */
    private static String getMessage(boolean b){
        if(b)
            return "no pro members found";

        return "no non pro members found";
    }
}
