package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.Booking;
import com.ticketmonster.movie_beast.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findAllByUser(User user);
}
