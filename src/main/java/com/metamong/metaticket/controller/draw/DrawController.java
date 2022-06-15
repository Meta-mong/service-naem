package com.metamong.metaticket.controller.draw;

import com.metamong.metaticket.domain.draw.Draw;
import com.metamong.metaticket.domain.draw.dto.DrawDTO;
import com.metamong.metaticket.domain.user.dto.UserDTO;
import com.metamong.metaticket.service.draw.DrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/draws")
@Controller
public class DrawController {

    private final DrawService drawService;
    private final HttpSession session;

    @GetMapping("/")
    public String selectMyDraws(Model model) {
        UserDTO.SESSION_USER_DATA currentUser = (UserDTO.SESSION_USER_DATA) session.getAttribute("user");
        List<DrawDTO.HISTORY> myDraws = drawService.findByUserId(currentUser.getId());
        model.addAttribute("myDraws", myDraws);
        return "mypage/draws";
    }

    @PostMapping("/{concertId}")
    public String applyDraw(@PathVariable("concertId") Long concertId) {
        UserDTO.SESSION_USER_DATA currentUser = (UserDTO.SESSION_USER_DATA) session.getAttribute("user");
        drawService.applyDraw(currentUser.getId(), concertId);

        return "redirect:/";
    }

    @PostMapping("/{drawId}")
    public String cancelDraw(@PathVariable("drawId") Long drawId) {
        //유저 확인해야됨
        drawService.cancelDraw(drawId);
        return "redirect:/";
    }
}
