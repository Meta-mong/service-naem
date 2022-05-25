package com.metaticket.metaticket.repository.draw;

import com.metaticket.metaticket.domain.draw.Draw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrawRepository extends JpaRepository<Draw, Long> {
}
