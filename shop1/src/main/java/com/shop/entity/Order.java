package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@ToString
@Setter@Getter
@Log4j2
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
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime orderDate;   //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // orderItems 에는 주문 상품 정보들을 담는다. orderItem 객체를 order 객체의 orderItems 에 추가
    public void addOrderItem(OrderItem orderItem) {

        orderItems.add(orderItem);
        // Order 엔티티와 OrderItem 엔티티가 양방향 참조 관계이므로, orderItem 객체에도 order 객체를 세팅
        orderItem.setOrder(this);
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList) {

        Order order = new Order();

        order.setMember(member); // 상품을 주문한 회원의 정보를 세팅

        // 여러 개의 주문 상품을 담을 수 있도록 리스트형태로 파라미터 값을 받으며 주문 객체에 orderItem 객체를 추가
        for(OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDER); // 주문 상태 ("ORDER")
        order.setOrderDate(LocalDateTime.now()); // 현재 시간을 주문 시간으로 세팅

        return order;
    }

    public int getTotalPrice(){ // 총 주문 금액을 구하는 메소드
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    // 주문 상태 취소로 변경, 주문 수량을 상품 재고에 더함
    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;

        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

}
