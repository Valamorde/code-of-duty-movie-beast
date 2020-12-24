package com.ticketmonster.moviebeast.repository;

import com.ticketmonster.moviebeast.model.Booking;
import com.ticketmonster.moviebeast.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByUser(User user);
}
