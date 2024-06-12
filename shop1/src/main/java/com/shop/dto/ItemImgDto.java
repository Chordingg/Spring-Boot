package com.shop.dto;

import com.shop.entity.ItemImg;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Log4j2
public class ItemImgDto {

    private Long id;

    private String imgName; //이미지명

    private String oriImgName; //원본이미지명

    private String imgUrl; //이미지 경로

    private String repimgYn; //대표이미지(이미지가 여러장일 때 , 메인페이지에서 보이는 이미지)

    private static ModelMapper modelMapper = new ModelMapper(); // 멤버 변수로 ModelMapper 객체 추가

    // ItemImg 엔티티를 받아서 ItemImgDto 변환
    public static ItemImgDto itemImgOfItemImgDto(ItemImg itemImg) {
        // ItemImg 엔티티 객체를 파라미터로 받아서 ItemImg 객체의 자료형과 멤버변수의 이름이 같을 때 ItemImgDto 로 값을 복사해서 반환
        // static 메소드로 선언해 ItemImgDto 객체를 생성하지 않아도 호출할 수 있도록 함

        log.info("-------------------------------");
        log.info(">>>>>>>>>>>>>>" + itemImg);
        log.info(">>>>>>>>>>>>>>>" + modelMapper.map(itemImg, ItemImg.class));
        return modelMapper.map(itemImg,ItemImgDto.class);
    }
}
