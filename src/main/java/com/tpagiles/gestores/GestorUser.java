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
import java.util.stream.Collectors;

@Service
public class GestorUser {
    @Autowired
    UserDAOImpl userDAO;

    public User createUser(UserDto userDto) throws Exception {
        String identification = userDto.getIdentification();
        List<User> users = userDAO.findByIdentification(identification);
        List<User> existentUsers = users.stream().filter(u -> u.getType() == EnumTypeIdentification.valueOf(userDto.getType()))
                .collect(Collectors.toList());
        if(existentUsers.size() == 0){
            User user = userDto.convertUserObject();
            String hash = createPassword(userDto.getPassword());
            user.setPassword(hash);
            return userDAO.createUser(user);
        }
        throw new Exception("User with type and identification already exists.");
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


    public User findByTypeAndIdentification(String type, String identification, String password) throws Exception {
        List<User> users = userDAO.findByIdentification(identification);
        List<User> existentUsers = users.stream().filter(u -> u.getType() == EnumTypeIdentification.valueOf(type))
                .collect(Collectors.toList());
        if(existentUsers.size()==0)
            throw new Exception("The user with type " + type + " and identification "+identification+" no longer exists.");

        String passwordHashed = existentUsers.get(0).getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        return argon2.verify(passwordHashed, password) ? existentUsers.get(0) : null;
    }

    public List<User> findAll(){
        return userDAO.findAllUsers();
    }

    public void deleteUser(int id) throws Exception {
        userDAO.deleteUser(id);
    }
}
