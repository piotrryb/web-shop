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

    public BigDecimal getTotalGrossPrice() {
//        double sum = cartDetailSet.stream().mapToDouble(cd -> cd.getAmount()
//                .multiply(cd.getPrice().getGrossPrice()).doubleValue()).sum();
        BigDecimal totalGross = cartDetailSet.stream().map(cd -> cd.getAmount().multiply(cd.getPrice().getGrossPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalGross;

    }

    public BigDecimal getTotalNetPrice() {
        BigDecimal totalNet = cartDetailSet.stream().map(cd -> cd.getAmount().multiply(cd.getPrice().getNetPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalNet;
    }
}
