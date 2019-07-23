package com.jason.exam.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jason.exam.model.common.R;
import com.jason.exam.service.api.IPlanService;

@Controller
@RequestMapping("/plan")
public class PlanController {
	
	@Autowired
	IPlanService planService;
	
	// 获取章节练习
	@RequestMapping("list")
	@ResponseBody
	public R list(@RequestParam Map<String, String> params) {
		try {
			String appUuid = params.get("appUuid");
			List<Map<String, Object>> list = planService.findByAppUuid(appUuid);
			return R.isOk().data(list);
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
	}
	


}
