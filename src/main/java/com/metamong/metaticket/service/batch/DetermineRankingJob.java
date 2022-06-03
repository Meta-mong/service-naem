package com.metamong.metaticket.service.batch;

import com.metamong.metaticket.domain.draw.Draw;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class DetermineRankingJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private static final int chunkSize = 10;

    @Bean
    public Job jpaItemWriterJob() {
        return jobBuilderFactory.get("jpaItemWriterJob")
                .start(jpaItemWriterStep1(null))
                .build();
    }

    @Bean
    @JobScope
    public Step jpaItemWriterStep1(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("jpaItemWriterStep1")
                .<Draw, Draw>chunk(chunkSize)
                .reader(drawByEndDateReader())
                //.processor(jpaItemProcessor())
                .writer(drawByEndDateWriter())
                .build();
    }

    @Bean
    @StepScope//@Value("#") String requestDate
    public JpaPagingItemReader<Draw> drawByEndDateReader() {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("today", LocalDateTime.now());

        return new JpaPagingItemReaderBuilder<Draw>()
                .name("drawByEndDateReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT d FROM Draw d")
                //.parameterValues(parameterValues)
                .build();
    }

//    @Bean
//    public ItemProcessor<Draw, Draw> jpaItemProcessor() {
//        return Draw -> {
//            Draw.
//        };
//    }

    public ItemWriter<Draw> drawByEndDateWriter() {
        return list -> {
            for (Draw draw : list) {
                System.out.println("draw = " + draw.getId());
            }
        };
    }
}
