package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Entity // 클래스를 엔티티로 선언
@Table(name = "item")   // 엔티티와 매팅할 테이블을 지정(테이블명 지정)
public class Item {

    @Id // 테이블의 기본키에 사용할 속성을 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 키 값을 생성하는 전략 명시
    private Long id;    // 상품 코드

    @Column(nullable = false, length = 50)  // null값 불가능
    private String itemNm;  // 상품명

    @Column(name = "price", nullable = false)
    private int price;  // 가격

    @Column(nullable = false)
    private int stockNumber;    // 재고 수량

    @Lob    // BLOB, CLOB 타입 매핑
    @Column(nullable = false)
    private String itemDetail;  // 상품 상세 설명

    @Enumerated(EnumType.STRING)    // enum 타입 매핑
    private ItemSellStatus itemSellStatus;  // 상품 판매 상태

    private LocalDateTime regTime;  // 등록 시간

    private LocalDateTime updateTime;   // 수정 시간
}
