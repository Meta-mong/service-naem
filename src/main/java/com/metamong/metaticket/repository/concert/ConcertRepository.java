package com.metamong.metaticket.repository.concert;

import com.metamong.metaticket.domain.concert.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert,Long> {
}
