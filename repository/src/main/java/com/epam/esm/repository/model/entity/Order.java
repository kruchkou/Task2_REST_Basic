package com.epam.esm.repository.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order")
@Data
@NoArgsConstructor
public class Order {

    @Id
    Integer id;
    Integer price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gift", nullable=false)
    GiftCertificate gift;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user", nullable=false)
    User user;

    LocalDateTime date;
}
