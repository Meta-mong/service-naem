package com.metamong.metaticket.service.question;

import com.metamong.metaticket.domain.admin.Admin;
import com.metamong.metaticket.domain.question.Question;
import com.metamong.metaticket.domain.question.dto.QuestionDTO;
import com.metamong.metaticket.repository.admin.AdminRepository;
import com.metamong.metaticket.repository.question.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
                .user_id(dto.getUser_id())
                .classify(dto.getClassify())
                .title(dto.getTitle())
                .ques_content(dto.getQues_content())
                .answer(dto.isAnswer())
                .reply_content(dto.getReply_content())
                .build();

        return question;
    }

    // 객체를 DTO 클래스로 변환
    public QuestionDTO.Quest entityToDto (Question question){
        QuestionDTO.Quest dto =QuestionDTO.Quest.builder()
                .id(question.getId())
                .user_id(question.getUser_id())
                .title(question.getTitle())
                .classify(question.getClassify())
                .ques_content(question.getQues_content())
                .answer(question.isAnswer())
                .reply_content(question.getReply_content())
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
    public Question replyContent(Long ques_id, String reply_content) throws Exception {
        Question replyContent = questionRepository.findById(ques_id).orElse(null);
        replyContent.setAnswer(true);
        replyContent.setReply_content(reply_content);
        questionRepository.save(replyContent);

        return replyContent;
    }

    //댓글 삭제
    public Question replyDelete(Long ques_id) throws Exception {
        Question replyDelete =questionRepository.findById(ques_id).orElse(null);
        replyDelete.setAnswer(false);
        replyDelete.setReply_content(null);

        return replyDelete ;
    }
    // 댓글 여부 (유/무)
    @Override
    public boolean answer(QuestionDTO.Quest dto) throws Exception {
        try {
            Question answer = dtoToEntity(dto);
            questionRepository.save(answer);
            return true;
        } catch (Exception e) {
            throw e;
        }

    }
}
