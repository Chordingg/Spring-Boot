package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import lombok.Data;

@Data
public class ItemSearchDto {

    /*
    현재 시간과 상품 등록일을 비교해서 상품 데이터를 조회
    all : 상품 등록일 전체
    1d : 최근 하루 동안 등록된 상품
    1w : 최근 일주일 동안 등록된 상품
    1m : 최근 한달 동안 등록된 상품
    6m : 최근 6개월 동안 등록된 상품
    */
    private String searchDateType; 

    private ItemSellStatus searchSellStatus;

    // 상품을 조회할 때 어떤 유형으로 조회할지 선택
    // itemNm : 상품명  ,  createBy : 상품 등록자 아이디
    private String searchBy;

    private String searchQuery = "";
}
