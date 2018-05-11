package com.shop.domain;

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

    //owner of relation is order detail
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<OrderDetail> orderDetailSet;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<OrderHistory> orderHistorySet;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            // joinColumn(order_complaint_id - column name in new table, id - key to joining table)
            // + column name in entity with joining key
            joinColumns = @JoinColumn(name = "order_complaint_id", referencedColumnName = "id"),
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
        return this.getOrderHistorySet().stream().min(Comparator.comparing(OrderHistory::getId))
                .orElse(new OrderHistory());
    }
}
