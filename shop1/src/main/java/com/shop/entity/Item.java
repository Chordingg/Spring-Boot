package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Entity
@Table(name = "item")
public class Item extends BaseEntity {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String itemNm;   //item_Nm

    @Column(name = "price", nullable = false)
    private int price;   //price

    @Column(nullable = false)
    private int stockNumber; //stock_number

    @Lob
    @Column(nullable = false)
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    // 상품 데이터를 업데이트하는 로직
    public void updateItem(ItemFormDto itemFormDto){
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();

    }

    public void removeStock(int stockNumber){

        int restStock = this.stockNumber - stockNumber; // 상품의 재고 수령에서 주문 후 남은 재고 수량을 구한다

        if(restStock < 0){
            // 상품의 재고가 주문 수량보다 작을 경우 재고 부족 예외 발생
            throw new OutOfStockException("상품의 재고가 부족합니다.(현재 재고 수량 : " + this.stockNumber + ")");
        }
        // 주문 후 남은 재고 수량을 상품의 현재 재고 값으로 할당
        this.stockNumber = restStock;
    }

    // 상품의 재고를 증가시키는 메소드
    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }

}
