package com.syd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @PostMapping("/success")
    public String success(){
        return "success";
    }

    @PostMapping("/fail")
    public String fail() {
        return "fail";
    }

    @GetMapping("/find")
    @ResponseBody
    public String find(){
        return "find";
    }

    @GetMapping("/findAll")
    @ResponseBody
    public String findAll(){
        return "findAll";
    }

    @GetMapping("/unauth")
    public String accessDenyPage(){
        return "unauth";
    }
}