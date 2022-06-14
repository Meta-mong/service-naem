package com.metamong.metaticket.domain.payment.kakao.ApprovalVO;

import lombok.Data;

@Data
public class AmountVO {

    private Integer total, tax_free, vat, point, discount;
}
