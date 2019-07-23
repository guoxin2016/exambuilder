package com.jason.exam.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jason.exam.model.common.R;
import com.jason.exam.service.api.ITopicService;

@Controller
@RequestMapping("/getK51Topic")
public class Getk51TopicController {

	@Autowired
	ITopicService topicService;

	// 获取章节练习
	@RequestMapping("chapter")
	@ResponseBody
	public R chapter(@RequestParam Map<String, String> params) {
		try {
			String planId = params.get("planUuid");
			topicService.getK51ChapterTopic(planId);

			return R.isOk();
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
	}


}
