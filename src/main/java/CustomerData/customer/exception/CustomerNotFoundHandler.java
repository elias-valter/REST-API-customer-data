package CustomerData.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class CustomerNotFoundHandler {

    @ExceptionHandler(value = CustomerNotFoundException.class)
    public ResponseEntity<Object> handleCustomerException(CustomerNotFoundException e){
        //payload containing exception details
        CustomerException customerException = new CustomerException(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));
        //return actual response entity
        return new ResponseEntity<>(customerException, HttpStatus.BAD_REQUEST);

    }

}
