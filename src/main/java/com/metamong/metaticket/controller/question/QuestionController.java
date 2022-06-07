package com.metamong.metaticket.controller.question;

import com.metamong.metaticket.domain.notice.Notice;
import com.metamong.metaticket.domain.notice.dto.NoticeDTO;
import com.metamong.metaticket.domain.question.Question;
import com.metamong.metaticket.domain.question.dto.QuestionDTO;
import com.metamong.metaticket.service.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/reply")
    public String reply(){
        return "reply";
    }
    //문의사항 리스트 조회
    @GetMapping("/list")
    public String questionList(Model model) throws Exception{
        model.addAttribute("Allqustionlist", questionService.allQuestionList());
    return "questionlist";
    }

    //문의사항 등록
    @PostMapping(value = "/upload")
    public String questionUpload(@ModelAttribute QuestionDTO.Quest dto, Model model){

        try {
            boolean result = questionService.register(dto);
            if(result == true){
                List<QuestionDTO.Quest> qup = questionService.allQuestionList();
                model.addAttribute("list",qup);
                return "questionlist";
            }
            throw new Exception();
        } catch (Exception e) {
            model.addAttribute("err","등록실패");
            return "questionupload"; // jsp

        }
    }

    //문의사항 삭제
    @PostMapping("/delete/{id}")
    public ResponseEntity questionDelete (@PathVariable Long id) throws Exception {
        questionService.questionDelete(id);
        return ResponseEntity.ok(id);
    }

    //문의사항 수정
    @PostMapping("/update/{id}")
    public void questionUpdate(@PathVariable Long id,
                               @ModelAttribute QuestionDTO.Quest dto,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        Question questionUpdate = questionService.updateQuestion(dto);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/question/questionlist");
        dispatcher.forward(request, response);
    }


    //댓글 추가
    @PostMapping("/replycontent")
    public String replyContent (@RequestAttribute("id") Long ques_id,
                                @RequestAttribute("reply") String reply_content)throws Exception{
        System.out.println("ddd : "+reply_content);
        questionService.replyContent(ques_id,reply_content);
        return "re";
    }

    //댓글 삭제
    @PostMapping("/replydelete/{gues_id}")
    public ResponseEntity replyDelete(@PathVariable Long ques_id) throws Exception{
        questionService.replyDelete(ques_id);
        return  ResponseEntity.ok(ques_id);
    }

    //댓글 여부
    @PostMapping(value = "/answer")
    public String answer (@ModelAttribute QuestionDTO.Quest dto, Model model){

        try {
            boolean result = questionService.register(dto);
            if(result == true){
                List<QuestionDTO.Quest> replyanswer  = questionService.allQuestionList();
                model.addAttribute("answer",replyanswer);
                return "questionlist";
            }
            throw new Exception();
        } catch (Exception e) {
            model.addAttribute("err","등록실패");
            return "replyupload"; // jsp

        }
    }


}
