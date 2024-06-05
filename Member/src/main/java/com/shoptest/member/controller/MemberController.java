package com.shoptest.member.controller;

import com.shoptest.member.entity.Member;
import com.shoptest.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/list")
    public String list(Model model) {

        List<Member> memberList = memberService.memberAll();

        model.addAttribute("memberList", memberList);

        return "member/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("member", new Member());
        return "member/newForm";
    }

    @PostMapping(value = {"/new", "/edit"})
    public String newMember(Member member) {
        memberService.memberSave(member);
        return "redirect:/member/list";
    }

    @GetMapping("edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {

        Member member = memberService.memberOne(id);
        model.addAttribute("member", member);
        return "member/updateForm";
    }

    @PostMapping("delete/{id}")
    public String deleteMember(@PathVariable("id") Long id) {
        memberService.memberDelete(id);
        return "redirect:/member/list";
    }

    @GetMapping("/list2")
    public String list2(@RequestParam(name = "page", defaultValue = "0") int page ,
                        @RequestParam(name = "size", defaultValue = "5") int size ,
                        Model model) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Member> memberPage = memberService.memberPaging(pageable);

        model.addAttribute("memberPage", memberPage);

        return "member/list2";
    }
}
