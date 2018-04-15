package hibernate.shop.order;

import hibernate.shop.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime statusDate;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn
    Order order;
}
