package com.shop.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")  // 정렬할 때 사용하는 "order" 키워드가 있기 때문에 Order 엔티티에 매핑되는 테이블명을 "orders"로 지정
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id") // 외래키 설정
    private Order order;

    private int orderPrice; // 가격

    private int count; // 수량

    private LocalDateTime regTime; // 작성일

    private LocalDateTime updateTime; // 수정일
}
