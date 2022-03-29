package CustomerData.customer;

import CustomerData.customer.exception.CustomerAlreadyExistingExceptions;
import CustomerData.customer.exception.CustomerNotFoundException;
import CustomerData.customer.exception.CustomerPasswordTooWeakException;
import CustomerData.customer.exception.CustomerProMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/customer/")
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

    /**
     * method to get all existing customers from the db
     * @return List of all existing customers
     */
    @GetMapping("get/all")
    List<Customer> findAllCustomers(){
        return customerService.findAll();
    }

    @GetMapping("get/byId/{id}")
    Customer findCustomerById(@PathVariable Long id) throws CustomerNotFoundException {
        return customerService.findById(id);
    }

    @GetMapping("get/byName")
    List<Customer> findByName(@RequestParam(required = false) String firstName,
                              @RequestParam(required = false) String lastName) throws CustomerNotFoundException {
        return customerService.findByName(firstName, lastName);
    }

    @GetMapping("get/byEmail{email}")
    Customer findByEmail(@PathVariable String email) throws CustomerNotFoundException {
        return customerService.findByEmail(email);
    }

    @GetMapping("get/sortedByAgeOldestFirst")
    List<Customer> sortByAgeOldest(){
        return customerService.sortByDob(true);
    }

    @GetMapping("get/SortedByAgeYoungestFirst")
    List<Customer> sortByAgeYoungest(){
        return customerService.sortByDob(false);
    }

    @GetMapping("get/byAge/{age}")
    List<Customer> findByAge(@PathVariable int age) throws CustomerNotFoundException {
        return customerService.findByAge(age);
    }

    @GetMapping("get/findProMembers")
    List<Customer> findAllProMembers() throws CustomerProMemberException {
        return customerService.findAllProMembers();
    }

    @GetMapping("get/findNonProMembers")
    List<Customer> findAllNonProMembers() throws CustomerProMemberException {
        return customerService.findAllNonProMembers();
    }

    @GetMapping("get/findByDob/{dob}")
    List<Customer> findByDob(@PathVariable Date dob) throws CustomerNotFoundException {
        return customerService.findByDob(dob);
    }

    @GetMapping("get/NumberOfCustomers")
    Long getNumberOfCustomers(){
        return customerService.getNumberOfCustomers();
    }

    @GetMapping("get/NumberOfProCustomers")
    Long getNumberOfProMembers(){
        return customerService.getNumberOfProMembers();
    }

    @GetMapping("get/NumberOfNonProCustomers")
    Long getNumberOfNonProCustomers(){
        return customerService.getNumberOfNonProMembers();
    }


    @DeleteMapping("/delete/ById/{id}")
    void deleteCustomerByID(@PathVariable Long id) {
        customerService.deleteById(id);
    }

    @DeleteMapping("/delete/ByEmail/{email}")
    void deleteCustomerByEmail(@PathVariable String email) throws CustomerNotFoundException {
        customerService.deleteByEmail(email);
    }

    @PostMapping("/post/add")
    void registerNewCustomer(@RequestBody Customer customer) throws CustomerAlreadyExistingExceptions {
        customerService.addNew(customer);
    }

    @PutMapping("/updateById/{id}")
    void updateEverything(@PathVariable("id") Long Id,
                          @RequestParam(required = false) String firstName,
                          @RequestParam(required = false) String lastName,
                          @RequestParam(required = false) int age,
                          @RequestParam(required = false) Date dateOfBirth,
                          @RequestParam(required = false) String email,
                          @RequestParam(required = false) String password,
                          @RequestParam(required = false) boolean proMember) throws CustomerAlreadyExistingExceptions,
            CustomerNotFoundException, CustomerPasswordTooWeakException {
        customerService.update(Id, firstName, lastName, age, dateOfBirth, email, password, proMember);
    }



}
