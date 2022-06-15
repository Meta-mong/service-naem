package com.metamong.metaticket.service.batch.email;

import com.metamong.metaticket.domain.draw.Draw;
import com.metamong.metaticket.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SendEmailNextUserRPW {

    private final PaymentService paymentService;
    private final EntityManagerFactory entityManagerFactory;
    private final ShareData shareData;

    @Bean
    @StepScope
    public JpaCursorItemReader<Draw> sendEmailNextUsersReader(@Value("#{jobParameters[concertId]}") Long concertId) {
        String findNextUsers = "select d from Draw d " +
                "join d.concert c " +
                "where c.id=:concertId and d.state='QUEUE' " +
                "order by d.ranking";

        Map<String, Object> params = new HashMap<>();
        params.put("concertId", concertId);

        JpaCursorItemReader<Draw> reader = new JpaCursorItemReaderBuilder<Draw>()
                .name("sendEmailNextUsersReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString(findNextUsers)
                .parameterValues(params)
                .build();

        log.info("sendEmailNextUsersReader");
        System.out.println(shareData.remainSeatCnt);
        reader.setMaxItemCount(shareData.remainSeatCnt);

        return reader;
    }

    @Bean
    public ItemProcessor<Draw, Draw> sendEmailNextUsersProcessor() {
        return nextQueueingDraw -> {
            paymentService.sendPaymentEmail(nextQueueingDraw);
            return nextQueueingDraw;
        };
    }

    @Bean
    public JpaItemWriter<Draw> sendEmailNextUsersWriter() {
        shareData.remainSeatCnt = 0;
        return new JpaItemWriterBuilder<Draw>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
