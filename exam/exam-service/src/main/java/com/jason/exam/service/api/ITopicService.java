package com.jason.exam.service.api;

public interface ITopicService {
	/**
	 * 章节练习
	 * 
	 * @param planId
	 */
	void getChapterTopic(String planId);

	/**
	 * 真题
	 * 
	 * @param planId
	 */
	void getoldExamTopic(String planId);

	/**
	 * 模拟题
	 * 
	 * @param planId
	 */
	void getSimulationTopic(String planId);

	/**
	 * 必做题
	 * @param planId
	 */
	void getMustdoTopic(String planId);

	/**
	 * k51 八大员章节练习
	 * @param planId
	 */
	void getK51ChapterTopic(String planId);

	/**
	 * 获取建校八大员题库
	 * @param planId
	 */
	void getJHChapterTopic(String planId,String filename);
}
