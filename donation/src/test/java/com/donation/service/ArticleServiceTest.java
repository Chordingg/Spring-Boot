package com.donation.service;

import com.donation.dto.ArticleForm;
import com.donation.entity.Article;
import org.junit.jupiter.api.Test; // Test 패키지 임포트
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test // 해당 메서드가 테스트 코드임을 선언
    void index() {
        
        // 1. 예상 데이터
        Article a = new Article (1L,"가가가가", "1111");
        Article b = new Article (2L,"나나나나", "2222");
        Article c = new Article (3L,"다다다다", "3333");

        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c)); // a,b,c 합치기

        // 2. 실제 데이터
        List<Article> articles = articleService.index();

        // 3. 비교 및 검증
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공() {

        // 1. 예상 데이터
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");

        // 2. 실제 데이터
        Article article = articleService.show(id);

        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_실패() {

        // 1. 예상 데이터
        Long id = -1L;
        Article expected = null;

        // 2. 실제 데이터
        Article article = articleService.show(id);

        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void create() {

        // 1. 예상 데이터
        String title = "라라라라";
        String content = "4444";

        ArticleForm articleForm = new ArticleForm(null, title, content);
        Article article = new Article(4L,title, content);

        // 2. 실제 데이터
        Article created = articleService.create(articleForm);

        // 3. 비교 및 검증
        assertEquals(article.toString(), created.toString());
    }

    @Test
    void update() {

        // 1. 예상 데이터

        // 2. 실제 데이터

        // 3. 비교 및 검증
    }

    @Test
    void delete() {

        // 1. 예상 데이터

        // 2. 실제 데이터

        // 3. 비교 및 검증
    }
}