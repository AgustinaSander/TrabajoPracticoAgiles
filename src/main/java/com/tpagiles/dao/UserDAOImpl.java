package com.tpagiles.dao;

import com.tpagiles.models.Person;
import com.tpagiles.models.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
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

	/*
	@Override
	public User findByTypeAndIdentification(String type, String identification) {
		List<Person> persons = userRepository.findByTypeAndIdentification(type, identification);
		User user = new User();
		if(persons.size() > 0){
			Person person = persons.get(0);
			user.setId(person.getId());
			user.setName(person.getName());
			user.setType(person.getType());
			user.setIdentification(person.getIdentification());
			user.setSurname(person.getSurname());
			user.setEmail(person.getEmail());

			Optional<User> user2 = userRepository.findById(person.getId());
			return user2.get();
		}

		return null;
	}*/

	/*
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
