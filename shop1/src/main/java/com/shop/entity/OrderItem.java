package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_item")
@ToString
@Setter
@Getter
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")  //외래키 설정
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice; //가격
    private int count; //  수량

    public static OrderItem createOrderItem(Item item, int count) {

        OrderItem orderItem = new OrderItem();

        orderItem.setItem(item); // 주문할 상품과
        orderItem.setCount(count); // 주문 수량을 세팅

        orderItem.setOrderPrice(item.getPrice()); // 현재 시간 기준으로 상품 가격을 주문 가격으로 세팅

        item.removeStock(count); // 주문 수량만큼 상품의 재고 수량을 감소

        return orderItem;
    }

    public int getTotalPrice(){ // 주문 가격과 주문 수량을 곱해서 해당 상품을 주문한 총 가격을 계산
        return orderPrice*count;
    }

    // 주문 취소 시 주문 수량만큼 상품의 재고를 더함
    public void cancel(){
        this.getItem().addStock(count);
    }
}
