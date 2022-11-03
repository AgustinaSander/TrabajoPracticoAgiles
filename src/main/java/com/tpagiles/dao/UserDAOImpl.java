package com.tpagiles.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpagiles.models.User;
import com.tpagiles.repositories.UserRepository;

@Service
public class UserDAOImpl implements IUserDAO{

	@Autowired
    UserRepository userRepository;

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	/*
    @Override
	public User findUserByEmail(User user) {
        List<User> users = userRepository.findUserByEmail(user.getEmail());
		System.out.println(users);
        if(users.isEmpty()) {
            return null;
        }

        String passwordHashed = users.get(0).getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        return argon2.verify(passwordHashed, user.getPassword()) ? users.get(0) : null;
    }

	@Override
	public List<User> findAllUsers() {
		List<User> users = (List<User>) userRepository.findAll();
		return users;
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
*/
}
