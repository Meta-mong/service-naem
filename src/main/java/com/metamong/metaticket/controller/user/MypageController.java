package com.metamong.metaticket.controller.user;

import com.metamong.metaticket.domain.user.User;
import com.metamong.metaticket.domain.user.dto.UserDTO;
import com.metamong.metaticket.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MypageController {
    @Autowired
    UserService userService;

    @Autowired
    HttpSession session;

    //비밀번호 변경
    @PostMapping("/user/modifyPasswd")
    @ResponseBody
    public Map<String, Object> modifyInfo(@RequestParam("email") String email, @RequestParam("passwd") String passwd){
        Map<String, Object> map = new HashMap<>();
        boolean result = userService.modifyPasswd(session, passwd);
        if(result==false){
            map.put("result", "비밀번호 변경에 실패하였습니다. 다시 시도해주세요.");
        }else{
            UserDTO.SESSION_USER_DATA dto = (UserDTO.SESSION_USER_DATA)session.getAttribute("user");
            User user = userService.userInfo(dto.getId());
            dto.setPasswd(user.getPasswd());
            session.setAttribute("user", dto);
            map.put("result", "비밀번호가 성공적으로 변경되었습니다.");
        }
        return map;
    }

    //회원 탈퇴 -> 페이지 만들고 해당 페이지에서 비밀번호 입력하고 탈퇴 버튼 누르도록 구현?
    @PostMapping("/user/unregister")
    @ResponseBody
    public Map<String, Object> unregister(@RequestParam("passwd") String passwd){
        Map<String, Object> map = new HashMap<>();
        boolean result = userService.unregister(session, passwd);
        map.put("result", result);
        if(result==true) session.invalidate();
        return map;
    }

}
