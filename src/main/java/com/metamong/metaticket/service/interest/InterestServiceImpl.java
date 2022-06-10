package com.metamong.metaticket.service.interest;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.interest.Interest;
import com.metamong.metaticket.domain.user.User;
import com.metamong.metaticket.repository.concert.ConcertRepository;
import com.metamong.metaticket.repository.interest.InterestRepository;
import com.metamong.metaticket.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class InterestServiceImpl implements InterestService{

    private final InterestRepository interestRepository;
    private final UserRepository userRepository;
    private final ConcertRepository concertRepository;

    @Override
    @Transactional
    public Interest saveInterest(Long userId, Long concertId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException());
        Concert findConcert = concertRepository.findById(concertId).orElseThrow(() -> new NoSuchElementException());

        Interest interest = Interest.builder().user(findUser).concert(findConcert).build();

        return interestRepository.save(interest);
    }

    @Override
    public boolean isInterested(Long userId, Long concertId) {
        return interestRepository.existsByUserIdAndAndConcertId(userId, concertId);
    }

    @Override
    public List<Concert> findUserInterestedConcertList(Long userId) {
        List<Interest> interestList = interestRepository.findByUserId(userId);

        List<Concert> interestedConcertList = new ArrayList<>();
        interestList.forEach(
                interest -> interestedConcertList.add(
                        concertRepository.findById(interest.getConcert().getId()).orElseThrow(() -> new NoSuchElementException())
                )
        );

        return interestedConcertList;
    }

    @Override
    @Transactional
    public void deleteInterest(Long userId, Long concertId) {
        Interest findInterest = interestRepository.findByUserIdAndAndConcertId(userId, concertId);
        interestRepository.delete(findInterest);
    }
}
