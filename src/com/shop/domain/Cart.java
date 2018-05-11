package com.shop.domain;

import com.shop.Price;
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
        return cartDetailSet.stream().map(cd -> cd.getAmount().multiply(cd.getPrice().getGrossPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalNetPrice() {
        return cartDetailSet.stream().map(cd -> cd.getAmount().multiply(cd.getPrice().getNetPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
