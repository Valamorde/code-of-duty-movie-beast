package com.ticketmonster.deprecated_logic_for_reference.repositories;

import com.ticketmonster.deprecated_logic_for_reference.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}
