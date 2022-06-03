package com.metamong.metaticket.service.draw;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.concert.Genre;
import com.metamong.metaticket.domain.concert.Ratings;
import com.metamong.metaticket.domain.draw.Draw;
import com.metamong.metaticket.domain.user.User;
import com.metamong.metaticket.repository.concert.ConcertRepository;
import com.metamong.metaticket.repository.draw.DrawRepository;
import com.metamong.metaticket.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class DrawServiceTest {

    @Mock
    private DrawRepository drawRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ConcertRepository concertRepository;

    private DrawService drawService;

    Draw draw;
    User user;
    Concert concert;

    @BeforeEach
    void setUp() {
        drawService = new DrawServiceImpl(drawRepository,concertRepository,userRepository);

        user = User.builder().id(1L).email("metamong@naver.com").passwd("7852").name("person1").age(27)
                .number("01012345678").loserCnt(3).cancelCnt(3).build();

        concert = Concert.builder().id(1L).title("웃는남자").description("부자들의 낙원은 가난한 자들의 지옥으로 세워진 것이다.").phamplet("웃는남자.jpg")
                .concertDate(LocalDateTime.now()).genre(Genre.MUSICAL_DRAMA).ratings(Ratings.FIFTEEN).address("세종문화회관 대극장")
                .host("(주)EMK뮤지컬컴퍼니").seatNum(250).drawStartDate(LocalDateTime.now()).drawEndDate(LocalDateTime.now()).price(150000)
                .visitCnt(5).build();

        draw = Draw.builder().user(user).concert(concert).build();
    }

    @Test
    @DisplayName("응모하기")
    void applyDraw() {
        System.out.println(userRepository.getClass());
        //given
        given(userRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(user));
        given(concertRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(concert));
        given(drawRepository.save(any(Draw.class))).willReturn(draw);

        //when
        Draw findDraw = drawService.applyDraw(1L, 1L);

        //then
        assertThat(findDraw.getUser().getEmail()).isEqualTo("metamong@naver.com");
        assertThat(draw.getUser().getEmail()).isEqualTo("metamong@naver.com");
        assertThat(draw.getConcert().getTitle()).isEqualTo("웃는남자");

        verify(userRepository, times(1)).findById(any(Long.class));
        verify(concertRepository, times(1)).findById(any(Long.class));
        verify(drawRepository, times(1)).save(any(Draw.class));
    }

    @Test
    @DisplayName("유저로 조회")
    void findByUser() {
        //given
        List<Draw> draws = new ArrayList<>();
        draws.add(draw);
        given(userRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(user));
        given(drawRepository.findByUser(any(User.class))).willReturn(draws);

        //when
        List<Draw> findDraws = drawService.findByUserId(1L);

        //then
        assertThat(findDraws.get(0)).isEqualTo(draws.get(0));
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(drawRepository, times(1)).findByUser(any(User.class));
    }

    @Test
    @DisplayName("콘서트로 조회")
    void findByConcert() {
        //given
        List<Draw> draws = new ArrayList<>();
        draws.add(draw);
        given(concertRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(concert));
        given(drawRepository.findByConcert(any(Concert.class))).willReturn(draws);

        //when
        List<Draw> findDraws = drawService.findByConcertId(1L);

        //then
        assertThat(findDraws.get(0)).isEqualTo(draws.get(0));
        verify(concertRepository, times(1)).findById(any(Long.class));
        verify(drawRepository, times(1)).findByConcert(any(Concert.class));
    }


    @Test
    @DisplayName("응모 취소")
    void cancelDraw() {
        //given
        given(userRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(user));
        given(concertRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(concert));
        given(drawRepository.findByUserAndAndConcert(any(User.class), any(Concert.class))).willReturn(draw);

        //when
        drawService.cancelDraw(1L, 1L);

        //then
        verify(userRepository, times(1)).findById(any(Long.class));
        verify(concertRepository, times(1)).findById(any(Long.class));
        verify(drawRepository, times(1)).findByUserAndAndConcert(any(User.class), any(Concert.class));
    }
}
