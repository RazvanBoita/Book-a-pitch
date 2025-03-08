package com.book_a_pitch.bapAPI.repositories;

import com.book_a_pitch.bapAPI.domain.Pitch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PitchRepository extends JpaRepository<Pitch, Long> {
}
