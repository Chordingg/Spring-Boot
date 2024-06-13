package com.shop.service;

import com.shop.dto.ItemFormDto;
import com.shop.dto.ItemImgDto;
import com.shop.dto.ItemSearchDto;
import com.shop.dto.MainItemDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import com.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        // 상품 등록
        Item item = itemFormDto.createItem();   // 상품 등록 폼으로부터 입력 받은 데이터를 이용하여 item 객체 생성
        itemRepository.save(item);  // 상품 데이터를 저장

        // 이미지 등록
        for(int i=0; i<itemImgFileList.size(); i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            if(i == 0){
                itemImg.setRepimgYn("Y"); // 첫 번째 이미지일 경우 대표 상품 이미지 여부 값을 "Y"로 세팅
            }else {
                itemImg.setRepimgYn("N"); // 나머지는 "N" 로 세팅
            }
            
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i)); // 상품의 이미지 정보를 저장
        }
        return item.getId();
    }


    @Transactional(readOnly = true) // 상품 데이터를 읽어오는 트랜잭션을 읽기 전용 설정. 이럴 경우 JPA 가 더티체킹(변경감지)을 수행하지 않아서 성능 향상
    public ItemFormDto getItemDtl(Long itemId) {

        // 해당 상품의 이미지를 조회. 오름차순으로 가져옴
        List<ItemImg> itemImgList  = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        // ItemImg(상품 이미지) -> ItemImgDto 전달
        for(ItemImg itemImg : itemImgList){
            ItemImgDto itemImgDto = ItemImgDto.itemImgOfItemImgDto(itemImg);
            itemImgDtoList.add(itemImgDto);

        }

        // Item(상품정보) -> ItemFormDto 전달
        Optional<Item> result = itemRepository.findById(itemId);
        Item item = result.orElseThrow(EntityNotFoundException::new);

        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);

        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        // 상품 등록 화면으로부터 전달 받은 상품 아이디를 이용하여 상품 엔티티를 조회
        Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new); 

        // 상품 등록 화면으로부터 전달 받는 itemFormDto 를 통해 상품 엔티티 업데이트
        item.updateItem(itemFormDto);

        // 상품 이미지 아이디 리스트를 조회
        List<Long> itemImgIds = itemFormDto.getItemImgIds(); 

        // 이미지 등록
        for(int i=0; i<itemImgFileList.size(); i++){
            // 상품 이미지를 업데이트하기 위해서 updateItemImg() 메소드에 상품 이미지 아이디와, 상품 이미지 파일 정보를 파라미터로 전달
            itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
        }
        return item.getId();
    }

    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto,Pageable pageable) {
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }
}
