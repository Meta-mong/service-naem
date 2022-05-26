package com.metamong.metaticket.repository.draw;

import com.metamong.metaticket.domain.draw.Draw;
import com.metamong.metaticket.domain.user.User;
import com.metamong.metaticket.repository.user.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"spring.config.location=classpath:application-test.yml"})
class DrawRepositoryTest {

    @Autowired
    DrawRepository drawRepository;

    @Autowired
    UserRepository userRepository;

    Draw draw;
    User user;

    @BeforeEach
    void setUp() {
        user = User.builder().email("metamong@naver.com").passwd("7852").name("person1").age(27)
                .number("01012345678").loser_cnt(3).cancel_cnt(3).mod_date(LocalDateTime.now()).build();
        userRepository.save(user);
        draw = Draw.builder().user(user).build();
    }

    @AfterEach
    void clean() {
        drawRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("저장")
    void save() {
        Draw savedDraw = drawRepository.save(draw);
        assertNotNull(savedDraw);
    }

    @Test
    void find() {
        Draw savedDraw = drawRepository.save(draw);
        Optional<Draw> findDraw = drawRepository.findById(savedDraw.getId());

        findDraw.ifPresent(d -> {
            assertEquals(d.getId(), savedDraw.getId());
        });
    }

    @Test
    void delete() {
        Draw savedDraw = drawRepository.save(draw);
        drawRepository.delete(savedDraw);
        Optional<Draw> findDraw = drawRepository.findById(savedDraw.getId());

        if (findDraw.isPresent())
            fail();
    }
}