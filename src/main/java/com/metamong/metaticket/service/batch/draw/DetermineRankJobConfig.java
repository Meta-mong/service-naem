package com.metamong.metaticket.service.batch.draw;

import com.metamong.metaticket.domain.draw.Draw;
import com.metamong.metaticket.domain.draw.DrawState;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
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

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class DetermineRankJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private int chunkSize = 10;
    private HashSet<Integer> checkDupleRank;

    @Bean
    public Job determineRankJob() {
        return jobBuilderFactory.get("determineRankJob")
                .start(determineRankStep())
                .build();
    }

    @Bean
    public Step determineRankStep() {
        return stepBuilderFactory.get("determineRankStep")
                .<Draw, Draw> chunk(chunkSize)
                .reader(determineRankReader(null))
                .processor(determineRankProcessor(null, null))
                .writer(determineRankWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaCursorItemReader<Draw> determineRankReader(@Value("#{jobParameters[concertId]}") Long concertId){
        Map<String, Object> params = new HashMap<>();
        params.put("concertId", concertId);

        return new JpaCursorItemReaderBuilder<Draw>()
                .name("determineRankReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select d from Draw d join d.concert c where c.id=:concertId")
                .parameterValues(params)
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Draw, Draw> determineRankProcessor(@Value("#{jobParameters[seatNum]}") Long seatNum,
                                                            @Value("#{jobParameters[drawCnt]}") Long drawCnt) {
        checkDupleRank = new HashSet<>();
        return requireRankDraw -> {
            int rank = determineRank(requireRankDraw, drawCnt);

            if (rank <= seatNum) {
                //paymentService.sendEmail(requireRankDraw);
            }

            return requireRankDraw;
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<Draw> determineRankWriter() {
        return new JpaItemWriterBuilder<Draw>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    private int determineRank(Draw draw, Long drawCnt) {
        while (true) {
            int rank = (int)(Math.random()*drawCnt);
            if (!checkDupleRank.contains(rank)) {
                checkDupleRank.add(rank);
                draw.setRanking(rank);
                draw.setState(DrawState.QUEUE);
                return rank;
            }
        }
    }
}
