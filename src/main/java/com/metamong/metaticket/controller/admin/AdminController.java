package com.metamong.metaticket.controller.admin;

import com.metamong.metaticket.domain.draw.Draw;
import com.metamong.metaticket.domain.user.User;
import com.metamong.metaticket.domain.user.dto.UserDTO;
import com.metamong.metaticket.domain.user.dto.UserPage;
import com.metamong.metaticket.service.admin.AdminService;
import com.metamong.metaticket.service.draw.DrawService;
import com.metamong.metaticket.service.user.PageService;
import com.metamong.metaticket.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @Autowired
    PageService pageService;

    @Autowired
    DrawService drawService;

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
        List<Draw> draws = drawService.findByUserId(user.getId());
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
}
