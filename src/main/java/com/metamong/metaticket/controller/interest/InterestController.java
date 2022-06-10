package com.metamong.metaticket.controller.interest;

import com.metamong.metaticket.domain.concert.Concert;
import com.metamong.metaticket.service.interest.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class InterestController {

    private final InterestService interestService;

    @GetMapping("/interests")
    public String selectInterests(@RequestParam("userId") Long userId, Model model) {
        List<Concert> interestedConcertList = interestService.findUserInterestedConcertList(userId);
        model.addAttribute("concerts", interestedConcertList); //concert 사진만으로 바꾸기

        return "interest/interestlist";
    }

    @PostMapping("/interests/{concertId}")
    public String saveOrDeleteInterest(@RequestParam("userId") Long userId, @PathVariable("concertId") Long concertId) {
        if (interestService.isInterested(userId, concertId))
            interestService.saveInterest(userId, concertId);
        else
            interestService.deleteInterest(userId, concertId);
        return "redirect:/";
    }
}
