package com.shop.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne   // 일대일 매핑
    @JoinColumn(name = "member_id") // 매핑할 외래키 지정
    private Member member;
}

