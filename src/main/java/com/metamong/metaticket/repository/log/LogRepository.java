package com.metamong.metaticket.repository.log;

import com.metamong.metaticket.domain.log.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Long> {
    Optional<Log> findById(Long id);
}
