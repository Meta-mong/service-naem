package com.metamong.metaticket.repository.question;

import com.metamong.metaticket.domain.question.Question;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@PropertySource("classpath:application.yml")
class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("문의사항 삽입 테스트1")
    public void InsertTest(){
        Question question = Question.builder().
                classify("테스트").
                title("테스트").
                content("테스트").
                answer(true).
                build();
        Question temp = questionRepository.save(question);
        System.out.println(temp.toString());
    }

    @Test
    @DisplayName("문의사항 수정 테스트")
    public void updateTest() throws Exception{

        Question findQuestion = questionRepository.findById(1L).get();
        findQuestion.update("테스트1","테스트2","테스트3");
        Question updateNotice = questionRepository.save(findQuestion);

        assertAll(
                () -> assertEquals(updateNotice.getTitle(),"테스트2"),
                () -> assertEquals(updateNotice.getContent(), "테스트3"),
                () -> assertEquals(updateNotice.getClassify(),"테스트1")
        );
    }

    @Test
    @DisplayName("문의사항 삭제 테스트")
    public void deleteTest(){
        Optional<Question> question = questionRepository.findById(1L);
        Assert.assertTrue(question.isPresent());


        question.ifPresent(selectNotice ->{
            questionRepository.delete(selectNotice);
        });

        Optional<Question> deletedQuest = questionRepository.findById(1L);

        Assert.assertFalse(deletedQuest.isPresent());

    }


    @Test
    @DisplayName("문의사항 조회 테스트")
    public void selectTest(){
        Long id = 1L;
        Optional<Question> result = questionRepository.findById(id);

        if(result.isPresent()){
            Question question = result.get();
            assertEquals(question.getId(),1L);

            System.out.println(question.toString());
        }

    }

}