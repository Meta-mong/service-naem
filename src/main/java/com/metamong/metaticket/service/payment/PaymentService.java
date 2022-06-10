package com.metamong.metaticket.service.payment;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.draw.Draw;
import com.metamong.metaticket.domain.payment.Payment;
import com.metamong.metaticket.domain.user.User;

public interface PaymentService {

    void sendPaymentEmail(Draw draw);

    void paymentSuccess(Payment payment, User user, Concert concert);
}
