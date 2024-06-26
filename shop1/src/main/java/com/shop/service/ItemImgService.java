package com.shop.service;

import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ItemImgService {

    @Value("${itemImgLocation}") // @Value 어노테이션을 통해 application.properties 파일에 등록한 itemImgLocation 값을 불러와서 itemImgLocation 변수에 넣음
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {

        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        // 파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){

            // 사용자가 상품의 이미지를 등록했다면 저장할 경로, 파일의 이름, 파일의 바이트 배열을 파일 업로드 파라미터로 uploadFile 메소드를 호출
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            
            // 저장된 상품 이미지를 불러올 경로를 설정. 
            // 외부 리소스를 불러오는 urlPatterns 로 WebMvcConfig 클래스에서 "/images/**" 를 설정해줌
            // application.properties 에서 설정한 uploadPath 경로인 "C:/shop/" 아래 폴더에 이미지를 저장하므로 "/images/item" 를 붙혀줌
            imgUrl = "/images/item/" + imgName;
            
            log.info("imgUrl: " + imgUrl);
        }

        // 상품 이미지 정보 저장
        itemImg.updateItemImg(oriImgName, imgName, imgUrl); // imgName : 실제 로컬에 저장된 상품 이미지 파일의 이름
                                                            // oriImgName : 업로드 했던 상품 이미지 파일의 원래 이름
                                                            // imgUrl : 업로드 결과 로컬에 저장된 상품 이미지 파일을 불러오는 경로
        itemImgRepository.save(itemImg);
    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
        if(!itemImgFile.isEmpty()){ // 상품 이미지를 수정한 경우 상품 이미지를 업데이트
            // 상품 이미지 아이디를 이용하여 기존에 저장했던 상품 이미지 엔티티를 조회
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new);

            // 기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedItemImg.getImgName())){    // 기존에 등록된 상품 이미지 파일이 있을 경우 해당 파일을 삭제
                fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();

            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes()); // 업데이트한 상품 이미지 파일을 업로드

            String imgUrl = "/images/item/" + imgName;
            
            // 변경된 상품 이미지 정보를 세팅. 여기서 중요한 점은 상품 등록 때처럼 itemImgRepository.save() 로직을 호출하지 않음
            // savedItemImg 엔티티는 현재 영속 상태이므로 데이터를 변경하는 것만으로 변경 감지 기능이 동작하여 트랜잭션이 끝날 때 update 쿼리가 실행
            // 여기서 중요한 것은 엔티티가 영속 상태여야 한다는 것
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl); 
        }
    }


}
