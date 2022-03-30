package CustomerData.order;

import CustomerData.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OderRepository extends JpaRepository<Order, Long> {

    /*
    @Query("SELECT customer FROM Order WHERE customer.id = ?1")
    Optional<Customer> findConcerningCustomer(Customer customer);
     */
}
