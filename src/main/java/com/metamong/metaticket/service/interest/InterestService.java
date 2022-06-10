package com.metamong.metaticket.service.interest;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.interest.Interest;

import java.util.List;

public interface InterestService {

    Interest saveInterest(Long userId, Long concertId);
    boolean isInterested(Long userId, Long concertId);
    List<Concert> findUserInterestedConcertList(Long userId);
    void deleteInterest(Long userId, Long concertId);
}
