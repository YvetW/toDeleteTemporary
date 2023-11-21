package com.shiyu.web01.controller;

import com.shiyu.web01.model.vo.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MemberController.BASE_URL)
public class MemberController {
    public static final String BASE_URL = "/member";

    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable("id") String id) {
        Member member = new Member();
        member.setId(id);
        member.setName("shiyu + tag v3.0.4");
        return member;
    }
}
