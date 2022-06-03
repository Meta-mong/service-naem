package com.metamong.metaticket.repository.draw;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.concert.Genre;
import com.metamong.metaticket.domain.concert.Ratings;
import com.metamong.metaticket.domain.draw.Draw;
import com.metamong.metaticket.domain.user.User;
import com.metamong.metaticket.repository.concert.ConcertRepository;
import com.metamong.metaticket.repository.user.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {"spring.config.location=classpath:application-test.yml"})
class DrawRepositoryTest {

    @Autowired
    DrawRepository drawRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ConcertRepository concertRepository;

    Draw draw;
    User user;
    Concert concert;

    @BeforeEach
    void setUp() {
        user = User.builder().email("metamong@naver.com").passwd("7852").name("person1").age(27)
                .number("01012345678").loserCnt(3).cancelCnt(3).build();

        concert = Concert.builder().title("웃는남자").description("부자들의 낙원은 가난한 자들의 지옥으로 세워진 것이다.").phamplet("웃는남자.jpg")
                .concertDate(LocalDateTime.now()).genre(Genre.MUSICAL_DRAMA).ratings(Ratings.FIFTEEN).address("세종문화회관 대극장")
                .host("(주)EMK뮤지컬컴퍼니").seatNum(250).drawStartDate(LocalDateTime.now()).drawEndDate(LocalDateTime.now()).price(150000)
                .visitCnt(5).build();

        userRepository.save(user);
        concertRepository.save(concert);
        draw = Draw.builder().user(user).concert(concert).build();
    }

    @AfterEach
    void clean() {
        drawRepository.deleteAll();
        userRepository.deleteAll();
        concertRepository.deleteAll();
    }

    @Test
    @DisplayName("조회")
    void find() {
        Draw savedDraw = drawRepository.save(draw);
        Optional<Draw> findDraw = drawRepository.findById(savedDraw.getId());

        findDraw.ifPresent(d -> {
            assertEquals(d.getId(), savedDraw.getId());
        });
    }

    @Test
    @DisplayName("삭제")
    void delete() {
        Draw savedDraw = drawRepository.save(draw);
        drawRepository.delete(savedDraw);
        Optional<Draw> findDraw = drawRepository.findById(savedDraw.getId());

        if (findDraw.isPresent())
            fail();

    }

    @Test
    @DisplayName("저장")
    void save() {
        Draw savedDraw = drawRepository.save(draw);
        assertNotNull(savedDraw);
    }

    @Test
    @DisplayName("콘서트로 가져오기")
    void findByConcert() {
        drawRepository.save(draw);
        List<Draw> findDraws = drawRepository.findByConcert(concert);
        assertNotNull(findDraws.get(0));
    }

    @Test
    @DisplayName("유저로 가져오기")
    void findByUser() {
        drawRepository.save(draw);
        List<Draw> findDraws = drawRepository.findByUser(user);
        assertNotNull(findDraws.get(0));

    }

}
