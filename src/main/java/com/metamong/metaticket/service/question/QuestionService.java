package com.metamong.metaticket.service.question;

import com.metamong.metaticket.domain.admin.Admin;
import com.metamong.metaticket.domain.question.Question;
import com.metamong.metaticket.domain.question.dto.QuestionDTO;

import java.util.List;
import java.util.Optional;

public interface QuestionService {


    //문의사항 전제 초회
    public List<QuestionDTO.Quest> allQuestionList() throws Exception;

    //문의사항 등록을 위한 메서드
    public boolean register(QuestionDTO.Quest dto) throws Exception;

    //문의사항 삭제하는 메서드
    public void questionDelete (Long id) throws Exception;

    //문의사항 수정 -> 유저 내용 수정
    public Question updateQuestion (QuestionDTO.Quest dto) throws Exception;

    //문의사항 - 관리자 답변 추가(댓글)
    public Question answer ( Long ques_id, String Answer) throws Exception;


    //문의사항 - 관리자 답변 삭제
    public Question replyDelete(Long ques_id) throws Exception;

    //문의사항 수정 -> 댓글 여부 -> 댓글이 있으면 Y / 없으면 N (answer)

    public QuestionDTO.Quest entityToDto (Question question) throws Exception;

    public Question dtoToEntity(QuestionDTO.Quest dto) throws Exception;

}
