package hibernate.shop;

import hibernate.shop.domain.User;
import hibernate.shop.domain.OrderComplaint;
import hibernate.shop.domain.OrderDetail;
import hibernate.shop.domain.OrderHistory;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orderDetailSet", "orderComplaintSet", "orderHistorySet"})
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    BigDecimal totalNet;
    BigDecimal totalGross;

    @ManyToOne
    @JoinColumn
    User user;

    // one order has many positions owner of relation will be order detail
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<OrderDetail> orderDetailSet;

    // właścicielem relacji jest druga storna czyli order history
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<OrderHistory> orderHistorySet;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            // joinColumns nazwa kolumny w tabeli dodatkowej z kluczem do tabeli laczonej
            // + nazwa pola w encji z kluczem po ktorym laczymy
            joinColumns = @JoinColumn(name = "order_comlaint_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    Set<OrderComplaint> orderComplaintSet;

    public Order(BigDecimal totalGross, User user) {
        this.totalGross = totalGross;
        this.user = user;
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        orderDetail.setOrder(this);
        orderDetailSet.add(orderDetail);
    }

    public void addOrderHistory(OrderHistory orderHistory) {
        orderHistory.setOrder(this);
        this.orderHistorySet.add(orderHistory);
    }

    public OrderHistory getCurrentOrderHistory() {
        return this.getOrderHistorySet().stream().sorted(Comparator.comparing(OrderHistory::getId)).findFirst()
                .orElse(new OrderHistory());
    }
}
