package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> , QuerydslPredicateExecutor<Item> {

    Item findByItemNm(String itemNm);
    //    Item findByItemDetail(String itemDetail);
//    Item findByItemNmAndItemDetail(String itemName, String itemDetail);

    List<Item> findByPriceLessThan(int price);
    List<Item> findByPriceLessThanOrderByPriceDesc(int price);

    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price asc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    @Query("select i from Item i where i.itemNm like %:itemNm and i.itemDetail like %:itemDetail")  // 두 조건을 동시에 만족하는 식
    List<Item> findByItemNmAndItemDetail(@Param("itemNm") String itemNm,@Param("itemDetail") String itemDetail);

    @Query(value = "select * from item where item_detail like %:itemDetail% order by price desc" , nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
}
