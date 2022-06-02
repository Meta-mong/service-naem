package com.metamong.metaticket.domain.draw;

import com.metamong.metaticket.domain.BaseEntity;
import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Draws")
public class Draw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "draw_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private Concert concert;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "concert_id")
//    private Payment payment;

    private int ranking;

    private LocalDateTime emailSendDate;
}
