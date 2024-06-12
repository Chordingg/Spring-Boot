package com.shop.controller;

import com.shop.dto.ItemFormDto;
import com.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/admin/item/new")
    public String itemForm(Model model){
         model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemForm";
    }

    @PostMapping("/admin/item/new")
    public String ItemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model
                        , @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){

        log.info("====================================");
        log.info("itemFormDto: " + itemFormDto);
        itemImgFileList.forEach(list -> log.info(list));
        log.info("====================================");

        if(bindingResult.hasErrors()){  // 상품 등록 시 필수 값이 없다면 다시 상품 등록 페이지로 전환
            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){    // 상품 등록 시 첫 번째 이미지가 없다면 에러 메시지와 함께 상품 등록 페이지로 전환
                                                                                // 상품의 첫 번째 이미지는 메인 페이지에서 보여줄 상품 이미지로 사용하기 위해서 필수 값으로 지정함
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/itemForm";
        }

        try{
            itemService.saveItem(itemFormDto, itemImgFileList); // 상품 저장 로직을 호출. 매개 변수로 상품 정보와 상품 이미지 정보를 담고 있는 itemImgFileList 를 넘겨줌
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }
        return "redirect:/";    // 상품이 정상적으로 등록되었다면 메인 페이지로 이동
    }

    @GetMapping(value = "/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model){

        try{
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId); // 조회한 상품 데이터를 모델에 담아서
            model.addAttribute("itemFormDto", itemFormDto); // 뷰로 전달

        }catch (Exception e){   // 상품 엔티티가 존재하지 않을 경우 에러메시지를 담아서 상품 등록 페이지로 이동
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "item/itemForm";
        }

        return "item/itemForm";
    }
}
