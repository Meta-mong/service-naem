package com.metamong.metaticket.controller.admin;

import com.metamong.metaticket.domain.draw.dto.DrawDTO;
import com.metamong.metaticket.domain.notice.dto.NoticeDTO;
import com.metamong.metaticket.domain.question.dto.QuestionDTO;
import com.metamong.metaticket.domain.user.User;
import com.metamong.metaticket.domain.user.dto.UserDTO;
import com.metamong.metaticket.domain.user.dto.UserPage;
import com.metamong.metaticket.repository.question.QuestionRepository;
import com.metamong.metaticket.service.admin.AdminService;
import com.metamong.metaticket.service.draw.DrawService;
import com.metamong.metaticket.service.notice.NoticeService;
import com.metamong.metaticket.service.question.QuestionService;
import com.metamong.metaticket.service.user.PageService;
import com.metamong.metaticket.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @Autowired
    PageService pageService;

    @Autowired
    DrawService drawService;

    @Autowired
    NoticeService noticeService;

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionRepository questionRepository;

    //로그인 /로그아웃
    @PostMapping (value = "/login")
    public String adminLogin(@RequestParam ("admin") String adminloginId,
                             @RequestParam("admin123") String password, Model model){
        try {
            boolean result = adminService.adminLogin(adminloginId,password);
            if(result ==true){
                model.addAttribute("adminlogin",adminService.adminInfo(adminloginId));
                return "main"; //view
            }else {
                model.addAttribute("err","로그인에 실패했습니다.");
                return "loginForm"; //view
            }

        }catch (Exception e){
            model.addAttribute("err","계정 정보가 없습니다.");
            return "loginForm"; //view
         }
    }

    @GetMapping(value = "/logout")
    public String adminLogout(HttpSession session){
        adminService.adminLogout(session);

        return "redirect:/admin/adminloginform";
    }

    //전체 사용자 조회
    @GetMapping(value = {"/allusers", "/allusers/{presentPage}"})
    public String allUsers(Model model, @PathVariable(required = false) Integer presentPage){
        UserPage userPage = new UserPage();
        if(presentPage != null) userPage.setPresentPage(presentPage);
        long totalCnt = (int)userService.allUserCnt();
        pageService.setPage(userPage, totalCnt);

        Pageable pageable = PageRequest.of(userPage.getPresentPage()-1, 10);
        Page<User> users = userService.createPage(pageable);
        Page<UserDTO.SESSION_USER_DATA> userDtos = users.map(User::createUserDTO);

        model.addAttribute("users", userDtos);
        model.addAttribute("pageInfo", userPage);

        return "/admin/admin_user";
    }

    //회원 정보 상세 조회 페이지
    @GetMapping("/userdetail/{id}")
    public String userDetail(@PathVariable Long id, Model model){
        System.out.println("id : "+ id);
        User user = userService.userInfo(id);
        UserDTO.SESSION_USER_DATA dto = User.createUserDTO(user);
        List<DrawDTO.HISTORY> draws = drawService.findByUserId(user.getId());
        model.addAttribute("user", dto);
        model.addAttribute("draws", draws);
        return "/admin/admin_user_detail";
    }

    //회원 정보 수정
    @PostMapping ("/modifyuser")
    @ResponseBody
    public Map<String, Object> modifyUser(@RequestParam("email") String email, @RequestParam("name") String name,
                                          @RequestParam("loserCnt") int loserCnt, @RequestParam("cancelCnt") int cancelCnt){
        Map<String, Object> map = new HashMap<>();
        User user = userService.userInfo(email);
        user.setName(name.trim());
        user.setLoserCnt(loserCnt);
        user.setCancelCnt(cancelCnt);
        map.put("result", userService.saveUser(user));

        return map;
    }

    //공지사항 전체 조회
    @GetMapping("/anlist")
    public String noticeList(Model model, Pageable pageable) throws Exception {

        Page<NoticeDTO.Notice> noticeList = noticeService.allNoticeInfo(pageable);
        model.addAttribute("allNoticeList", noticeList);

        log.info("총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
                noticeList.getTotalElements(), noticeList.getTotalPages(), noticeList.getSize(),
                noticeList.getNumber(), noticeList.getNumberOfElements());

        return "admin/admin_noticelist";
    }


    //문의사항 전체 조회
    @GetMapping("/aqlist")
    public String questionList( Model model, Pageable pageable) throws Exception{
        Page<QuestionDTO.Quest> questionList = questionService.allQuestionList(pageable);
        model.addAttribute("allQuestionList", questionList);

        log.info("총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
                questionList.getTotalElements(), questionList.getTotalPages(), questionList.getSize(),
                questionList.getNumber(), questionList.getNumberOfElements());

        return "admin/admin_qnalist";
    }
    //문의사항 상세조회
    @GetMapping("/qnadetail/{questionId}")
    public String questiondetail (@PathVariable Long questionId,Model model) throws Exception {
        QuestionDTO.Quest questionDto = questionService.questiondetail(questionId);
        User user = userService.userInfo(questionDto.getUserId());
        model.addAttribute("userName", user.getName());
        model.addAttribute("question",questionDto);
        return "/admin/admin_qnadetail";
    }

    //문의사항 답글 추가 페이지 이동
    @GetMapping("/qnareply/{questionId}")
    public String answer (@PathVariable Long questionId, Model model)throws Exception{
        QuestionDTO.Quest questionDto = questionService.questiondetail(questionId);
        User user = userService.userInfo(questionDto.getUserId());
        model.addAttribute("userName", user.getName());
        model.addAttribute("question",questionDto);
        return "/admin/admin_qnareply";
    }

    // 문의사항 답글 추가 처리
    @PostMapping("/qnareply/{questionId}")
    @ResponseBody
    public Map<String, Object> questionUpdate(@PathVariable Long questionId,
                                 @RequestParam("admincontent") String admincontent) {
        System.out.println("확인 : "+admincontent);
        Map<String,Object> map = new HashMap<>();
        try {
            questionService.answer(questionId, admincontent);
            map.put("result", true);
        }catch(Exception e){
            e.getStackTrace();
            map.put("result", false);
        }
        return map;

    }



}
