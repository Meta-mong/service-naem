package com.metamong.metaticket.service.draw;

import com.metamong.metaticket.domain.draw.Draw;
import com.metamong.metaticket.domain.draw.dto.DrawDTO;

import java.util.List;

public interface DrawService {

    Draw applyDraw(Long userId, Long concertId);
    List<DrawDTO.HISTORY> findByUserId(Long userId);
    List<Draw> findByConcertId(Long concertId);
    void cancelDraw(Long drawId);
}
