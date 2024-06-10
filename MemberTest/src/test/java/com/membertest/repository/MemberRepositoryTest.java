package com.membertest.repository;

import com.membertest.entity.Member;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Log4j2
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testSample(){
        log.info("memberRepository : " + memberRepository);
    }


    @Test
    public void memberInsert(){
        for(int i=0; i<10; i++) {
            Member member = Member.builder()
                    .name("user" + i)
                    .age(i + 5)
                    .phone("010-5191-8611")
                    .address("봉담" + i)
                    .build();

            memberRepository.save(member);
        }
    }

    //단건 조회
    @Test
    public void memberGetOne(){

        Long memberId = 4L;

        //ctrl + alt+v
        Member member = memberRepository
                .findById(memberId)
                        .orElseThrow(() -> new EntityNotFoundException("Member not found"));
                //.orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found : " + memberId));

//        Optional<Member> result = memberRepository.findById(memberId);
//
//        Member member = result.get();

         log.info(member);

    }

    //전체데이터 가져오기
    @Test
    public void memberList(){
        List<Member> lists = memberRepository.findAll();

        lists.forEach(list -> log.info(list));
    }

    //전체 데이터 페이징 처리
    @Test
    public void memberPaging(){
        Pageable pageable = PageRequest.of(1,5);

        Page<Member> result = memberRepository.findAll(pageable);

        log.info("getTotalElements : " + result.getTotalElements());
        log.info("getTotalPages : " + result.getTotalPages());
        log.info("getSize : " + result.getSize());
        log.info("getContent : " + result.getContent());
        log.info("-------------------------------------------");
        result.stream().forEach(list -> log.info(list));
    }

    //삭제
    @Test
    public void memberDelete(){
        Long memberId =  13L;

                memberRepository.deleteById(memberId);
    }

    //업데이트
    @Test
    public void memberUpdate(){
        Member member = Member.builder()
                .name("뽀양")
                .age(7)
                .phone("010-5191-8611")
                .address("경기도 수원시 팔달구")
                .id(12L)
                .build();

        //저장할 때, 수정할 때 => save
        memberRepository.save(member);
    }
}