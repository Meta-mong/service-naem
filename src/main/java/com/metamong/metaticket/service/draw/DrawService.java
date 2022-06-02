package com.metamong.metaticket.service.draw;

import com.metamong.metaticket.domain.draw.Draw;

import java.util.List;

public interface DrawService {

    Draw applyDraw(Long userId, Long concertId);
    List<Draw> findByUserId(Long userId);
    List<Draw> findByConcertId(Long concertId);
    void cancelDraw(Long userId, Long concertId);
}
