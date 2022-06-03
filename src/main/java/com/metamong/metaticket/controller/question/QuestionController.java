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
    public void questionUpdate(@PathVariable Long id, @ModelAttribute QuestionDTO.Quest dto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Question questionUpdate = questionService.updateQuestion(dto);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/question/questionlist");
        dispatcher.forward(request, response);
    }



}
