package com.metamong.metaticket.repository.draw;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.draw.Draw;
import com.metamong.metaticket.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DrawRepository extends JpaRepository<Draw, Long> {
    List<Draw> findByConcert(Concert concert);
    List<Draw> findByUser(User user);
    Draw findByUserAndAndConcert(User user, Concert concert);

    @Query("select max(d.ranking) from Draw d where d.concert.id=:concertId and d.emailSendDate is not null")
    int findLowRankingGroupByConcert(@Param("concertId") Long concertId);
}
