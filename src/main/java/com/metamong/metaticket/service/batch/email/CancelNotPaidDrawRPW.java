package com.metamong.metaticket.service.batch.email;

import com.metamong.metaticket.domain.draw.Draw;
import com.metamong.metaticket.service.draw.DrawService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CancelNotPaidDrawRPW {

    private final EntityManagerFactory entityManagerFactory;
    private final ShareData shareData;
    private final DrawService drawService;

    @Bean
    @StepScope
    public JpaCursorItemReader<Draw> cancelNotPaidDrawsReader(@Value("#{jobParameters[concertId]}") Long concertId) {
        String findNotPaidDraw = "select d from Draw d " +
                "join d.concert c " +
                "where c.id=:concertId and d.emailSendDate<:threeDaysAgo and d.state='WIN'";

        Map<String, Object> params = new HashMap<>();
        params.put("concertId", concertId);
        params.put("threeDaysAgo", LocalDateTime.parse(LocalDate.now().minusDays(3) + "T00:00:00"));

        return new JpaCursorItemReaderBuilder<Draw>()
                .name("cancelNotPaidDrawsReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString(findNotPaidDraw)
                .parameterValues(params)
                .build();
    }

    @Bean
    public ItemProcessor<Draw, Draw> cancelNotPaidDrawsProcessor() {
        log.info("cancelNotPaidDrawsProcessor");
        return notPaidDraw -> {
            shareData.remainSeatCnt++;
            System.out.println("shareData.remainSeatCnt = " + shareData.remainSeatCnt);
            drawService.cancelDraw(notPaidDraw.getId());

            return notPaidDraw;
        };
    }

    @Bean
    public JpaItemWriter<Draw> cancelNotPaidDrawsWriter() {
        log.info("cancelNotPaidDrawsWriter");
        return new JpaItemWriterBuilder<Draw>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
