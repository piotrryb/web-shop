package hibernate.shop;

import hibernate.shop.cart.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
