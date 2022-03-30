package CustomerData.customer;


import CustomerData.order.Order;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private Date dateOfBirth;
    private String email;
    private String password;
    private boolean isProMember;
    @OneToMany
    private List<Order> orders;
}
