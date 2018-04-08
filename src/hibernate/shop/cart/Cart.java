package hibernate.shop.cart;

import hibernate.shop.Price;
import hibernate.shop.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"cartDetailSet", "user"})
public class Cart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<CartDetail> cartDetailSet;

    @OneToOne
    User user;

    @Transient
    Price price;

    public void addCartDetail(CartDetail cartDetail) {
        cartDetail.setCart(this);
        cartDetailSet.add(cartDetail);
    }
}
