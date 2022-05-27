package com.metamong.metaticket.repository.log;

import com.metamong.metaticket.domain.log.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LogRepositoryTest {

    @Autowired
    LogRepository logRepository;

    @Test
    public void insertLogTest(){
        Log log = Log.builder().
                visitDate(LocalDateTime.now()).
                build();
        Log temp = logRepository.save(log);
        System.out.println(temp.toString());
    }

    @Test
    public void selectLogTest(){
        Log log = Log.builder().
                visitDate(LocalDateTime.now()).
                build();
        Log temp = logRepository.save(log);

        Log selectLog = logRepository.findById(1L).get();
        System.out.println(selectLog.toString());
    }

    @Test
    public void updateLogTest(){
        Log origin = logRepository.findById(1L).get();
        origin.setVisitDate(LocalDateTime.now());

        Log temp = logRepository.save(origin);
        System.out.println(temp.toString());
    }

    @Test
    public void deleteLogTest(){
        Log origin = logRepository.findById(2L).get();

        logRepository.delete(origin);
    }

}