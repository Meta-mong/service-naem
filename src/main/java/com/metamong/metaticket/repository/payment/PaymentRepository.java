package com.metamong.metaticket.repository.payment;

import com.metamong.metaticket.domain.payment.Payment;
import com.metamong.metaticket.domain.payment.PaymentStatus;
import com.metamong.metaticket.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // 결제정보 저장(매개변수 payment: domain에서 메서드를 통해 생성)
    Payment save(Payment payment);

    // 유저 Id로 결제 상태 불러오기(마이페이지 결제내역 결제 상태 보여주기)
    Payment findByUserId(Long userId, Long concertId);
}
