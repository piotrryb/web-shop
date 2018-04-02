package hibernate.shop.order;

import hibernate.shop.complaint.OrderComplaint;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orderDetailSet", "orderComplaintSet"})
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    BigDecimal totalNet;
    BigDecimal totalGross;
    String userEmail;

    // one order has many positions owner of relation will be order detail
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<OrderDetail> orderDetailSet;

    // właścicielem relacji jest druga storna czyli order history
    @OneToOne(mappedBy = "order")
    OrderHistory orderHistory;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            // joinColumns nazwa kolumny w tabeli dodatkowej z kluczem do tabeli laczonej
            // + nazwa pola w encji z kluczem po ktorym laczymy
            joinColumns = @JoinColumn(name = "order_comlaint_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    Set<OrderComplaint> orderComplaintSet;

    public Order(BigDecimal totalGross, String userEmail) {
        this.totalGross = totalGross;
        this.userEmail = userEmail;
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetail.setOrder(this);
        orderDetailSet.add(orderDetail);
    }
}
