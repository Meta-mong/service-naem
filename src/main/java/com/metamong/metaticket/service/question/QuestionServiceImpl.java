package com.metamong.metaticket.service.question;

import com.metamong.metaticket.domain.question.Question;
import com.metamong.metaticket.domain.question.dto.QuestionDTO;
import com.metamong.metaticket.repository.admin.AdminRepository;
import com.metamong.metaticket.repository.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class QuestionServiceImpl implements QuestionService{
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AdminRepository adminRepository;


    //객체로 변환
    public Question dtoToEntity (QuestionDTO.Quest dto){
        Question question = Question.builder()
                .id(dto.getId())
                .user(dto.getUser())
                .classify(dto.getClassify())
                .title(dto.getTitle())
                .quesContent(dto.getQuesContent())
                .answer(dto.getAnwser())
                .build();

        return question;
    }

    // 객체를 DTO 클래스로 변환
    public QuestionDTO.Quest entityToDto (Question question){
        QuestionDTO.Quest dto =QuestionDTO.Quest.builder()
                .id(question.getId())
                .user(question.getUser())
                .title(question.getTitle())
                .classify(question.getClassify())
                .quesContent(question.getQuesContent())
                .anwser(question.getAnswer())
                .build();
        return dto;
    }

    //문의사항 조회
    @Override
    public List<QuestionDTO.Quest> allQuestionList() throws Exception {
        List<QuestionDTO.Quest> questionList = new ArrayList<>();
        List<Question> ql = questionRepository.findAll();
        for(Question temp: ql){
            QuestionDTO.Quest qdto = entityToDto(temp);
            questionList.add(qdto);
        }
        return questionList;
    }

    //문의사항 등록
    @Override
    public boolean register(QuestionDTO.Quest dto) throws Exception {
        try {
            Question question =dtoToEntity(dto);
            questionRepository.save(question);
            return true;
        }catch (Exception e){
            throw e;
        }

    }


    //문의사항 삭제
    @Override
    public void questionDelete(Long id) throws Exception {
        questionRepository.deleteById(id);
    }

    //문의사항 수정
    @Override
    public Question updateQuestion(QuestionDTO.Quest dto) throws Exception {
        Question updateQuestion = questionRepository.findById(dto.getId()).orElse(null);
        updateQuestion.update(dto);
        return updateQuestion;
    }


    //댓글 추가
    @Override
    public Question answer(Long ques_id, String answer) throws Exception {
        Question question = questionRepository.findById(ques_id).orElse(null);

        question.setAnswer(answer);
        Question quest = questionRepository.save(question);

        return  quest;
    }

    //댓글 삭제
    public Question replyDelete(Long ques_id) throws Exception {
        Question replyDelete =questionRepository.findById(ques_id).orElse(null);
        replyDelete.setAnswer(null);

        return replyDelete ;
    }



}
