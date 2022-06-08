package com.metamong.metaticket.pay.kakao;

import lombok.extern.java.Log;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@Log
public class KakaoPay {
    private static final String HOST = "https://kapi.kakao.com";

    private KakaoPayReadyVO kakaoPayReadyVO;

    public String kakaoPayReady() {

        // User user =  (User)SessionUtils.getAttribute("LOGIN_USER"); // 로그인한 유저
        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "9cc037f0bc9bdcc3ea2493cf1c20ff17");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME"); // 가맹점 코드(테스트 결제는 "TC0ONETIME")
        params.add("partner_order_id", "1001"); // 주문번호
        params.add("partner_user_id", "gorany"); // 회원 ID
        params.add("item_name", "갤럭시S9"); // 상품명
        params.add("quantity", "1"); // 상품 수량
        params.add("total_amount", "2100"); // 상품 총액
        params.add("tax_free_amount", "100"); // 상품 비과세 금액
        params.add("approval_url", "http://localhost:8080/pay/kakao/success"); // 결제 성공 시 redirect url
        params.add("cancel_url", "http://localhost:8080/pay/kakao/cancel"); // 결제 취소 시 redirect url
        params.add("fail_url", "http://localhost:8080/pay/kakao/fail");  // 결제 성공 시 redirect url

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);

            log.info("" + kakaoPayReadyVO);

            return kakaoPayReadyVO.getNext_redirect_pc_url();

        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return "/pay";

    }
}
