package com.jason.exam.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author guoxin 题目类型
 */
@Entity
@Table(name = "tb_topic_chapter")
public class TopicChapter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3347038254765033912L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title; // 题目标题
	private String uuid; // uuid
	private String analysis; // 分析
	private String score; // 分值
	private String answer; // 答案
	private String answer_real; // 答案
	private String type; // 类型
	private String type_tow;           //题目类型
	private String title_parent; //       公共题干
	private String select_answer; //     选择选项  
	private String select_answer_real; //     选择选项
	private String plan_catalog_uuid; // 题目分类 uuid
	private Date create_time; // 创建时间
	private int status; // 状态 1有效 0 无效
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getAnalysis() {
		return analysis;
	}
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType_tow() {
		return type_tow;
	}
	public void setType_tow(String type_tow) {
		this.type_tow = type_tow;
	}
	public String getTitle_parent() {
		return title_parent;
	}
	public void setTitle_parent(String title_parent) {
		this.title_parent = title_parent;
	}
	public String getSelect_answer() {
		return select_answer;
	}
	public void setSelect_answer(String select_answer) {
		this.select_answer = select_answer;
	}
	public String getPlan_catalog_uuid() {
		return plan_catalog_uuid;
	}
	public void setPlan_catalog_uuid(String plan_catalog_uuid) {
		this.plan_catalog_uuid = plan_catalog_uuid;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public String getAnswer_real() {
		return answer_real;
	}

	public void setAnswer_real(String answer_real) {
		this.answer_real = answer_real;
	}

	public String getSelect_answer_real() {
		return select_answer_real;
	}

	public void setSelect_answer_real(String select_answer_real) {
		this.select_answer_real = select_answer_real;
	}
}
