package com.metamong.metaticket.domain.concert;

import com.metamong.metaticket.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

// 엔티티
@Data
@Entity
@Table(name = "Concerts")
@Builder
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모두 들어간 생성자
public class Concert extends BaseEntity { //BaseEntity -> 공통으로 사용하는  Entity

    @Id // 기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_id")
    private Long id;

    private String title;

    private String description;

    private String phamplet;

    @Column(name = "concert_date")
    private LocalDateTime c_date;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    private Ratings ratings;

    private String address;

    private String host;

    private int seat_num;

    @Column(name = "draw_start_date")
    private LocalDateTime s_date;

    @Column(name = "draw_end_date")
    private LocalDateTime e_date;

    private int price;

    private int visit_cnt;

//    public void update(바꿀 타입, 변수명){
//        this.price = 변수명
//    }

}