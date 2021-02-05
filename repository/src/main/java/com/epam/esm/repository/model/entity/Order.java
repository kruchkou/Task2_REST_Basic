package com.epam.esm.repository.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order")
@Data
@NoArgsConstructor
public class Order {

    @Id
    private Integer id;
    private Integer price;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_gift",
            joinColumns = @JoinColumn(name = "gift"),
            inverseJoinColumns = @JoinColumn(name = "order"))
    private List<GiftCertificate> giftList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user", nullable=false)
    private User user;

    private LocalDateTime date;
}
