package com.donation.service;

import com.donation.dto.ArticleForm;
import com.donation.entity.Article;
import com.donation.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository; // 게시글 Repository 객체 주입

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm articleForm){
        Article article = articleForm.toEntity(); // DTO -> Entity 로 변환 후 article 에 저장

        if(article.getId() != null){
            return null;
        }
        return articleRepository.save(article); // article 을 DB 에 저장
    }

    public Article update(Long id, ArticleForm articleForm){

        // 1. DTO -> Entity 변환
        Article article = articleForm.toEntity();

        // 2. 타깃 조회하기
        Article targerArticle = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리하기
        if(targerArticle == null || id != article.getId()){
            // 400, 잘못된 요청 응답!
            return null;

        }
        // 4. 업데이트 및 정상 응답(200)
        targerArticle.patch(article);

        Article updatedArticle = articleRepository.save(targerArticle);

        return updatedArticle;
    }

    public Article delete(Long id) {

        // 1. 대상 찾기
        Article article = articleRepository.findById(id).orElse(null);

        // 2. 잘못된 요청 처리하기
        if (article == null) {
            return null;
        }
        // 3. 대상 삭제하기
        articleRepository.delete(article);

        return article;

    }

    public List<Article> createArticles(List<ArticleForm> articleForms){

        // 1. DTO 묶음을 Entity 묶음으로 변환
        List<Article> articleList = articleForms.stream()
                // map() 으로 DTO 가 하나하나 올 때마다 articleForms.toEntity() 를 수행해 매핑
                .map(dto -> dto.toEntity())
                
                // 매핑한 것을 리스트로 묶음
                .collect(Collectors.toList());

        // 2. Entity 묶음을 DB 에 저장
        articleList.stream() // articleList 를 스트림화
                .forEach(article -> articleRepository.save(article));

        // 3. 강제 예외 발생시키기
        articleRepository.findById(-1L)
                .orElseThrow(() -> new IllegalArgumentException("결제 실패"));

        // 4. 결과 값 반환
        return articleList;
    }

}

