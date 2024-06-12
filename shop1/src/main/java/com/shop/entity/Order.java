package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@ToString
@Setter@Getter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 주문 상품 엔티티와 일대다 매핑. 외래키(item_id)가 order_item 테이블에 있으므로 연관 관계의 주인은 OrderItem 엔티티이다.
    // Order 엔티티가 주인이 아니므로 "mappedBy" 속성으로 연관 관계의 주인을 설정
    // 속성의 값으로 "order" 를 적어준 이유는  OrderItem 에 있는 Order 에 의해 관리된다는 의미로 해석
    // 즉, 연관 관계의 주인의 필드인 order 를 mappedBy 의 값으로 세팅
    @OneToMany(mappedBy = "order" ,cascade = CascadeType.ALL
            ,orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> oderItems = new ArrayList<>();

    private LocalDateTime orderDate;   //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;


}
