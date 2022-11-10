package com.tpagiles.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.tpagiles.models.User;
import com.tpagiles.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserDAOImpl implements IUserDAO{

	@Autowired
    UserRepository userRepository;

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findById(int id){
		Optional<User> user = userRepository.findById(id);
		return user.isPresent() ? user.get() : null;
	}

	public List<User> findByIdentification(String identification) {
		return userRepository.findByIdentification(identification);
	}

	@Override
	public List<User> findAllUsers() {
		List<User> users = (List<User>) userRepository.findAll();
		return users;
	}

	public void deleteUser(int id) throws Exception {
		try{
			userRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e){
			throw new Exception("Unexistent user with id "+id);
		}

	}
/*
	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
*/
}
