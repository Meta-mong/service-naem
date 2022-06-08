package com.metamong.metaticket.pay.kakao;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
public class KakaoPay {
    private static final String HOST = "https://kapi.kakao.com";

    private KakaoPayReadyVO kakaoPayReadyVO;
    private KakaoPayApprovalVO kakaoPayApprovalVO;

    @Value("${KAKAO_ADMIN_ID_LSH}")
    private String ADMIN_KEY;

    public String kakaoPayReady() {

        // User user =  (User)SessionUtils.getAttribute("LOGIN_USER"); // 로그인한 유저
        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = getHeaders();

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME"); // 가맹점 코드(테스트 결제는 "TC0ONETIME")
        params.add("partner_order_id", "1001"); // 주문번호
        params.add("partner_user_id", "gorany"); // 회원 ID
        params.add("item_name", "갤럭시S9"); // 상품명
        params.add("quantity", "1"); // 상품 수량
        params.add("total_amount", "2100"); // 상품 총액
        params.add("tax_free_amount", "100"); // 상품 비과세 금액
        params.add("approval_url", "http://localhost/pay/kakao/success"); // 결제 성공 시 redirect url
        params.add("cancel_url", "http://localhost/pay/kakao/cancel"); // 결제 취소 시 redirect url
        params.add("fail_url", "http://localhost/pay/kakao/fail");  // 결제 성공 시 redirect url

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


    public KakaoPayApprovalVO kakaoPayInfo(String pg_token) {

        log.info("KakaoPayInfoVO............................................");
        log.info("-----------------------------");

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = getHeaders();

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME"); // 가맹점 코드(테스트:"TC0ONETIME")
        params.add("tid", kakaoPayReadyVO.getTid()); // 결제 고유번호, 결제 준비 API 응답에 포함
        params.add("partner_order_id", "1001"); // 가맹점 주문번호, 결제 준비 API 요청과 일치해야 함
        params.add("partner_user_id", "gorany"); // 가맹점 회원 id, 결제 준비 API 요청과 일치해야 함
        params.add("pg_token", pg_token);  // 결제승인 요청을 인증하는 토큰
        params.add("total_amount", "2100"); // 상품 총액, 결제 준비 API 요청과 일치해야 함

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
            log.info("" + kakaoPayApprovalVO);

            return kakaoPayApprovalVO;

        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    // header() 셋팅
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + ADMIN_KEY);
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        return headers;
    }
}
