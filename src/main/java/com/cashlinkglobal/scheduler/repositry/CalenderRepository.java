package com.cashlinkglobal.scheduler.repositry;

import com.cashlinkglobal.scheduler.entity.CalenderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CalenderRepository extends JpaRepository<CalenderDetails, Long> {
    Optional<CalenderDetails> findByDate(LocalDate date);

}
