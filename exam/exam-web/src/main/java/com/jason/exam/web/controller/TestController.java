package com.jason.exam.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public R list1() {
        try {
        	Account a = new Account();
    		a.setAccountname("张三");
            return R.isOk().data(a);
        } catch (Exception e) {
            return R.isFail(e);
        }
    }
	@RequestMapping("list")
    public String list(Model model) {
		List<Account> accountList = new ArrayList<Account>();
    	Account a = new Account();
		a.setAccountname("张三");
		accountList.add(a);
		model.addAttribute("accounts", accountList);
        return "/test/list";
    }
	
	@RequestMapping("login")
    public String login() {
		List<Account> accountList = new ArrayList<Account>();
    	Account a = new Account();
		a.setAccountname("张三");
		accountList.add(a);
        return "/login";
    }

}
