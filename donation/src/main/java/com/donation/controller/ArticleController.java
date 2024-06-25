package com.donation.controller;

import com.donation.dto.ArticleForm;
import com.donation.entity.Article;
import com.donation.repository.ArticleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Log4j2
@Controller
public class ArticleController {

    @Autowired // 스프링 부트가 미리 생성해 놓은 Repository 객체 주입 (DI)
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
       return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm articleForm){

        log.info("--------------------------");
        log.info(articleForm);

        // 1. DTO 를 엔티티로 변환
        Article article = articleForm.toEntity();
        
        // 2. Repository 로 엔티티를 DB에 저장
        Article savedArticle = articleRepository.save(article);

        log.info(savedArticle);
        log.info("--------------------------");

        return "redirect:/articles/" + savedArticle.getId();
    }

    // 데이터 조회 요청 접수
    @GetMapping("/articles/{id}")
    public String show(@PathVariable("id") Long id, Model model){

        // 1. id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);

        // 3. 뷰 페이지 설정하기
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){

        // 1. 모든 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();

        // 2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList);

        // 3. 뷰 페이지 설정하기
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model){

        // 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);

        // 뷰 페이지 설정하기
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm articleForm){ // 매개변수로 DTO 받아오기

        // 1. DTO 를 Entity 로 변환하기
        Article articleEntity = articleForm.toEntity();

        // 2. Entity 를 DB에 저장하기
        // 2-1 DB 에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        
        // 2-2 기존 데이터 값을 갱신하기
        if(target != null){
            articleRepository.save(articleEntity); // Entity 를 DB 에 저장(갱신)
        }

        // 3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){

        // 1. 삭제할 대상 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 대상 Entity 삭제하기
        if(articleEntity != null){
            articleRepository.delete(articleEntity);
            redirectAttributes.addFlashAttribute("message", "정상적으로 삭제 되었습니다.");
        }

        // 3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }
    
}
