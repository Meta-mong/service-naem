package com.metamong.metaticket.domain.payment;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private Concert concert;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private int amount;

    //==생성 메서드==//
    public static Payment createPayment(User user, Concert concert){
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setConcert(concert);
        payment.setPaymentStatus(PaymentStatus.IN_PROGRESS);
        payment.setAmount(concert.getPrice());

        return payment;
    }

}
