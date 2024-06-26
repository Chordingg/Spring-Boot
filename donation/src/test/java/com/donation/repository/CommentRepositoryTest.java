package com.donation.repository;

import com.donation.entity.Article;
import com.donation.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("테스트 결과에 보여줄 이름")
    void findByArticleId() {

        {
            // 1. 입력 데이터 준비
            Long articleId = 4L;

            // 2. 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 3. 예상 데이터
            Article article = new Article(4L, "당신의 인생 영화는?", "댓글 고");

            Comment a = new Comment(1L, article, "Park", "굿 윌 헌팅");
            Comment b = new Comment(2L, article, "Kim", "아이 엠 샘");
            Comment c = new Comment(3L, article, "Choi", "쇼생크 탈출");

            List<Comment> expected = Arrays.asList(a, b, c);

            // 4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 출력!");
        }

        // 1. 입력 데이터 준비
        Long articleId = 1L;

        // 2. 실제 데이터
        List<Comment> comments = commentRepository.findByArticleId(articleId);

        // 3. 예상 데이터
        Article article = new Article(1L, "가가가가", "1111");

        List<Comment> expected = Arrays.asList();

        // 4. 비교 및 검증
        assertEquals(expected.toString(), comments.toString(), "1번 글은 댓글이 없음!");


    }

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")
    void findByNickname() {

        // 1. 입력 데이터 준비
        String nickname = "Park";

        // 2. 실제 데이터
        List<Comment> comments = commentRepository.findByNickname(nickname);

        // 3. 예상 데이터
        Comment a = new Comment(2L, new Article(4L, "당신의 인생 영화는?", "댓글 고"),
                nickname, "굿 윌 헌팅");

        // 4. 비교 및 검증
//        assertEquals();
    }
}