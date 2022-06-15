package com.metamong.metaticket.domain.log;

import com.metamong.metaticket.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime visitDate;
    //유저와 관계 매핑 필요(user_id)

    public static Log createLog(Long id){
        Log log = Log.builder().visitDate(LocalDateTime.now()).build();
        return log;
    }

    @ManyToOne(fetch = FetchType.LAZY) //지연로딩
    @JoinColumn(name="user_id")
    private User user;
}
