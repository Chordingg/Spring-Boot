package com.shoptest.member.repository;

import com.shoptest.member.entity.Member;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testRepository(){
        log.info("MemberRepository : " + memberRepository);
    }

    @Test
    public void testRegister(){
        Member member = Member.builder()
                .name("몽이")
                .age(2)
                .address("경기도 안성시")
                .phone("010-1672-9041")
                .build();
        memberRepository.save(member);
    }

    @Test
    public void testUpdate(){
        Member member = Member.builder()
                .name("몽이")
                .age(3)
                .address("경기도 수원시")
                .phone("010-5814-1489")
                .id(2L)
                .build();
        memberRepository.save(member);
    }

    @Test
    public void testDelete(){
        memberRepository.deleteById(2L);
    }

    @Test
    public void testFindOne(){
        Member member = memberRepository.findById(3L).orElseThrow(() -> new EntityNotFoundException("Member not found"));
    }

    @Test
    public void testFindAll(){
        List<Member> members = memberRepository.findAll();
    }

    @Test
    public void testMemberPaging(){
        Pageable pageable = PageRequest.of(1,5);

        Page<Member> memberPage = memberRepository.findAll(pageable);

        log.info("----------------------------------------");
        log.info("memberPage : " + memberPage);
        log.info("getTotal : " + memberPage.getTotalElements());
        log.info("getTotalPages : " + memberPage.getTotalPages());
        log.info("getNumber : " + memberPage.getNumber());
        log.info("getSize : " + memberPage.getSize());
        log.info("----------------------------------------");
    }
}