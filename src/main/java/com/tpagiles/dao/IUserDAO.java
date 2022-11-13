package com.tpagiles.dao;

import com.tpagiles.models.User;
import com.tpagiles.models.dto.UserDto;

import java.util.List;

public interface IUserDAO {
    User createUser(User user);
    User findById(int id);
    List<User> findAllUsers();

    void deleteUser(int id) throws Exception;

    List<User> findByType(String type);
}
