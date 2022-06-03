package com.metamong.metaticket.repository.question;

import com.metamong.metaticket.domain.question.Question;
import com.metamong.metaticket.domain.question.dto.QuestionDTO;
import com.metamong.metaticket.repository.user.UserRepository;
import com.metamong.metaticket.service.question.QuestionService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@PropertySource("classpath:application.yml")
class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionService questionService;

    Question question;
    QuestionDTO.Quest dto;

    @BeforeEach
    void setUp() {
        question = Question.builder()
                .id(1L)
                .title("제목")
                .classify("분류")
                .ques_content("내용")
                .answer(true)
                .reply_content("댓글")
                .build();

        // 객체를 DTO 클래스로 변환
        dto = QuestionDTO.Quest.builder()
                .id(1L)
                .title("제목")
                .classify("분류")
                .ques_content("s내용")
                .answer(true)
                .reply_content("댓글")
                .build();

    }

    @Test
    @DisplayName("문의사항 삽입 테스트1")
    public void InsertTest(){
        Question question = Question.builder().
                classify("테스트").
                title("테스트").
                ques_content("테스트").
                answer(true).
                build();
        Question temp = questionRepository.save(question);
        System.out.println(temp.toString());
    }

    @Test
    @DisplayName("문의사항 수정 테스트")
    public void updateTest() throws Exception{

        Question findQuestion = questionRepository.findById(1L).get();
        //findQuestion.update("테스트1","테스트2","테스트3");
        Question updateNotice = questionRepository.save(findQuestion);

        assertAll(
                () -> assertEquals(updateNotice.getTitle(),"테스트2"),
                () -> assertEquals(updateNotice.getQues_content(), "테스트3"),
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
    //객체로 변환

    //문의사항 등록
    @Test
    @DisplayName("등록")
    void register() throws Exception {

        Question question = questionService.dtoToEntity(dto);
        Question savedQuestion = questionRepository.save(question);

        assertNotNull(savedQuestion);
    }



    @Test
    @DisplayName("수정")
    void  updateQuestion() throws Exception {
        Question updateQuestion = questionRepository.findById(dto.getId()).orElse(null);
        updateQuestion.update(dto);

    }

    @Test
    @DisplayName("삭제")

    void questionDelete() throws Exception {
        questionRepository.deleteById(1L);
    }


    @Test
    @DisplayName("리스트 조회")
    void list() throws Exception{
        List<QuestionDTO.Quest> list = questionService.allQuestionList();
        for(QuestionDTO.Quest temp : list){
            System.out.println(temp);
        }

    }
}
