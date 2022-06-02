package com.metamong.metaticket.controller.notice;

import com.metamong.metaticket.domain.notice.dto.NoticeDTO;
import com.metamong.metaticket.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    @Autowired
    NoticeService noticeService;

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


}
