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

//    public Price getPrice() {
//        price = new Price();
//        price.setNetPrice(new BigDecimal(0));
//        price.setGrossPrice(new BigDecimal(0));
//
//        for (CartDetail cd : cartDetailSet) {
//            BigDecimal netPrice = cd.getPrice().getNetPrice().multiply(cd.getAmount());
//            BigDecimal grossPrice = cd.getPrice().getGrossPrice().multiply(cd.getAmount());
//            price.getNetPrice().add(netPrice);
//            price.getGrossPrice().add(grossPrice);
//        }
//        return price;
//    }
}
