package CustomerData.customer.controller;

import CustomerData.customer.Customer;
import CustomerData.customer.repository.CustomerRepository;
import CustomerData.customer.service.CustomerService;
import CustomerData.customer.exception.CustomerAlreadyExistingException;
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
    private final CustomerRepository customerRepository;  //dependency injection

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

    /**
     * method to get a specific customer by id
     * @param id the customer's id
     * @return the specific customer which is represented by the id
     * @throws CustomerNotFoundException if id does not belong to a customer
     */
    @GetMapping("get/byId/{id}")
    Customer findCustomerById(@PathVariable Long id) throws CustomerNotFoundException {
        return customerService.findById(id);
    }

    /**
     * method to find all customers with the specific input name
     * @param firstName String value representing the customers' firstname
     * @param lastName String value representing the customers' lastname
     * @return the specific customers stored in a list
     * @throws CustomerNotFoundException if there is no customer with this name
     */
    @GetMapping("get/byName")
    List<Customer> findByName(@RequestParam(required = false) String firstName,
                              @RequestParam(required = false) String lastName) throws CustomerNotFoundException {
        return customerService.findByName(firstName, lastName);
    }

    /**
     * method to find all customers with the specific input email
     * @param email String value representing the customer's email adress
     * @return the specific customer of the associated email
     * @throws CustomerNotFoundException if there is no customer with this email
     */
    @GetMapping("get/byEmail{email}")
    Customer findByEmail(@PathVariable String email) throws CustomerNotFoundException {
        return customerService.findByEmail(email);
    }

    /**
     * method sorts all existing customers by their age (ascending) and returns the sorted list
     * @return list filled with customers sorted bei der age (the oldest first)
     */
    @GetMapping("get/sortedByAgeOldestFirst")
    List<Customer> sortByAgeOldest(){
        return customerService.sortByDob(true);
    }

    /**
     * method sorts all existing customers by their age (descending) and returns the sorted list
     * @return list filled with customers sorted bei der age (the youngest first)
     */
    @GetMapping("get/SortedByAgeYoungestFirst")
    List<Customer> sortByAgeYoungest(){
        return customerService.sortByDob(false);
    }

    /**
     * method finds all customers with the associated input age
     * @param age int value representing the customers' age
     * @return List filled with all customers who are {@param age} years old
     * @throws CustomerNotFoundException if there is no customer with the input age
     */
    @GetMapping("get/byAge/{age}")
    List<Customer> findByAge(@PathVariable int age) throws CustomerNotFoundException {
        return customerService.findByAge(age);
    }

    /**
     * method finds all customers who have a pro-membership
     * @return List filled with all pro-member customers
     * @throws CustomerProMemberException if there is no customer with a pro-membership
     */
    @GetMapping("get/findProMembers")
    List<Customer> findAllProMembers() throws CustomerProMemberException {
        return customerService.findAllProMembers();
    }

    /**
     * method finds all customers who have a non pro-membership
     * @return List filled with all non pro-member customers
     * @throws CustomerProMemberException if there is no customer without a pro-membership
     */
    @GetMapping("get/findNonProMembers")
    List<Customer> findAllNonProMembers() throws CustomerProMemberException {
        return customerService.findAllNonProMembers();
    }

    /**
     * method to find all customers who are born at the {@param dob}
     * @param dob Date value representing the customers' date of birth
     * @return List filled with customers who are born at the {@param dob}
     * @throws CustomerNotFoundException if there is no customer with this date of birth
     */
    @GetMapping("get/findByDob/{dob}")
    List<Customer> findByDob(@PathVariable Date dob) throws CustomerNotFoundException {
        return customerService.findByDob(dob);
    }

    /**
     * method counts the overall number of existing customers
     * @return int value representing the number of customers
     */
    @GetMapping("get/NumberOfCustomers")
    Long getNumberOfCustomers(){
        return customerService.getNumberOfCustomers();
    }

    /**
     * method counts the overall number of existing customers with a pro-membership
     * @return int value representing the number of pro-members
     */
    @GetMapping("get/NumberOfProCustomers")
    Long getNumberOfProMembers(){
        return customerService.getNumberOfProMembers();
    }

    /**
     * method counts the overall number of existing customers without a pro-membership
     * @return int value representing the number of non pro-members
     */
    @GetMapping("get/NumberOfNonProCustomers")
    Long getNumberOfNonProCustomers(){
        return customerService.getNumberOfNonProMembers();
    }


    /**
     * method deletes the customer with the specific {@param id}  from the database
     * @param id Long value representing the customers id
     */
    @DeleteMapping("/delete/ById/{id}")
    void deleteCustomerByID(@PathVariable Long id) {
        customerService.deleteById(id);
    }

    /**
     * method deletes the customer with the specific {@param email}  from the database
     * @param email String value representing the customers email
     */
    @DeleteMapping("/delete/ByEmail/{email}")
    void deleteCustomerByEmail(@PathVariable String email) throws CustomerNotFoundException {
        customerService.deleteByEmail(email);
    }

    /**
     * method adds a new customer to the database
     * @param customer Customer value which is added to the database
     * @throws CustomerAlreadyExistingException if there is already a customer with the same id/email in the database
     */
    @PostMapping("/post/add")
    void registerNewCustomer(@RequestBody Customer customer) throws CustomerAlreadyExistingException {
        customerService.addNew(customer);
    }

    /**
     * method updates a specific customer trait or all traits in the database
     * @param Id Long value representing the id
     * @param firstName String value representing the firstname
     * @param lastName String value representing the lastname
     * @param age int value representing the age
     * @param dateOfBirth Date value representing the date of birth
     * @param email String value representing the email
     * @param password String value representing the password
     * @param proMember boolean value representing the pro-membership
     * @throws CustomerAlreadyExistingException if the new chosen email is already existing
     * @throws CustomerNotFoundException if there is no customers with the specific id
     * @throws CustomerPasswordTooWeakException if the new chosen password is to week
     */
    @PutMapping("/updateById/{id}")
    void updateEverything(@PathVariable("id") Long Id,
                          @RequestParam(required = false) String firstName,
                          @RequestParam(required = false) String lastName,
                          @RequestParam(required = false) int age,
                          @RequestParam(required = false) Date dateOfBirth,
                          @RequestParam(required = false) String email,
                          @RequestParam(required = false) String password,
                          @RequestParam(required = false) boolean proMember) throws CustomerAlreadyExistingException,
            CustomerNotFoundException, CustomerPasswordTooWeakException {
        customerService.update(Id, firstName, lastName, age, dateOfBirth, email, password, proMember);
    }



}
