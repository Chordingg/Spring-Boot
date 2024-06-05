package com.shoptest.member.service;

import com.shoptest.member.entity.Member;
import com.shoptest.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@RequiredArgsConstructor
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;

    public void memberSave(Member member) {
        memberRepository.save(member);
    }

    public void memberDelete(Long member_id) {
        memberRepository.deleteById(member_id);
    }

    public Member memberOne(Long member_id) {

        Member member = memberRepository.findById(member_id).orElseThrow(()-> new EntityNotFoundException("Member not found"));

        return member;
    }

    public List<Member> memberAll(){
        return memberRepository.findAll();
    }

    public Page<Member> memberPaging(Pageable pageable){
        return memberRepository.findAll(pageable);
    }
}
