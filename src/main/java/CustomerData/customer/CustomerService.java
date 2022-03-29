package CustomerData.customer;

import CustomerData.customer.exception.CustomerAlreadyExistingException;
import CustomerData.customer.exception.CustomerNotFoundException;
import CustomerData.customer.exception.CustomerPasswordTooWeakException;
import CustomerData.customer.exception.CustomerProMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) throws CustomerNotFoundException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

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

    public List<Customer> findByDob(Date dob) throws CustomerNotFoundException {
        List<Customer> customers = customerRepository.findAll()
                .stream()
                .filter(customer -> customer.getDateOfBirth().equals(dob))
                .toList();

        if (customers.isEmpty())
            throw new CustomerNotFoundException(dob);

        return customers;
    }

    public List<Customer> findByAge(int age) throws CustomerNotFoundException {
        List<Customer> customers = customerRepository.findAll()
                .stream()
                .filter((customer -> customer.getAge() == age))
                .toList();

        if (customers.isEmpty())
            throw new CustomerNotFoundException(age);

        return customers;

    }

    public List<Customer> findAllProMembers() throws CustomerProMemberException {
        List<Customer> customers = customerRepository.findAll()
                .stream()
                .filter(Customer::isProMember)
                .toList();

        if (customers.isEmpty())
            throw new CustomerProMemberException(true);

        return customers;
    }

    public List<Customer> findAllNonProMembers() throws CustomerProMemberException {
        List<Customer> customers = customerRepository.findAll()
                .stream()
                .filter(customer -> !customer.isProMember())
                .toList();

        if (customers.isEmpty())
            throw new CustomerProMemberException(false);

        return customers;
    }

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

    public Long getNumberOfCustomers() {
        return customerRepository.findAll()
                .stream()
                .count();
    }

    public Long getNumberOfProMembers() {
        return customerRepository.findAll()
                .stream()
                .filter(Customer::isProMember)
                .count();
    }

    public Long getNumberOfNonProMembers() {
        return customerRepository.findAll()
                .stream()
                .filter(customer -> !customer.isProMember())
                .count();
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    public void deleteByEmail(String email) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(email);
        if (customerOptional.isPresent())
            customerRepository.deleteById(customerOptional.get().getId());
        else
            throw new CustomerNotFoundException(email);
    }

    public void addNew(Customer customer) throws CustomerAlreadyExistingException {
        Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(customer.getEmail());
        if(customerOptional.isPresent())
            throw new CustomerAlreadyExistingException(customer.getEmail());
        else
            customerRepository.save(customer);
    }

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
