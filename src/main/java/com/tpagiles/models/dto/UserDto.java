package com.tpagiles.models.dto;

import com.tpagiles.models.EnumTypeIdentification;
import com.tpagiles.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private int id;
	private String name;
    private String surname;
    private String email;  
    private String type;
	private String identification;
    private String password;

	public UserDto(String name, String surname, String email, String type, String identification, String password){
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.type = type;
		this.identification = identification;
		this.password = password;
	}

	public User convertUserObject() {
		EnumTypeIdentification typeEnum = EnumTypeIdentification.valueOf(type);
		return new User(0, name, surname, email, typeEnum, identification, password, null);
	}
}
