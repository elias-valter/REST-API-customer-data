package CustomerData.customer.exception;

public class CustomerProMemberException extends RuntimeException {

    public CustomerProMemberException(boolean b){
        super(CustomerProMemberException.getMessage(b));
    }

    private static String getMessage(boolean b){
        if(b)
            return "no pro members found";

        return "no non pro members found";
    }
}
