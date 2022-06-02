package com.metamong.metaticket.controller.admin;

import com.metamong.metaticket.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    AdminService adminService;

    //로그인 /로그아웃
    @PostMapping (value = "/login")
    public String adminlogin(@RequestParam ("admin") String adminloginId,
                             @RequestParam("admin123") String password, Model model){
        try {
            boolean result = adminService.adminlogin(adminloginId,password);
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
    public String adminlogout(HttpSession session){
        adminService.adminlogout(session);

        return "redirect:/admin/adminloginform";
    }



}
