package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findAllByUserId(Integer userId);
    List<Booking> findAllByShowId(Integer showId);
    Booking findByBookingId(Integer bookingId);
    Booking findByBookingIdAndUserId(Integer bookingId, Integer userId);
}
