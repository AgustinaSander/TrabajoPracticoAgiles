package com.tpagiles.repositories;

import java.util.List;
import java.util.Optional;

import com.tpagiles.models.Person;
import com.tpagiles.models.dto.UserDto;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tpagiles.models.User;
import org.springframework.stereotype.Service;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User u, Person p WHERE p.identification = :identification AND p.id = u.id")
    List<User> findByIdentification(String identification);

    /*
    @Query("SELECT p FROM Person p WHERE p.type = :type AND p.identification = :identification")
    List<Person> findByTypeAndIdentification(@Param("type") String type, @Param("identification") String identification);
    */
}

