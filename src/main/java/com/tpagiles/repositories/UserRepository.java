package com.tpagiles.repositories;

import java.util.List;

import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tpagiles.models.User;
import org.springframework.stereotype.Service;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
