package com.metamong.metaticket.pay.kakao.ApprovalVO;

import lombok.Data;

@Data
public class AmountVO {

    private Integer total, tax_free, vat, point, discount;
}
