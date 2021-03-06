package com.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Double rating;

    String description;

    LocalDateTime createDate;

    @ManyToOne
    User user;

    @ManyToOne
    Product product;

    boolean isVisible;
}
