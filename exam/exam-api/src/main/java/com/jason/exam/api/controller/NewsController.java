package com.jason.exam.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jason.exam.model.common.Pager;
import com.jason.exam.service.api.INewsService;


@Controller
@RequestMapping("news")
public class NewsController
{
    @Autowired
    INewsService newsService;

    /**
     * 获取咨询 分页
     */
    @RequestMapping("list")
	@ResponseBody
    public Pager topics(@RequestParam Map<String, String> params)
    {
    	try {
    		Page<Map<String, Object>> datas= newsService.getNewsByPlanUuId(params);
    		Pager p = Pager.isOk();
    		p.setData(datas.getContent());
    		p.setRecordsTotal(datas.getTotalElements());
			return p;
		} catch (Exception e) {
			e.printStackTrace();
			return Pager.isFail();
		}
    }
    
}
