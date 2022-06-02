package com.metamong.metaticket.controller.notice;

import com.metamong.metaticket.domain.notice.Notice;
import com.metamong.metaticket.domain.notice.dto.NoticeDTO;
import com.metamong.metaticket.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    @Autowired
    NoticeService noticeService;
    
    // 공지사항 전체 리스트 불러오기
    @GetMapping("/list")
    public String noticeList(Model model) throws Exception {
        model.addAttribute("Allnoticelist",noticeService.allNoticeInfo());
        return "noticelist";
    }

    // 공지사항 등록
    @PostMapping(value = "/upload")
    public String noticeupload(@ModelAttribute NoticeDTO.Notice dto, Model model){

        try {
            boolean result = noticeService.register(dto);
            if(result == true){
                List<NoticeDTO.Notice> lup = noticeService.allNoticeInfo();
                model.addAttribute("list",lup);
                return "noticelist";
            }
            throw new Exception();
        } catch (Exception e) {
            model.addAttribute("err","등록실패");
            return "noticeupload"; // jsp

        }
    }

    //공지사항 수정
    @PostMapping("/update")
    public void noticeUpdate(@ModelAttribute NoticeDTO.Notice dto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Notice noticeUpdate =noticeService.updateNotice(dto);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/notice/noticelist");
        dispatcher.forward(request, response);
    }


    //공지사항 삭제
    @PostMapping("/delete/{id}")
    public ResponseEntity noticeDelete (@PathVariable Long id) throws Exception {
        noticeService.noticeDelete(id);
        return ResponseEntity.ok(id);
    }


}
