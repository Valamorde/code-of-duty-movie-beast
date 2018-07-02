package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    User findByFullName(String fullName);
    List<User> findAllByRole(String role);
    User findOneByEmail(String email);
}
