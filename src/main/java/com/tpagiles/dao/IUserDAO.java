package com.tpagiles.dao;

import com.tpagiles.models.User;

public interface IUserDAO {
    //List<User> findAllUsers();

    //void deleteUser(Long id);

    User createUser(User user);
    User findById(int id);

    //User findUserByEmail(User usuario);
}
