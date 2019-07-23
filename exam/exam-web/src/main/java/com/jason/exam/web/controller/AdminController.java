package com.jason.exam.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jason.exam.model.Account;
import com.jason.exam.model.common.Pager;
import com.jason.exam.model.query.AccountQuery;
import com.jason.exam.service.IAccountService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
    @Autowired
    IAccountService accountService; 
	
	@RequestMapping(value = "/listview")
    public String listview() {
		
        return "/admin/adminlist";
    }
	
	@RequestMapping(value = "/listdata")
	@ResponseBody
    public Pager listdata(Integer draw,Integer start,Integer length,AccountQuery accountQuery) {
		
		int page = (start-1)/length;
		
		Page<Account> datas = accountService.findAccountCriteria(page, length, accountQuery);
		Pager p = Pager.isOk();
		p.setDraw(draw);
		p.setData(datas.getContent());
		p.setRecordsTotal(datas.getTotalElements());
		p.setRecordsFiltered(datas.getTotalElements());
		return p;
    }
	
}
