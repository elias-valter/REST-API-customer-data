package CustomerData.customer.repository;

import CustomerData.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * method to search for a specific customer who is associated with the email
     * @param email String value representing the specific email address
     * @return an Optional object filled with the specific customer
     */
    @Query("SELECT customer FROM Customer customer WHERE customer.email = ?1")
    Optional<Customer> findCustomerByEmail(String email);
}
