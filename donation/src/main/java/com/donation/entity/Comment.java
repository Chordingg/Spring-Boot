package com.donation.entity;

import com.donation.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 대표키

    @ManyToOne // Comment 엔티티와 Article 엔티티를 다대일 관계로 설정
    @JoinColumn(name = "article_id") // 외래키 생성, Article 엔티티의 기본키(id)와 매핑
    private Article article; // 해당 댓글의 부모 게시글

    @Column
    private String nickname; // 댓글을 단 사람

    @Column
    private String body; // 댓글 본문

    public static Comment createComment(CommentDto commentDto, Article article) {

        // 예외 발생
        if(commentDto.getId() != null){
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        }
        if(commentDto.getArticleId() != article.getId()){
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못됐습니다.");
        }

        return new Comment(
                commentDto.getId(),
                article,
                commentDto.getNickname(),
                commentDto.getBody()
        );
    }

    public void patch(CommentDto commentDto) {

        // 예외 발생
        if(this.id != commentDto.getId()){
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다.");
        }
        if(commentDto.getNickname() != null){ // 수정할 닉네임 데이터가 있다면
            this.nickname = commentDto.getNickname(); // 내용 반영
        }
        if(commentDto.getBody() != null){ // 수정할 본문 데이터가 있다면
            this.body = commentDto.getBody(); // 내용 반영
        }
    }
}
