package com.tpagiles.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="email")
    private String email;

    @Column(name="type_id")
    @Enumerated(EnumType.STRING)
    private EnumTypeIdentification type;

    @Column(name="identification")
    private String identification;

    @Column(name="password")
    private String password;

    @OneToMany
    @Transient
    private List<Licence> listLicenses;

    public User(String name, String surname, String email, EnumTypeIdentification type, String identification){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.type = type;
        this.identification = identification;
        this.password = "";
        this.listLicenses = new ArrayList<>();
    }
}
