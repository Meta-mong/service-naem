package com.metamong.metaticket.controller.interest;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.domain.user.dto.UserDTO;
import com.metamong.metaticket.service.interest.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class InterestController {

    private final InterestService interestService;
    private final HttpSession session;

    @GetMapping("/interests")
    public String selectInterests(Model model) {
        UserDTO.SESSION_USER_DATA currentUser = (UserDTO.SESSION_USER_DATA) session.getAttribute("user");
        List<Concert> interestedConcertList = interestService.findUserInterestedConcertList(currentUser.getId());
        model.addAttribute("concerts", interestedConcertList); //concert 사진만으로 바꾸기

        return "interest/interestlist";
    }

    @PostMapping("/interests/{concertId}")
    public String saveOrDeleteInterest(@RequestParam("userId") Long userId, @PathVariable("concertId") Long concertId) {
        UserDTO.SESSION_USER_DATA currentUser = (UserDTO.SESSION_USER_DATA) session.getAttribute("user");
        if (interestService.isInterested(currentUser.getId(), concertId))
            interestService.saveInterest(currentUser.getId(), concertId);
        else
            interestService.deleteInterest(currentUser.getId(), concertId);
        return "redirect:/";
    }
}
