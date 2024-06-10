package com.shop.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cart_Item")
public class CartItem {

    @Id
    @Column(name = "cart_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne  // 다대일 매핑 , 이 테이블에서 외래키 생성
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;
}

