package com.metamong.metaticket.repository.concert;


import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.concert.Genre;
import com.metamong.metaticket.domain.concert.Phamplet_File;
import com.metamong.metaticket.domain.concert.Ratings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConcertRepositoryTest {

    @Autowired
    ConcertRepository concertRepository;

    @Autowired
    FilesRepository filesRepository;

    @Test
    //@DisplayName("테스트")
    public void 공연등록(){
        Phamplet_File file = new Phamplet_File(1L,"웃는남자.jpg","/uploadImg/");
        filesRepository.save(file);
        Concert concert = Concert.builder()
                .title("웃는남자")
                .description("부자들의 낙원은 가난한 자들의 지옥으로 세워진 것이다.")
                .phamplet(file)
                .concertDate(LocalDateTime.now())
                .genre(Genre.MUSICAL_DRAMA)
                .ratings(Ratings.FIFTEEN)
                .address("세종문화회관 대극장")
                .host("(주)EMK뮤지컬컴퍼니")
                .seatNum(500)
                .drawStartDate(LocalDateTime.now())
                .drawEndDate(LocalDateTime.now())
                .price(150000)
                .visitCnt(5)
                .build();

        Concert test = concertRepository.save(concert); // 디비에 저장..
        System.out.println(test);
    }

    @Test
    @Transactional
    public void 공연목록(){
        List<Concert> concert = concertRepository.findAll();
        for(Concert c : concert) {
            System.out.println(c);
        }
//        assertThat(concert.get(0).getId()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void 공연수정(){
        // update 여서 reg_date 자동으로 생성 안됨
//        Concert concert = Concert.builder().id(3L)
//               .title("웃는남자")
//                .description("부자들의 낙원은 가난한 자들의 지옥으로 세워진 것이다.")
//                .phamplet("웃는남자.jpg")
//                .concertDate(LocalDateTime.now())
//                .genre(Genre.MUSICAL_DRAMA)
//                .ratings(Ratings.FIFTEEN)
//                .address("세종문화회관 대극장")
//                .host("(주)EMK뮤지컬컴퍼니")
//                .seat_num(500)
//                .drawStartDate(LocalDateTime.now())
//                .drawEndDate(LocalDateTime.now())
//                .price(150000)
//                .visit_cnt(130)
//                .build();
        Phamplet_File file = new Phamplet_File(1L,"웃는남자.jpg","/uploadImg/");
        Concert concert = Concert.builder()
                .title("웃는남자")
                .description("부자들의 낙원은 가난한 자들의 지옥으로 세워진 것이다.")
                .phamplet(file)
                .concertDate(LocalDateTime.now())
                .genre(Genre.MUSICAL_DRAMA)
                .ratings(Ratings.FIFTEEN)
                .address("세종문화회관 대극장")
                .host("(주)EMK뮤지컬컴퍼니")
                .seatNum(500)
                .drawStartDate(LocalDateTime.now())
                .drawEndDate(LocalDateTime.now())
                .price(150000)
                .visitCnt(130)
                .build();

        Concert test = concertRepository.save(concert); // 디비에 저장..
        Concert concert2 = concert;
        concert2.setSeatNum(120);
        Concert test2 = concertRepository.save(concert2);
        System.out.println(test2);

        assertThat(concert.getSeatNum()).isEqualTo(120);
    }

    @Test
    public void 공연삭제(){
        Concert findConcert = concertRepository.findAll().get(1);
        if(findConcert != null){
            concertRepository.delete(findConcert);
        }
        assertThat(concertRepository.findAll().size()).isEqualTo(1);
    }

}