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
    private Integer id;
    private Integer price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gift", nullable=false)
    private GiftCertificate gift;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user", nullable=false)
    private User user;

    private LocalDateTime date;
}
