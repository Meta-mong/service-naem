package com.metamong.metaticket.controller.pay;

import com.metamong.metaticket.pay.kakao.KakaoPay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class KakaoPayController {

    private final KakaoPay kakaoPay;

    @GetMapping("/kakaoPay")
    public String kakaoPay() {
        return "/pay/kakao/kakaoPay";
    }

    @PostMapping("/kakaoPay")
    public String kakaoPayPost() {
        log.info("kakaoPay post............................................");
        return "redirect:" + kakaoPay.kakaoPayReady();
    }

    @GetMapping("/pay/kakao/success")
    public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model) {
        log.info("kakaoPaySuccess get............................................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);

        model.addAttribute("info", kakaoPay.kakaoPayInfo(pg_token));
    }
}
