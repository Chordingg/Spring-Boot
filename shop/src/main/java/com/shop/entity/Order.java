package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")  // 정렬할 때 사용하는 "order" 키워드가 있기 때문에 Order 엔티티에 매핑되는 테이블명을 "orders"로 지정
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 한 명의 회원은 여러 번 주문을 할 수 있으므로 다대일 단방향 매핑

    // 주문 상품 엔티티와 일대다 매핑. 외래키(item_id)가 order_item 테이블에 있으므로 연관 관계의 주인은 OrderItem 엔티티이다.
    // Order 엔티티가 주인이 아니므로 "mappedBy" 속성으로 연관 관계의 주인을 설정
    // 속성의 값으로 "order" 를 적어준 이유는  OrderItem 에 있는 Order 에 의해 관리된다는 의미로 해석
    // 즉, 연관 관계의 주인의 필드인 order 를 mappedBy 의 값으로 세팅
    // 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이하는 CascadeTypeAll 옵션을 설정
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) 
    private List<OrderItem> orderItems = new ArrayList<>(); // 하나의 주문이 여러 개의 주문 상품을 갖으므로 List 자료형을 사용해서 매핑

    private LocalDateTime orderDate; // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태

    private LocalDateTime regTime; // 작성일

    private LocalDateTime updateTime; // 수정일
}
