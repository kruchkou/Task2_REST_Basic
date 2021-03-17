package com.epam.esm.repository.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@Audited
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    public static class RoleNames {

        public static String USER = "USER";
        public static String ADMIN = "ADMIN";

    }

}
