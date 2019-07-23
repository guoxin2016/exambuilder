package com.jason.exam.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jason.exam.model.common.R;
import com.jason.exam.service.api.ITopicService;
/**
 * 获取建校题库
 * @author guoxin
 *
 */
@Controller
@RequestMapping("/getJHTopic")
public class GetJHTopicController {

	@Autowired
	ITopicService topicService;
	
	
	//标准员-----2a212315ab44473b945113d3525c08a3
	//材料员-----02ce774e880c4024b075ecbbcbfbd9c3
	//机械员-----5b94bda0621146329a3688f0c862b744
	
	// 获取章节练习
	@RequestMapping("chapter")
	@ResponseBody
	public R chapter(@RequestParam Map<String, String> params) {
		try {
			String planId = params.get("planUuid");
			String filename = params.get("filename");
			topicService.getJHChapterTopic(planId,filename);

			return R.isOk();
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
	}


}
