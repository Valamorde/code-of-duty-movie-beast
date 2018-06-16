package com.ticketmonster.ticketbeast.repositories;

import com.ticketmonster.ticketbeast.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
