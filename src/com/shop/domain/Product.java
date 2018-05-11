package com.shop.domain;

import com.shop.Price;
import com.shop.ProductType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"orderDetailSet", "cartDetailSet", "productRatingSet"})
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column()
    private LocalDate date;

    @Enumerated
    private ProductType productType;

    @Embedded
    private Price price;

    private String description;

    @Lob
    private byte[] image;

    @OneToMany(mappedBy = "product")
    Set<OrderDetail> orderDetailSet;

    @OneToMany(mappedBy = "product")
    Set<CartDetail> cartDetailSet;

    @OneToMany(mappedBy = "product")
    Set<ProductRating> productRatingSet;

    public Product() {
    }

    public Product(String name, ProductType productType, Price price) {
        this.name = name;
        this.productType = productType;
        this.price = price;
        this.date = LocalDate.now();
    }
}
