package com.epam.esm.repository.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "gift_certificate")
@Data
@NoArgsConstructor
@DynamicUpdate
@Audited
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    private Integer duration;
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;
    @Column(name = "last_update_date", nullable = false)
    private LocalDateTime lastUpdateDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinTable(
            name = "gift_tag",
            joinColumns = {@JoinColumn(name = "gift", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "tag", nullable = false)})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Tag> tagList;

}
