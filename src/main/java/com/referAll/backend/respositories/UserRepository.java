package com.referAll.backend.respositories;

import com.referAll.backend.entities.models.ReferPost;
import com.referAll.backend.entities.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByEmailId(String email);
}
