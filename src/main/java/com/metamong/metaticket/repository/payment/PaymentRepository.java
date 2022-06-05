package com.metamong.metaticket.repository.payment;

import com.metamong.metaticket.domain.payment.Payment;
import com.metamong.metaticket.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByUser(User user);
}
