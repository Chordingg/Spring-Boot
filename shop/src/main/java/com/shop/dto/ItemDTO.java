package com.shop.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private Long id;
    private String itemNm;
    private int price;
    private String stockNumber;
    private String itemDetail;
    private String sellStatusCd;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}
