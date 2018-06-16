package com.ticketmonster.ticketbeast.repositories;

import com.ticketmonster.ticketbeast.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
