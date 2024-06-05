package com.shop.repository;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
//@Transactional
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;


    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        Item item = new Item();

        item.setItemNm("라면");
        item.setPrice(1500);
        item.setItemDetail("라면 상세 설명2");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        Item savedItem = itemRepository.save(item);
        log.info(savedItem);
    }

    @Test
    @DisplayName("id번 검색")
    public void findByIdTest() {
        Optional<Item> result = itemRepository.findById(5L);

        Item item = result.get();

        log.info(item);
    }

    @Test
    @DisplayName("라면 검색")
    public void findByNameTest() {
        Item item = itemRepository.findByItemNm("라면");
        log.info("라면: " + item);
    }

    @Test
    @DisplayName("라면 상세")
    public void findByItemDetailTest() {
        List<Item> item = itemRepository.findByItemDetail("라면");

        item.forEach(list -> log.info(list));
    }

    @Test
    @DisplayName("라면 & 라면 상세 설명")
    public void findByItemNmAndItemDetail() {
        List<Item> item = itemRepository.findByItemNmAndItemDetail("라면","라면 상세 설명");

        item.forEach(list -> log.info(list));
    }

    @Test
    @DisplayName("price가 30000미만 검색")
    public void findByPriceTest(){
        List<Item> list = itemRepository.findByPriceLessThan(30000);
        list.forEach(result->log.info(result));
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDesc(){
        this.createItemTest();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(30000);
        for(Item item : itemList){
            log.info(item);
        }
    }

    @Test
    @DisplayName("findByItemDetailByNative")
    public void findByItemDetailByNative(){
        List<Item> items = itemRepository.findByItemDetailByNative("라면");
        items.forEach(result -> log.info(result));
    }
}