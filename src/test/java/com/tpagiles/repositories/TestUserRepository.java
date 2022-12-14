package com.tpagiles.repositories;

import com.tpagiles.gestores.GestorUser;
import com.tpagiles.models.EnumTypeIdentification;
import com.tpagiles.models.User;
import com.tpagiles.models.dto.UserDto;
import com.tpagiles.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.CoreMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestUserRepository {
    @Autowired
    UserRepository userRepository;

    @Autowired
    GestorUser gestorUser;

    List<User> users;

    @Before
    public void setup() throws Exception {
        users = gestorUser.findAll();
    }

    @Test
    @DisplayName("Should return the user by identification")
    public void testFindByIdentification(){
        String identification = users.get(0).getIdentification();
        List<User> expectedUsers = users.stream().filter(u -> u.getIdentification().equals(identification))
                .collect(Collectors.toList());
        List<User> actualUsers = userRepository.findByIdentification(identification);
        assertEquals(actualUsers, expectedUsers);
    }
    /* @Query("SELECT u FROM User u, Person p WHERE p.identification = :identification AND p.id = u.id")
    List<User> findByIdentification(String identification); */

}
