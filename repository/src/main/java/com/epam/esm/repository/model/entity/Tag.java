package com.epam.esm.repository.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tag")
@Data
@NoArgsConstructor
@NamedNativeQuery(
        name = "getTagListByGiftID",
        query = "SELECT * From tag tags join gift_tag link on tags.id = link.tag " +
                "WHERE link.gift = :giftID",
        resultClass = Tag.class)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
