package com.donation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 컨트롤러 선언
public class FirstController {

    @GetMapping("/hi") // URL 요청 접수
    public String niceToMeetYou(Model model){
        model.addAttribute("username", "팍팍");
        return "greetings";
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("username", "홍길동");
        return "goodbye";
    }
}
