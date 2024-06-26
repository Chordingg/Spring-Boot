package com.donation.api;

import com.donation.dto.CommentDto;
import com.donation.entity.Comment;
import com.donation.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    // 1. 댓글 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {

        // 서비스에 위임
        List<CommentDto> commentDtos = commentService.comments(articleId);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(commentDtos);

    }

    // 2. 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto commentDto) {

        // 서비스에 위임
        CommentDto createDto = commentService.create(articleId, commentDto);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createDto);

    }

    // 3. 댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto commentDto) {

        // 서비스에 위임
        CommentDto updateDto = commentService.update(id, commentDto);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }

    // 4. 댓글 삭제
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id) {

        // 서비스에 위임
        CommentDto deleteDto = commentService.delete(id);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(deleteDto);
    }

}
