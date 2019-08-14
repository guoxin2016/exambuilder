package com.jason.exam.api.controller;

import java.util.List;
import java.util.Map;

import com.jason.exam.service.api.IUserService;
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

	@Autowired
	IUserService userService;

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
			String planUuid = params.get("planUuid");
			String pageNumber = params.get("pageNumber");
			String pageSize = params.get("pageSize");
//    		boolean isLogin = true;
//    		if(StringUtils.isBlank(userUuid) || StringUtils.isBlank(sessionId)) {
//    			isLogin = false;
//    			params.put("pageNumber", "0");
//    			params.put("pageSize", "10");
//    		}
			String vip_level = "-1";
			if(StringUtils.isNotBlank(userUuid) && StringUtils.isNotBlank(sessionId)  && StringUtils.isNotBlank(planUuid)) {
				//获取用户最新权限
				Map<String, Object> temp = userService.getVipInfo(userUuid,planUuid);
				vip_level = String.valueOf(temp.get("vip_level"));
			}
			if(!"1".equals(vip_level)) {
				pageNumber = "0";
				pageSize = "10";
				params.put("pageNumber",pageNumber);
				params.put("pageSize",pageSize);
			}
    		Page<Map<String, Object>> datas= mustdoService.getTopicByMustdoMenuId(params);
    		Pager p = Pager.isOk();
    		p.setData(datas.getContent());
//    		p.setRecordsTotal(isLogin?datas.getTotalElements():10);
			p.setRecordsTotal("1".equals(vip_level)?datas.getTotalElements():10);
			return p;
		} catch (Exception e) {
			e.printStackTrace();
			return Pager.isFail();
		}
    }
    
    
    
}
