package com.metamong.metaticket.repository.draw;

import com.metamong.metaticket.domain.draw.Draw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrawRepository extends JpaRepository<Draw, Long> {
}
