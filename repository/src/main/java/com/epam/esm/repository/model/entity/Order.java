package com.epam.esm.repository.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order")
@Data
@NoArgsConstructor
@Audited
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user", nullable = false)
    private User user;


    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinTable(
            name = "order_gift",
            joinColumns = @JoinColumn(name = "order", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "gift", nullable = false))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<GiftCertificate> giftList;


}
