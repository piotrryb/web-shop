package com.shop.domain;

import com.shop.Price;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class OrderDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Embedded
    Price price;
    BigDecimal amount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    Order order;
}
