package com.book_a_pitch.bapAPI.repositories;

import com.book_a_pitch.bapAPI.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    @Query("SELECT b FROM Booking b WHERE b.pitch.pitchId = :pitchId AND " +
            "((b.startTime < :endTime AND b.endTime > :startTime))")
    List<Booking> findByPitchAndTimeOverlap(
            @Param("pitchId") Long pitchId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}
