package com.shop.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log4j2
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID(); // UUID(Universally Unique Identifier) 는 서로 다른 개체들을 구별하기 위해서 이름을 부여할 때 사용
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension; // UUID 로 받은 값과 원래 파일의 이름의 확장자를 조합해서 저장된 파일 이름을 만듬
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData); // fileData 를 파일 출력 스트림에 입력
        fos.close();

        log.info("===================================");
        log.info("uuid : " + uuid);
        log.info("extension : " + extension);
        log.info("savedFileName : " + savedFileName);
        log.info("fileUploadFullUrl : " + fileUploadFullUrl);
        log.info("===================================");

        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);
        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }

}
