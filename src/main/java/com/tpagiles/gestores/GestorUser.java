package com.tpagiles.gestores;

import com.tpagiles.dao.UserDAOImpl;
import com.tpagiles.models.*;
import com.tpagiles.models.dto.UserDto;
import com.tpagiles.models.dto.PersonFilter;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
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
        throw new Exception("Ya existe un usuario con ese tipo y numero de identificacion.");
    }

    public User findById(int id) throws Exception {
        User user = userDAO.findById(id);
        if(user==null)
            throw new Exception("The user with id " + id + " no longer exists.");
        return user;
    }

    public User updateUser(int id, UserDto userDto) throws Exception {
        User user = userDAO.findById(id);
        if(user==null)
            throw new Exception("The user with id " + id + " no longer exists.");

        List<User> users = userDAO.findByIdentification(userDto.getIdentification());
        List<User> existentUsers = users.stream().filter(u -> u.getId() != id &&
                        u.getType() == EnumTypeIdentification.valueOf(userDto.getType()))
                .collect(Collectors.toList());
        if(existentUsers.size() == 0){
            user.setName(userDto.getName());
            user.setSurname(userDto.getSurname());
            user.setEmail(userDto.getEmail());
            user.setType(EnumTypeIdentification.valueOf(userDto.getType()));
            user.setIdentification(userDto.getIdentification());
            user.setPassword(createPassword(userDto.getPassword()));
            return userDAO.createUser(user);
        }
        throw new Exception("Ya existe un usuario con ese tipo y numero de identificacion.");
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

    public List<User> findAllWithFilters(PersonFilter filters) {
        List<User> users = findAll();
        if(filters.getIdentification() != null){
            users = users.stream().filter(u -> u.getIdentification().equals(filters.getIdentification()))
                    .collect(Collectors.toList());
        }
        if(filters.getType() != null){
            users = users.stream().filter(u -> u.getType().name().equals(filters.getType()))
                    .collect(Collectors.toList());
        }
        if(filters.getName() != null){
            users = users.stream().filter(u -> u.getName().equals(filters.getName()))
                    .collect(Collectors.toList());
        }
        if(filters.getSurname() != null){
            users = users.stream().filter(u -> u.getSurname().equals(filters.getSurname()))
                    .collect(Collectors.toList());
        }
        return users;
    }

    private List<User> findByType(String type) {
        return userDAO.findByType(type);
    }

    private List<User> findByIdentification(String identification) {
        return userDAO.findByIdentification(identification);
    }
}
