package hibernate.shop.product;

import hibernate.shop.Price;
import hibernate.shop.cart.CartDetail;
import hibernate.shop.order.OrderDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = {"orderDetailSet","cartDetailSet"})
public class Product implements Serializable{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private Long id;

    private String name;

    @Column ()
    private LocalDate date;

    @Enumerated
    private ProductType productType;

    @Embedded
    private Price price;

    @OneToMany(mappedBy = "product")
    // one product may be on many positions in order
    Set<OrderDetail> orderDetailSet;

    @OneToMany(mappedBy = "product")
    Set<CartDetail> cartDetailSet;

    public Product() {
    }

    public Product(String name, ProductType productType, Price price) {
        this.name = name;
        this.productType = productType;
        this.price = price;
        this.date = LocalDate.now();
    }
}
