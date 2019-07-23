package com.jason.exam.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jason.exam.model.Account;
import com.jason.exam.model.common.R;

@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/{id}") 
    public String  getUser(@PathVariable Integer id,Model model) {
        
		Account a = new Account();
		a.setAccountname("张三");
        model.addAttribute("user",a);
        return "/user/detail";
    }
    
	@RequestMapping("list1")
	@ResponseBody
    public R list1(@RequestParam Map<String, String> params) {
		System.out.println(params.get("channelid"));
		System.out.println(params);
        try {
        	Account a = new Account();
    		a.setAccountname("张三");
    		System.out.println("111");
            return R.isOk().data(a);
        } catch (Exception e) {
            return R.isFail(e);
        }
    }
	@RequestMapping("list")
	@ResponseBody
    public String list() {
        return "success";
    }
	
}
