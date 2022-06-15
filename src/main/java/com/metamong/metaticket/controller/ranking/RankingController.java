package com.metamong.metaticket.controller.ranking;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ranking")
public class RankingController {

    @GetMapping
    public String ranking(){
        return "/ranking-page/ranking";
    }
}
