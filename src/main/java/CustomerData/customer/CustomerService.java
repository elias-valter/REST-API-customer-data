package CustomerData.customer;

import CustomerData.customer.exception.CustomerNotFoundException;
import CustomerData.customer.exception.CustomerProMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public record CustomerService(CustomerRepository customerRepository) {

    @Autowired
    public CustomerService {
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

    public List<Customer> sortByName(boolean b) {
        //b true -> byLastName
        //b false -> byFirstName
        if (b)
            return customerRepository.findAll()
                    .stream()
                    .sorted(Comparator.comparing(Customer::getLastName))
                    .toList();

        return customerRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Customer::getFirstName))
                .toList();

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
}
