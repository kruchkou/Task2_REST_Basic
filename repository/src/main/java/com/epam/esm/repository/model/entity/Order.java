package com.epam.esm.repository.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="\"order\"")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user", nullable=false)
    private User user;

    private Integer price;

    private LocalDateTime date;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_gift",
            joinColumns = @JoinColumn(name = "order"),
            inverseJoinColumns = @JoinColumn(name = "gift"))
    private List<GiftCertificate> giftList;


}
