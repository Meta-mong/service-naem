package com.metamong.metaticket.service.payment;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.draw.Draw;
import com.metamong.metaticket.domain.payment.Payment;
import com.metamong.metaticket.domain.user.User;
import com.metamong.metaticket.domain.user.dto.UserDTO;


public interface PaymentService {

    Payment savePayment(User user, Concert concert);

    void sendPaymentEmail(Draw draw);

    // 결제 성공 했을때(결제 성공 페이지가 정상적으로 나왔을때)
    void paymentSuccess(User user, Concert concert);

    // 결제 실패 했을때(결제 실패 페이지가 나왔을때)
    void paymentFail(User user, Concert concert);

    // 세션 정보로 User Id 가져오기(카카오 페이 결제 시 필요함)
    Long getUserId();

    User getUser();

    Payment createPayment(Long concertId);
}
