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
 * @author guoxin 模拟题目
 */
@Entity
@Table(name = "tb_topic_simulation")
public class TopicSimulation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1526131147503625697L;
	
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
	private Date create_time; // 创建时间
	
	private String simulation_uuid; // 题目分类 uuid
	private int status; 		// 状态
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
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getSimulation_uuid() {
		return simulation_uuid;
	}
	public void setSimulation_uuid(String simulation_uuid) {
		this.simulation_uuid = simulation_uuid;
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
