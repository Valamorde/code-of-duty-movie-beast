package com.ticketmonster.movie_beast.repositories;

import com.ticketmonster.movie_beast.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Integer> {

    @Query("SELECT b FROM Booking b WHERE b.user_id = :user_id")
    List<Booking> findByUserId(@Param("user_id") Integer user_id);

    @Query("SELECT b FROM Booking b WHERE b.show_id = :show_id")
    List<Booking> findByShowId(@Param("show_id") Integer show_id);
}
