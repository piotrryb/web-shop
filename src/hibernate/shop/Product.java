package hibernate.shop;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
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

    public Product() {
    }

    public Product(String name, ProductType productType, Price price) {
        this.name = name;
        this.productType = productType;
        this.price = price;
        this.date = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}
