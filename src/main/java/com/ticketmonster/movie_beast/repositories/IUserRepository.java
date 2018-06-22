package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.full_name = :full_name")
    User findByFullName(@Param("full_name") String full_name);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findByRole(@Param("role") String role);
}
