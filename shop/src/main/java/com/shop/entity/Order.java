package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @OneToMany(mappedBy = "order")  // 외래키 설정하지 않는다.
    private List<OrderItem> orderItems;

    private LocalDateTime orderDate; // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태

    private LocalDateTime regTime; // 작성일

    private LocalDateTime updateTime; // 수정일
}
