package com.metamong.metaticket.service.payment;

import com.metamong.metaticket.domain.draw.Draw;
import com.metamong.metaticket.domain.user.User;

public interface PaymentService {

    void sendPaymentEmail(Draw draw);
}
