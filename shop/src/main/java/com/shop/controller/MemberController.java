package com.shop.controller;

import com.shop.dto.MemberFormDTO;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/new")
    public String newMember(Model model) {
        model.addAttribute("memberFormDTO", new MemberFormDTO());

        return "member/memberForm";
    }

    // Post -> Redirect -> Get (PRG방식)
    @PostMapping("/new")
    public String memberForm(@Valid MemberFormDTO memberFormDTO, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            return "member/memberForm";
        }
        try{
            //MemberFormDTO >>> Member Entity 변환
            Member member = Member.createMember(memberFormDTO, passwordEncoder);

            //member 객체 저장(회원 저장)
            memberService.saveMember(member);

        }catch (IllegalStateException e){    // 중복 회원 입력 시 예외 처리
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginMember() {
        return "member/memberLoginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "member/memberLoginForm";
    }
}
