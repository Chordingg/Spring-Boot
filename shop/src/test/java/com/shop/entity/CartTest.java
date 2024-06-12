package com.shop.entity;

import com.shop.dto.MemberFormDTO;
import com.shop.repository.CartRepository;
import com.shop.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class CartTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EntityManager entityManager;

    public Member createMember(){
        MemberFormDTO memberFormDTO = MemberFormDTO.builder()
                .email("test4")
                .address("서울시 마포구 합정동")
                .password("1234")
                .build();

        return Member.createMember(memberFormDTO, passwordEncoder);
    }

    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
    public void findCartAndMember(){
        Member member = createMember();
        memberRepository.save(member);

        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

//        entityManager.flush();
//        entityManager.clear();

        // 저장된 장바구니 엔티티를 조회
        Cart savedCart = cartRepository.findById(cart.getId()).orElseThrow(()-> new EntityNotFoundException());

        // 처음에 저장한 member 엔티티의 id 와 savedCart 에 매핑된 member 엔티티의 id 를 비교
        
        assertEquals(savedCart.getMember().getId(), member.getId());

    }
}