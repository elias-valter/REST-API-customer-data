package CustomerData.customer;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
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

    public Customer(Long id,
                    String firstName,
                    String lastName,
                    int age,
                    Date dateOfBirth,
                    String email,
                    String password,
                    boolean isProMember) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.isProMember = isProMember;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return age == customer.age && isProMember == customer.isProMember && Objects.equals(id, customer.id) &&
                Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) &&
                Objects.equals(dateOfBirth, customer.dateOfBirth) && Objects.equals(email, customer.email) &&
                Objects.equals(password, customer.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, age, dateOfBirth, email, password, isProMember);
    }
}
