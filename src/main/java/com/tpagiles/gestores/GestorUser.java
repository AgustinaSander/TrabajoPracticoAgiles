package com.tpagiles.gestores;

import com.tpagiles.dao.UserDAOImpl;
import com.tpagiles.models.User;
import com.tpagiles.models.dto.UserDto;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class GestorUser {
    @Autowired
    UserDAOImpl userDAO;

    public User altaUsuario(UserDto userDto){
        User user = userDto.convertUserObject();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024,1, user.getPassword());
        user.setPassword(hash);

        userDAO.createUser(user);
        return user;
    }
}
