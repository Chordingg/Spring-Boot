package com.shop.service;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemFormDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    List<MultipartFile> createMultipartFiles() throws Exception{ // MockMultipartFile 클래스를 이용하여 가짜 MultipartFile 리스트를 만들어서 반환하는 메소드

        List<MultipartFile> multipartFiles = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String path = "C:/shop/item/";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile MultipartFile = new MockMultipartFile(path, imageName, "/image/jpg", new byte[]{1,2,3,4});
            multipartFiles.add(MultipartFile);
        }
        return multipartFiles;
    }

    @Test
    @DisplayName("상품 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveItem() throws Exception{
        ItemFormDto itemFormDto = ItemFormDto.builder() // 상품 등록 화면에서 입력 받는 상품 데이터를 세팅
                .itemNm("테스트 상품")
                .itemSellStatus(ItemSellStatus.SELL)
                .itemDetail("테스트 상품 입니다.")
                .price(1000)
                .stockNumber(100)
                .build();

        List<MultipartFile> multipartFiles = createMultipartFiles();

        Long itemId = itemService.saveItem(itemFormDto, multipartFiles); // 상품 데이터와 이미지 정보를 파라미터로 넘겨서 저장 후 상품의 아이디 값을 반환 값으로 리턴

        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        Item item = itemRepository.findById(itemId).orElseThrow(()-> new EntityNotFoundException());

        assertEquals(itemFormDto.getItemNm(), item.getItemNm());
        assertEquals(itemFormDto.getItemSellStatus(), item.getItemSellStatus());
        assertEquals(itemFormDto.getItemDetail(), item.getItemDetail());
        assertEquals(itemFormDto.getPrice(), item.getPrice());
        assertEquals(itemFormDto.getStockNumber(), item.getStockNumber());
        assertEquals(multipartFiles.get(0).getOriginalFilename(), itemImgList.get(0).getOriImgName());

    }
}