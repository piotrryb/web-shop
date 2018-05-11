package hibernate.shop.domain;

import hibernate.shop.Order;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"productRatingSet", "orderSet"})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String email;
    String password;
    String firstName;
    String lastName;

    @OneToOne (mappedBy = "user")
    Cart cart;

    @OneToMany(mappedBy = "user")
    Set<ProductRating> productRatingSet;

    @OneToMany(mappedBy = "user")
    Set<Order> orderSet;
}
