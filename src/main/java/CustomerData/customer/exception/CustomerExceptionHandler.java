package CustomerData.customer.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class CustomerExceptionHandler {

    /**
     * method to handle the CustomerNotFoundException's behavior
     * @param e the specific exception
     * @return a new response entity including the http status code
     */
    @ExceptionHandler(value = CustomerNotFoundException.class)
    public ResponseEntity<Object> handleCustomerException(CustomerNotFoundException e){
        //payload containing exception details
        CustomerException customerException = new CustomerException(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));
        //return actual response entity
        return new ResponseEntity<>(customerException, HttpStatus.BAD_REQUEST);
    }

    /**
     * method to handle the CustomerAlreadyExistingException's behavior
     * @param e the specific exception
     * @return a new response entity including the http status code
     */
    @ExceptionHandler(value = CustomerAlreadyExistingException.class)
    public ResponseEntity<Object> handleCustomerAlreadyExistingException(CustomerAlreadyExistingException e){
        CustomerException customerException = new CustomerException(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(customerException, HttpStatus.BAD_REQUEST);
    }

    /**
     * method to handle the CustomerProMemberShipException's behavior
     * @param e the specific exception
     * @return a new response entity including the http status code
     */
    @ExceptionHandler(value = CustomerProMemberException.class)
    public ResponseEntity<Object> handleCustomerProMemberException(CustomerProMemberException e){
        CustomerException customerException = new CustomerException(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(customerException, HttpStatus.BAD_REQUEST);
    }
}
