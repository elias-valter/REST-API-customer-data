package CustomerData.customer;

import CustomerData.customer.exception.CustomerNotFoundException;
import CustomerData.customer.exception.CustomerProMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/customer/")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    /**
     * constructor to initialize customerService and customerRepository
     * @param customerService parameter from type CustomerService to initialize customerService
     * @param customerRepository parameter from type CustomerRepository to initialize
     *                           customerRepository
     */
    @Autowired
    public CustomerController(CustomerService customerService,
                              CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    /*
    GET
     */

    /**
     * method to get all existing customers from the db
     * @return List of all existing customers
     */
    @GetMapping("get/all/")
    List<Customer> findAllCustomers(){
        return customerService.findAll();
    }

    @GetMapping("get/byId/{id}/")
    Customer findCustomerById(@PathVariable Long id) throws CustomerNotFoundException {
        return customerService.findById(id);
    }

    @GetMapping("get/byName/")
    List<Customer> findByName(@RequestParam(required = false) String firstName,
                              @RequestParam(required = false) String lastName) throws CustomerNotFoundException {
        return customerService.findByName(firstName, lastName);
    }

    @GetMapping("get/sortByLastName/")
    List<Customer> sortByLastName(){
        return customerService.sortByName(true);
    }

    @GetMapping("get/sortByFirstName/")
    List<Customer> sortByFirstName(){
        return customerService.sortByName(false);
    }

    @GetMapping("get/byEmail{email}/")
    Customer findByEmail(@PathVariable String email) throws CustomerNotFoundException {
        return customerService.findByEmail(email);
    }

    /*
    @GetMapping("get/sortByDobOldest/")
    List<Customer> sortByDob(){
        return customerService.sortByDob(true);
    }

    @GetMapping("get/sortByDobYoungest/")
    List<Customer> sortByDob(){
        return customerService.sortByDob(false);
    }
    */

    @GetMapping("get/byAge/{age}/")
    List<Customer> findByAge(@PathVariable int age) throws CustomerNotFoundException {
        return customerService.findByAge(age);
    }

    @GetMapping("get/findProMembers/")
    List<Customer> findAllProMembers() throws CustomerProMemberException {
        return customerService.findAllProMembers();
    }

    @GetMapping("get/findNonProMembers/")
    List<Customer> findAllNonProMembers() throws CustomerProMemberException {
        return customerService.findAllNonProMembers();
    }

    @GetMapping("get/findByDob/{dob}/")
    List<Customer> findByDob(@PathVariable Date dob) throws CustomerNotFoundException {
        return customerService.findByDob(dob);
    }

    /*
    DELETE
     */

    @DeleteMapping("/delete/ById/{id}/")
    void deleteCustomerByID(@PathVariable Long id) {
        customerService.deleteById(id);
    }

    @DeleteMapping("/delete/ByEmail/{email}/")
    void deleteCustomerByEmail(@PathVariable String email) {
        customerService.deleteByEmail(email);
    }

    /*
    POST
     */

    @PostMapping("/post/add/")
    void registerNewCustomer(@RequestBody Customer customer){
        customerService.addNew(customer);
    }


    /*
    PUT
     */

    @PutMapping("/updateById/{id}")
    void updateEverything(@PathVariable("id") Long Id,
                          @RequestParam(required = false) String firstName,
                          @RequestParam(required = false) String lastName,
                          @RequestParam(required = false) String email,
                          @RequestParam(required = false) boolean proMember) {
        customerService.update(Id, firstName, lastName, email, proMember);
    }

}
