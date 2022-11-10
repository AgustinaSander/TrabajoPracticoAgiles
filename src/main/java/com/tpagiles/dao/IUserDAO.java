package com.tpagiles.dao;

import com.tpagiles.models.User;
import com.tpagiles.models.dto.UserDto;

public interface IUserDAO {
    User createUser(User user);
    User findById(int id);
    //User findByTypeAndIdentification(String type, String identification);
}
