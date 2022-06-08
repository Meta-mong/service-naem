package com.metamong.metaticket.service.draw;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.draw.Draw;
import com.metamong.metaticket.domain.draw.DrawState;
import com.metamong.metaticket.domain.user.User;
import com.metamong.metaticket.repository.concert.ConcertRepository;
import com.metamong.metaticket.repository.draw.DrawRepository;
import com.metamong.metaticket.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DrawServiceImpl implements DrawService {

    private final DrawRepository drawRepository;
    private final ConcertRepository concertRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Draw applyDraw(Long userId, Long concertId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException());
        Concert concert = concertRepository.findById(concertId).orElseThrow(() -> new NoSuchElementException());
        Draw draw = Draw.builder().user(user).concert(concert).build();
        return drawRepository.save(draw);
    }

    @Override
    public List<Draw> findByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException());
        return drawRepository.findByUser(user);
    }

    @Override
    public List<Draw> findByConcertId(Long concertId) {
        Concert concert = concertRepository.findById(concertId).orElseThrow(() -> new NoSuchElementException());
        return drawRepository.findByConcert(concert);
    }

    @Override
    @Transactional
    public void cancelDraw(Long drawId) {
        Draw findDraw = drawRepository.findById(drawId).orElseThrow(() -> new NoSuchElementException());

        if (findDraw.getState() == DrawState.WIN) {
            User findUser = userRepository.findById(findDraw.getUser().getId()).orElseThrow(() -> new NoSuchElementException());
            findUser.setCancelCnt(findUser.getCancelCnt() + 1);
        }

        findDraw.setState(DrawState.CANCEL);
    }
}
