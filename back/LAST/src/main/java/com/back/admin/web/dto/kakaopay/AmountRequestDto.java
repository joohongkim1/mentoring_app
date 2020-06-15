package com.back.admin.web.dto.kakaopay;

import lombok.Data;

@Data
public class AmountRequestDto {
    private Integer total, tax_free, vat, point, discount;
}
