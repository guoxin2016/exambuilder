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
import com.jason.exam.service.api.IChapterService;


@Controller
@RequestMapping("chapter")
public class ChapterController
{
    @Autowired
    IChapterService chapterService;

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
			List<Map<String, Object>> menuChapters= chapterService.getChapterMenuByPlanId(planId);
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
    		Page<Map<String, Object>> datas= chapterService.getTopicByChapterMenuId(params);
    		Pager p = Pager.isOk();
    		p.setData(datas.getContent());
    		p.setRecordsTotal("1".equals(vip_level)?datas.getTotalElements():10);
			return p;
		} catch (Exception e) {
			e.printStackTrace();
			return Pager.isFail();
		}
    }
    
    /**
     * 易错随机练习
     */
    @RequestMapping("errorprone")
	@ResponseBody
    public R errorProneProblem(@RequestParam Map<String, String> params)
    {
    	try {
    		List<Map<String, Object>> topics = chapterService.getErrorProneProblemTopic(params);
			return R.isOk().data(topics);
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
    }
    
    /**
     * 精选随机练习
     */
    @RequestMapping("selected")
	@ResponseBody
    public R selected(@RequestParam Map<String, String> params)
    {
    	try {
    		List<Map<String, Object>> topics = chapterService.getSelectedTopic(params);
			return R.isOk().data(topics);
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
    }
    
    
    
    
}
