package com.tpagiles.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User extends Person{
    private String password;

    public User(int id, String name, String surname, String email, EnumTypeIdentification type, String identification, String password){
        super(id, name, surname, email, type, identification);
        this.password = password;
    }

    public User(String name, String surname, String email, EnumTypeIdentification type, String identification, String password){
        super(name, surname, email, type, identification);
        this.password = password;
    }
}
