package com.donation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecondController {

    @GetMapping("/random-quote")
    public String randomQuote(Model model) {
        String[] quotes = {
                "피할 수 없으면 즐겨라 – 로버트 엘리엇",
                "먼저 핀 꽃은 먼저 진다 남보다 먼저 공을 세우려고 조급히 서둘 것이 아니다 – 채근담",
                "행복은 습관이다, 그것을 몸에 지니라 -허버드",
                "성공의 비결은 단 한 가지, 잘할 수 있는 일에 광적으로 집중하는 것이다.- 톰 모나건"
        };

        int randomInt = (int) (Math.random() * quotes.length);
        model.addAttribute("randomQuote", quotes[randomInt]);
        return "quote";
    }
}
