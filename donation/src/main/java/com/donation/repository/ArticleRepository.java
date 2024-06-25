package com.donation.repository;

import com.donation.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    
    @Override
    ArrayList<Article> findAll(); // Iterable -> ArrayList 수정
}
