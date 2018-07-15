package com.ticketmonster.moviebeast.repositories;

import com.ticketmonster.moviebeast.models.Booking;
import com.ticketmonster.moviebeast.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findAllByUser(User user);
}
