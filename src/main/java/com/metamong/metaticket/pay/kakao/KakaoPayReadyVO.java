package com.metamong.metaticket.pay.kakao;

import lombok.Data;

import java.util.Date;

@Data
public class KakaoPayReadyVO {
    // 서버가 사용자의 결제 요청 정보를 카카오에 보내고 난 후 받는 정보들
    private String tid; // 결제 고유 번호
    private String next_redirect_pc_url; // 요청한 클라이언트가 PC 웹일 경우 카카오톡으로 결제 요청 메시지(TMS)를 보내기 위한 사용자 정보 입력 화면 Redirect URL
    private Date created_at;
}
