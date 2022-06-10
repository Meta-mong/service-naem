package com.metamong.metaticket.repository.concert;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.concert.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableDefault;

import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert,Long> {
    Page<Concert> findByGenre(@PageableDefault(size = 16) Pageable pageable,Genre genre);
}
