package com.metamong.metaticket.repository.interest;

import com.metamong.metaticket.domain.interest.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, Long> {
    boolean existsByUserIdAndAndConcertId(Long userId, Long concertId);
    List<Interest> findByUserId(Long userId);
    Interest findByUserIdAndAndConcertId(Long userId, Long concertId);
}
