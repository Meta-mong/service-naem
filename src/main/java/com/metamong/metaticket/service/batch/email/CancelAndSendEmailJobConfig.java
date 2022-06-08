package com.metamong.metaticket.service.batch.email;

import com.metamong.metaticket.domain.draw.Draw;
import com.metamong.metaticket.service.draw.DrawService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class CancelAndSendEmailJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CancelNotPaidDrawRPW cancelNotPaidDrawRPW;
    private final SendEmailNextUserRPW sendEmailNextUserRPW;

    private final int chunkSize = 10;

    @Bean
    public Job cancelAndSendEmailJob() {
        return jobBuilderFactory.get("cancelAndSendEmailJob")
                .start(cancelNotPaidStep())
                .next(sendEmailNextUserStep())
                .build();
    }

    @Bean
    @JobScope
    public Step cancelNotPaidStep() {
        return stepBuilderFactory.get("cancelNotPaidStep")
                .<Draw, Draw>chunk(chunkSize)
                .reader(cancelNotPaidDrawsReader())
                .processor(cancelNotPaidDrawsProcessor())
                .writer(cancelNotPaidDrawsWriter())
                .build();
    }

    @Bean
    @JobScope
    public Step sendEmailNextUserStep() {
        return stepBuilderFactory.get("sendEmailNextUserStep")
                .<Draw, Draw>chunk(chunkSize)
                .reader(sendEmailNextUsersReader())
                .processor(sendEmailNextUsersProcessor())
                .writer(sendEmailNextUsersWriter())
                .build();
    }

    public JpaCursorItemReader<Draw> cancelNotPaidDrawsReader() {
        return cancelNotPaidDrawRPW.cancelNotPaidDrawsReader(null);
    }

    public ItemProcessor<Draw, Draw> cancelNotPaidDrawsProcessor() {
        return cancelNotPaidDrawRPW.cancelNotPaidDrawsProcessor();
    }

    public JpaItemWriter<Draw> cancelNotPaidDrawsWriter() {
        return cancelNotPaidDrawRPW.cancelNotPaidDrawsWriter();
    }

    public JpaCursorItemReader<Draw> sendEmailNextUsersReader() {
        return sendEmailNextUserRPW.sendEmailNextUsersReader(null);
    }

    public ItemProcessor<Draw, Draw> sendEmailNextUsersProcessor() {
        return sendEmailNextUserRPW.sendEmailNextUsersProcessor();
    }

    public JpaItemWriter<Draw> sendEmailNextUsersWriter() {
        return sendEmailNextUserRPW.sendEmailNextUsersWriter();
    }
}
