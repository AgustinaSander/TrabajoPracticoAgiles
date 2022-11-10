package com.tpagiles.gestores;

import com.tpagiles.dao.UserDAOImpl;
import com.tpagiles.models.*;
import com.tpagiles.models.dto.AddressDto;
import com.tpagiles.models.dto.LicenseHolderDto;
import com.tpagiles.models.dto.UserDto;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestorUser {
    @Autowired
    UserDAOImpl userDAO;

    public User createUser(UserDto userDto){
        User user = userDto.convertUserObject();
        String hash = createPassword(userDto.getPassword());
        user.setPassword(hash);
        return userDAO.createUser(user);
    }

    public User findById(int id){
        return userDAO.findById(id);
    }

    public User updateUser(int id, UserDto userDto) throws Exception {
        User user = userDAO.findById(id);
        if(user==null)
            throw new Exception("The user with id " + id + " no longer exists.");

        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setType(EnumTypeIdentification.valueOf(userDto.getType()));
        user.setIdentification(userDto.getIdentification());
        user.setPassword(createPassword(userDto.getPassword()));
        return userDAO.createUser(user);
    }

    private String createPassword(String password){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        return argon2.hash(1,1024,1, password);
    }

}
