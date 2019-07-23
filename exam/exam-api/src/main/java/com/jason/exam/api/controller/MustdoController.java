package com.jason.exam.api.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jason.exam.model.common.Pager;
import com.jason.exam.model.common.R;
import com.jason.exam.service.IMenuService;
import com.jason.exam.service.api.IChapterService;
import com.jason.exam.service.api.IMustdoService;


@Controller
@RequestMapping("mustdo")
public class MustdoController
{
    @Autowired
    IMustdoService mustdoService;

    /**
     * 章节菜单
     */
    @RequestMapping("papers")
	@ResponseBody
    public R papers(@RequestParam Map<String, String> params)
    {
    	try {
			String planId = params.get("planUuid");
			List<Map<String, Object>> menuChapters= mustdoService.getMustdoMenuByPlanId(planId);
			return R.isOk().data(menuChapters);
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
    }
    
    /**
     * 根据章节获取题目 分页
     */
    @RequestMapping("topics")
	@ResponseBody
    public Pager topics(@RequestParam Map<String, String> params)
    {
    	try {
    		String userUuid = params.get("userUuid");
    		String sessionId = params.get("sessionId");
    		boolean isLogin = true;
    		if(StringUtils.isBlank(userUuid) || StringUtils.isBlank(sessionId)) {
    			isLogin = false;
    			params.put("pageNumber", "0");
    			params.put("pageSize", "10");
    		}
    		Page<Map<String, Object>> datas= mustdoService.getTopicByMustdoMenuId(params);
    		Pager p = Pager.isOk();
    		p.setData(datas.getContent());
    		p.setRecordsTotal(isLogin?datas.getTotalElements():10);
			return p;
		} catch (Exception e) {
			e.printStackTrace();
			return Pager.isFail();
		}
    }
    
    
    
}
