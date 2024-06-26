package com.donation.dto;

import com.donation.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor // 매개변수 생성자 자동 생성
public class ArticleForm {

    private Long id;
    private String title;
    private String content;

    // 전송받은 제목과 내용을 필드에 저장하는 생성자 추가
//    public ArticleForm(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }

    public Article toEntity() {
        return new Article(id, title, content);
    }
}
