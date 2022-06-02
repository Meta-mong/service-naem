package com.metamong.metaticket.repository.draw;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.draw.Draw;
import com.metamong.metaticket.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrawRepository extends JpaRepository<Draw, Long> {
    List<Draw> findByConcert(Concert concert);
    List<Draw> findByUser(User user);
    Draw findByUserAndAndConcert(User user, Concert concert);
}
