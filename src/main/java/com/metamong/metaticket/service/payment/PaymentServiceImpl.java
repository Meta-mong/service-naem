package com.metamong.metaticket.service.payment;

import com.metamong.metaticket.domain.draw.Draw;
import com.metamong.metaticket.domain.draw.DrawState;
import com.metamong.metaticket.repository.draw.DrawRepository;
import com.metamong.metaticket.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final DrawRepository drawRepository;
    private final JavaMailSender javaMailSender;

    @Override
    @Transactional
    public void sendPaymentEmail(Draw draw) {
        SimpleMailMessage message = getSimpleMailMessage(draw);
        javaMailSender.send(message);
        draw.setState(DrawState.WIN);
        draw.setEmailSendDate(LocalDateTime.now());
    }

    private SimpleMailMessage getSimpleMailMessage(Draw draw) {
        String paymentURL = "http://localhost/payment?concert=" + draw.getConcert().getId();
        String text = "이메일 수신일 기준 3일 안에 결제하지 않으면 당첨이 취소됩니다.\n" +
                "결제 페이지: " + paymentURL;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("metaticket3@gmail.com");
        message.setTo(draw.getUser().getEmail());
        message.setSubject("[메타티켓] 콘서트 티켓 당첨 안내");
        message.setText(text);
        return message;
    }
}
