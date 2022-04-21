package CustomerData.customer.service;

import CustomerData.customer.Customer;
import CustomerData.customer.exception.CustomerAlreadyExistingException;
import CustomerData.customer.exception.CustomerNotFoundException;
import CustomerData.customer.exception.CustomerPasswordTooWeakException;
import CustomerData.customer.exception.CustomerProMemberException;
import CustomerData.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * constructor to create a CustomerRepositoryException, Dependency Injection
     * @param customerRepository CustomerRepository parameter to initialize the private object attribute
     */
    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * method to get all existing customers from the db
     * @return List of all existing customers
     */
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    /**
     * method to get a specific customer by id
     * @param id the customer's id
     * @return the specific customer which is represented by the id
     * @throws CustomerNotFoundException if id does not belong to a customer
     */
    public Customer findById(Long id) throws CustomerNotFoundException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    /**
     * method to find all customers with the specific input name
     * @param firstName String value representing the customers' firstname
     * @param lastName String value representing the customers' lastname
     * @return the specific customers stored in a list
     * @throws CustomerNotFoundException if there is no customer with this name
     */
    public List<Customer> findByName(String firstName, String lastName) throws CustomerNotFoundException {
        List<Customer> customers = customerRepository.findAll()
                .stream()
                .filter(first -> first.getFirstName().equals(firstName))
                .filter(last -> last.getFirstName().equals(lastName))
                .toList();

        if (customers.isEmpty())
            throw new CustomerNotFoundException(firstName, lastName);

        return customers;
    }

    /**
     * method to find all customers with the specific input email
     * @param email String value representing the customer's email adress
     * @return the specific customer of the associated email
     * @throws CustomerNotFoundException if there is no customer with this email
     */
    public Customer findByEmail(String email) throws CustomerNotFoundException {
        Customer customer = customerRepository.findAll()
                .stream()
                .filter(x -> x.getEmail().equals(email))
                .toList()
                .get(0);

        if (customer == null)
            throw new CustomerNotFoundException(email);

        return customer;
    }

    /**
     * method to find all customers who are born at the {@param dob}
     * @param dob Date value representing the customers' date of birth
     * @return List filled with customers who are born at the {@param dob}
     * @throws CustomerNotFoundException if there is no customer with this date of birth
     */
    public List<Customer> findByDob(Date dob) throws CustomerNotFoundException {
        List<Customer> customers = customerRepository.findAll()
                .stream()
                .filter(customer -> customer.getDateOfBirth().equals(dob))
                .toList();

        if (customers.isEmpty())
            throw new CustomerNotFoundException(dob);

        return customers;
    }

    /**
     * method finds all customers with the associated input age
     * @param age int value representing the customers' age
     * @return List filled with all customers who are {@param age} years old
     * @throws CustomerNotFoundException if there is no customer with the input age
     */
    public List<Customer> findByAge(int age) throws CustomerNotFoundException {
        List<Customer> customers = customerRepository.findAll()
                .stream()
                .filter((customer -> customer.getAge() == age))
                .toList();

        if (customers.isEmpty())
            throw new CustomerNotFoundException(age);

        return customers;

    }

    /**
     * method finds all customers who have a pro-membership
     * @return List filled with all pro-member customers
     * @throws CustomerProMemberException if there is no customer with a pro-membership
     */
    public List<Customer> findAllProMembers() throws CustomerProMemberException {
        List<Customer> customers = customerRepository.findAll()
                .stream()
                .filter(Customer::isProMember)
                .toList();

        if (customers.isEmpty())
            throw new CustomerProMemberException(true);

        return customers;
    }

    /**
     * method finds all customers who have a non pro-membership
     * @return List filled with all non pro-member customers
     * @throws CustomerProMemberException if there is no customer without a pro-membership
     */
    public List<Customer> findAllNonProMembers() throws CustomerProMemberException {
        List<Customer> customers = customerRepository.findAll()
                .stream()
                .filter(customer -> !customer.isProMember())
                .toList();

        if (customers.isEmpty())
            throw new CustomerProMemberException(false);

        return customers;
    }

    /**
     * method sorts all existing customers by their age (descending or ascending) and returns the sorted list
     * @param b boolean parameter to decide in which order the list should be sorted
     * @return list filled with customers sorted bei der age
     */
    public List<Customer> sortByDob(boolean b) {
        //b true -> oldest first
        //b false -> youngest first
        if(b)
        return customerRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Customer::getDateOfBirth).reversed())
                .toList();

        return customerRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Customer::getDateOfBirth))
                .toList();
    }

    /**
     * method counts the overall number of existing customers
     * @return int value representing the number of customers
     */
    public Long getNumberOfCustomers() {
        return customerRepository.findAll()
                .stream()
                .count();
    }

    /**
     * method counts the overall number of existing customers with a pro-membership
     * @return int value representing the number of pro-members
     */
    public Long getNumberOfProMembers() {
        return customerRepository.findAll()
                .stream()
                .filter(Customer::isProMember)
                .count();
    }

    /**
     * method counts the overall number of existing customers without a pro-membership
     * @return int value representing the number of non pro-members
     */
    public Long getNumberOfNonProMembers() {
        return customerRepository.findAll()
                .stream()
                .filter(customer -> !customer.isProMember())
                .count();
    }

    /**
     * method deletes the customer with the specific {@param id}  from the database
     * @param id Long value representing the customers id
     */
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    /**
     * method deletes the customer with the specific {@param email}  from the database
     * @param email String value representing the customers email
     */
    public void deleteByEmail(String email) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(email);
        if (customerOptional.isPresent())
            customerRepository.deleteById(customerOptional.get().getId());
        else
            throw new CustomerNotFoundException(email);
    }

    /**
     * method adds a new customer to the database
     * @param customer Customer value which is added to the database
     * @throws CustomerAlreadyExistingException if there is already a customer with the same id/email in the database
     */
    public void addNew(Customer customer) throws CustomerAlreadyExistingException {
        Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(customer.getEmail());
        if(customerOptional.isPresent())
            throw new CustomerAlreadyExistingException(customer.getEmail());
        else
            customerRepository.save(customer);
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
    @Transactional
    public void update(Long id,
                       String firstName,
                       String lastName,
                       int age,
                       Date dateOfBirth,
                       String email,
                       String password,
                       boolean proMember) throws CustomerNotFoundException, CustomerAlreadyExistingException, CustomerPasswordTooWeakException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        if(firstName != null && firstName.length() > 0 && !Objects.equals(customer.getFirstName(), firstName)) {
            customer.setFirstName(firstName);
        }
        if(lastName != null && lastName.length() > 0 && !Objects.equals(customer.getLastName(), lastName)) {
            customer.setLastName(lastName);
        }
        if(age > 0 && !Objects.equals(customer.getAge(), age)){
            customer.setAge(age);
        }
        if(dateOfBirth != null && dateOfBirth.before(Date.from(Instant.from(LocalDateTime.now())))){
            customer.setDateOfBirth(dateOfBirth);
        }

        if(email != null && email.length() > 0 && !Objects.equals(customer.getEmail(), email)) {
            Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(email);
            if(customerOptional.isPresent()) {
                throw new CustomerAlreadyExistingException(email);
            }
            customer.setEmail(email);
        }

        if(password != null && !Objects.equals(customer.getPassword(), password)){
            if(password.length() <  15){
                throw new CustomerPasswordTooWeakException(password);
            }
            customer.setPassword(password);
        }

        if(!Objects.equals(customer.isProMember(), proMember)) {
            customer.setProMember(proMember);
        }

    }
}
