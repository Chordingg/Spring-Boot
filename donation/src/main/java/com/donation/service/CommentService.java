package com.donation.service;

import com.donation.dto.CommentDto;
import com.donation.entity.Article;
import com.donation.entity.Comment;
import com.donation.repository.ArticleRepository;
import com.donation.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {

        // 1. 댓글 조회
        List<Comment> comments = commentRepository.findByArticleId(articleId);

        // 2. Entity -> DTO 변환
        List<CommentDto> commentDtos = new ArrayList<CommentDto>();

        for(int i = 0; i < comments.size(); i++){
            Comment comment = comments.get(i);
            CommentDto commentDto = CommentDto.createCommentDto(comment);
            commentDtos.add(commentDto);
        }

        // 3. 결과 반환
        return commentRepository.findByArticleId(articleId) // 댓글 엔티티 목록 조회
                .stream() // 댓글 엔티티 목록을 스트림으로 변환
                .map(comment -> CommentDto.createCommentDto(comment)) // 엔티티를 DTO 로 매핑
                .collect(Collectors.toList()); // 스트림을 리스트로 변환
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto commentDto) {

        // 1. 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패!" + "대상 게시글이 없습니다."));

        // 2. 댓글 엔티티 생성
        Comment comment = Comment.createComment(commentDto, article);

        // 3. 댓글 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);

        // 4. DTO 로 변환해 변환
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto commentDto) {
        
        // 1. 댓글 조회 및 예외 발생
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다.")); // 수정할 댓글 가져오기
        
        // 2. 댓글 수정
        comment.patch(commentDto);
        
        // 3. DB로 갱신
        Comment updated = commentRepository.save(comment);
        
        // 4. 댓글 엔티티를 DTO 로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {

        // 1. 댓글 조회 및 예외 발생
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 대상이 없습니다."));

        // 2. 댓글 삭제
        commentRepository.delete(comment);

        // 3. 삭제 댓글을 DTO 로 변환 및 반환
        return CommentDto.createCommentDto(comment);
    }
}
