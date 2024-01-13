package com.example.basicfunctions01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class controller {
    public controller(){
        System.out.println("到此一遊");
    }
    @RequestMapping("/ss")
    public String index() {
        System.out.println("到此一遊2");
        return "";
    }
}
