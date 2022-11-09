package com.tpagiles.models;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "person")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String email;
    @Enumerated(EnumType.STRING)
    private EnumTypeIdentification type;
    private String identification;

    public Person(String name, String surname, String email, EnumTypeIdentification type, String identification){
        this.name= name;
        this.surname = surname;
        this.email = email;
        this.type = type;
        this.identification = identification;
    }
}
