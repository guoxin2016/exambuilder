package com.jason.exam.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.jason.exam.model.common.R;
import com.jason.exam.service.api.ITopicService;

@Controller
@RequestMapping("/getTopic")
public class GetTopicController {

	@Autowired
	ITopicService topicService;

	// 获取章节练习
	@RequestMapping("chapter")
	@ResponseBody
	public R chapter(@RequestParam Map<String, String> params) {
		try {
			String planId = params.get("planUuid");
			topicService.getChapterTopic(planId);

			return R.isOk();
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
	}

	// 获取真题
	@RequestMapping("oldexam")
	@ResponseBody
	public R oldExam(@RequestParam Map<String, String> params) {
		try {
			String planId = params.get("planUuid");
			topicService.getoldExamTopic(planId);

			return R.isOk();
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
	}

	// 获取模拟题
	@RequestMapping("simulation")
	@ResponseBody
	public R simulation(@RequestParam Map<String, String> params) {
		try {
			String planId = params.get("planUuid");
			topicService.getSimulationTopic(planId);

			return R.isOk();
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
	}

	// 获取必做题
	@RequestMapping("mustdo")
	@ResponseBody
	public R mustdo(@RequestParam Map<String, String> params) {
		try {
			String planId = params.get("planUuid");
			topicService.getMustdoTopic(planId);

			return R.isOk();
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
	}

}
