package com.jason.exam.service.api.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jason.exam.common.util.UUIDTool;
import com.jason.exam.dao.repository.api.OldExamRepository;
import com.jason.exam.dao.repository.api.PlanCatalogRepository;
import com.jason.exam.dao.repository.api.SimulationRepository;
import com.jason.exam.dao.repository.api.TopicChapterRepository;
import com.jason.exam.dao.repository.api.TopicMenuMustdoRepository;
import com.jason.exam.dao.repository.api.TopicMustdoRepository;
import com.jason.exam.dao.repository.api.TopicOldExamRepository;
import com.jason.exam.dao.repository.api.TopicSimulationRepository;
import com.jason.exam.dao.repository.api.TopicTypeRepository;
import com.jason.exam.model.TopicChapter;
import com.jason.exam.model.TopicMenuChapter;
import com.jason.exam.model.TopicMenuMustDo;
import com.jason.exam.model.TopicMenuOldExam;
import com.jason.exam.model.TopicMenuSimulation;
import com.jason.exam.model.TopicMustDo;
import com.jason.exam.model.TopicOldExam;
import com.jason.exam.model.TopicSimulation;
import com.jason.exam.model.TopicType;
import com.jason.exam.service.api.ITopicService;

@Service
public class TopicServiceImpl implements ITopicService {

	@Autowired
	private RestTemplate restTemplate;

	@Resource
	PlanCatalogRepository planCatalogRepository;

	@Resource
	TopicTypeRepository topicTypeRepository;

	@Resource
	TopicChapterRepository topicChapterRepository;

	@Resource
	OldExamRepository oldExamRepository;
	@Resource
	TopicOldExamRepository topicOldExamRepository;

	@Resource
	SimulationRepository simulationRepository;
	@Resource
	TopicSimulationRepository topicSimulationRepository;

	@Resource
	TopicMenuMustdoRepository topicMenuMustdoRepository;

	@Resource
	TopicMustdoRepository topicMustdoRepository;

	/**
	 * 获取章节练习目录及题目
	 */
	@Override
	public void getChapterTopic(String planId) {
		// 获取目录
		String uri = "http://wx.100xuexi.com/handle/tkHandle.ashx";
		MultiValueMap<String, String> postData = new LinkedMultiValueMap<String, String>();
		postData.add("mtype", "GetMenu");
		postData.add("questionPlanID", "3088");
		postData.add("orgID", "");
		postData.add("userID", "");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Origin", "http://wx.100xuexi.com");
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(postData,
				headers);
		String json = restTemplate.postForEntity(uri, request, String.class).getBody();
		JSONObject menu = JSONObject.parseObject(json);
		JSONArray data = menu.getJSONArray("data");
		for (int i = 0; i < data.size(); i++) {
			JSONObject modual = data.getJSONObject(i);
			if (modual.getInteger("moduleID") == 2) {// 获取章节练习
				JSONArray papers = modual.getJSONArray("paperList");
				String parent_uuid = "";
				for (int j = 0; j < papers.size(); j++) {
					JSONObject paper = papers.getJSONObject(j);
					String paperId = paper.getString("paperID");
					if ("0".equals(paper.getString("parentID"))) {
						String name = paper.getString("classTypeName");
						TopicMenuChapter planCatalog = planCatalogRepository.findByNameAndPlanUuid(name, planId);
						if (planCatalog == null) {
							planCatalog = new TopicMenuChapter();
							planCatalog.setName(paper.getString("classTypeName"));
							planCatalog.setParent_uuid("0");
							planCatalog.setPlan_uuid(planId);
							planCatalog.setStatus(1);
							planCatalog.setUuid(UUIDTool.getUUID());
							planCatalog.setCreate_time(new Date());
							planCatalog.setPaper_id(paperId);
							planCatalog = planCatalogRepository.save(planCatalog);
						}
						parent_uuid = planCatalog.getUuid();
					} else {
						// 先看看这个章节有没有处理过，处理过就不再处理
						TopicMenuChapter TopicMenuChapter = planCatalogRepository.findByPaperId(paperId);
						if (TopicMenuChapter == null) {
							TopicMenuChapter planCatalog = new TopicMenuChapter();
							planCatalog.setName(paper.getString("paperName"));
							planCatalog.setParent_uuid(parent_uuid);
							planCatalog.setPlan_uuid(planId);
							planCatalog.setStatus(1);
							planCatalog.setUuid(UUIDTool.getUUID());
							planCatalog.setCreate_time(new Date());
							planCatalog.setPaper_id(paperId);
							planCatalog = planCatalogRepository.save(planCatalog);

							// ================
							// 根据paperid 获取 题目 id 65404480-ec96-454b-b4a7-80610b354c08
							MultiValueMap<String, String> postData2 = new LinkedMultiValueMap<String, String>();
							postData2.add("mtype", "GetPaperAnswerSheet");
							postData2.add("paperID", paper.getString("paperID"));
							postData2.add("orgID", "wuyou.100xuexi.com");
							postData2.add("paperType", "1");
							HttpEntity<MultiValueMap<String, String>> request2 = new HttpEntity<MultiValueMap<String, String>>(
									postData2, headers);
							String json2 = restTemplate.postForEntity(uri, request2, String.class).getBody();
							JSONArray nodelist = JSONObject.parseObject(json2).getJSONObject("data")
									.getJSONArray("nodeList");
							System.out.println(nodelist);

							for (int k = 0; k < nodelist.size(); k++) {
								JSONObject topictype = nodelist.getJSONObject(k);
								String name = topictype.getString("name").replace("型题", "").trim();
								String desc = topictype.getString("desc");

								// 题型处理
								if (name.contains("A1") || name.contains("A2")) {
									name = "A1/A2型选择题";
									desc = "以下每一道考题下面有A、B、C、D、E五个备选答案。请从中选择一个最佳答案．并在答题卡上将相应题号的相应字母所属的方框涂黑。";
								}
								if (name.contains("B1") || name.contains("B")) {
									name = "B1型选择题";
									desc = "以下提供若干组考题，每组考题共同使用在考题前列出的A、B、C、D、E五个备选答案。请从中选择一个与考题关系最密切的答案，并在答题卡上将相应题号的相应字母所属的方框涂黑。某个备选答案可能被选择一次、多次或不被选择。";
								}
								if (name.contains("A3") || name.contains("A4")) {
									name = "A3/A4型选择题";
									desc = "以下提供若干个案例，每个案例有若干个考题。请根据提供的信息，在每题的A、B、C、D、E五个备选答案中选择一个最佳答案，并在答题卡上按照题号，将所选答案对应字母的方框涂黑。";
								}

								TopicType topicType = topicTypeRepository.findTopicTypeByName(name);
								if (topicType == null) {
									topicType = new TopicType();
									topicType.setType_name(name);
									topicType.setDesc(desc);
									topicType.setCreate_time(new Date());
									topicType.setUuid(UUIDTool.getUUID());
									topicTypeRepository.save(topicType);
								}

								if("A1/A2型选择题".equals(name)) {
									JSONArray topics = topictype.getJSONArray("questionList");
									List<String> topicList = new ArrayList<>();
									for (int h = 0; h < topics.size(); h++) {
										topicList.add(topics.getString(h));
									}
									Collections.shuffle(topicList);
									Collections.shuffle(topicList);
									for (int h = 0; h < topicList.size(); h++) {
										String uuid = topicList.get(h);

										MultiValueMap<String, String> postData3 = new LinkedMultiValueMap<String, String>();
										postData3.add("mtype", "GetExamDetail");
										postData3.add("id", uuid);
										postData3.add("orgID", "wuyou.100xuexi.com");
										HttpEntity<MultiValueMap<String, String>> request3 = new HttpEntity<MultiValueMap<String, String>>(
												postData3, headers);
										String json3 = restTemplate.postForEntity(uri, request3, String.class).getBody();
										JSONObject topicObj = JSONObject.parseObject(json3).getJSONObject("data");

										TopicChapter topicChapter = new TopicChapter();

										topicChapter.setUuid(UUIDTool.getUUID());
										topicChapter.setTitle(topicObj.getString("QuestionContent"));
										topicChapter.setAnalysis(topicObj.getString("QuestionAnalysis"));
										topicChapter.setScore(topicObj.getString("QuestionScore"));
										String answer = topicObj.getString("answer");
										String selectAnswer = topicObj.getString("selectAnswer");
										topicChapter.setAnswer_real(answer);
										topicChapter.setSelect_answer_real(selectAnswer);
										List<String> temp = getRandomAnswer(selectAnswer, answer);
										topicChapter.setSelect_answer(temp.get(0));
										topicChapter.setAnswer(temp.get(1));

										topicChapter.setType(topicObj.getString("typeName"));
										topicChapter.setType_tow(topicType.getUuid());
										topicChapter.setTitle_parent(topicObj.getString("QuestionContentParent"));
										topicChapter.setCreate_time(new Date());
										topicChapter.setPlan_catalog_uuid(planCatalog.getUuid());
										topicChapter.setStatus(1);
										topicChapterRepository.save(topicChapter);
									}
								}else if("B1型选择题".equals(name)){

									JSONArray topics = topictype.getJSONArray("questionList");
									List<TopicChapter> topicChapters = new ArrayList<>();
									for (int h = 0; h < topics.size(); h++) {
										String uuid = topics.getString(h);

										MultiValueMap<String, String> postData3 = new LinkedMultiValueMap<String, String>();
										postData3.add("mtype", "GetExamDetail");
										postData3.add("id", uuid);
										postData3.add("orgID", "wuyou.100xuexi.com");
										HttpEntity<MultiValueMap<String, String>> request3 = new HttpEntity<MultiValueMap<String, String>>(
												postData3, headers);
										String json3 = restTemplate.postForEntity(uri, request3, String.class).getBody();
										JSONObject topicObj = JSONObject.parseObject(json3).getJSONObject("data");

										TopicChapter topicChapter = new TopicChapter();

										topicChapter.setUuid(UUIDTool.getUUID());
										topicChapter.setTitle(topicObj.getString("QuestionContent"));
										topicChapter.setAnalysis(topicObj.getString("QuestionAnalysis"));
										topicChapter.setScore(topicObj.getString("QuestionScore"));
										String answer = topicObj.getString("answer");
										String questionContentParent = topicObj.getString("QuestionContentParent");
										topicChapter.setAnswer_real(answer);
										topicChapter.setSelect_answer_real(questionContentParent);
//										List<String> temp = getRandomAnswerForBA3A4(questionContentParent, answer);
										topicChapter.setSelect_answer(topicObj.getString("selectAnswer"));
										topicChapter.setTitle_parent(questionContentParent);
										topicChapter.setAnswer(answer);

										topicChapter.setType(topicObj.getString("typeName"));
										topicChapter.setType_tow(topicType.getUuid());

										topicChapter.setCreate_time(new Date());
										topicChapter.setPlan_catalog_uuid(planCatalog.getUuid());
										topicChapter.setStatus(1);

										topicChapters.add(topicChapter);

									}
									Collections.shuffle(topicChapters);
									Collections.shuffle(topicChapters);
									ArrayListMultimap<String,TopicChapter> topicsMultimap = ArrayListMultimap.create();
									for(int p=0;p<topicChapters.size();p++){
										topicsMultimap.put(topicChapters.get(p).getTitle_parent(),topicChapters.get(p));
									}
									List<TopicChapter> topicChaptersTemp = new ArrayList<>();
									Set<String> titleParent = topicsMultimap.keySet();
									titleParent.forEach((s)->{
										topicChaptersTemp.addAll(getRandomAnswerForBA3A4(topicsMultimap.get(s)));
									});

									topicChaptersTemp.forEach((topicChapter)->{
										topicChapterRepository.save(topicChapter);
									});
								}else if("A3/A4型选择题".equals(name)){
									JSONArray topics = topictype.getJSONArray("questionList");
									List<TopicChapter> topicChapters = new ArrayList<>();

									for (int h = 0; h < topics.size(); h++) {
										String uuid = topics.getString(h);

										MultiValueMap<String, String> postData3 = new LinkedMultiValueMap<String, String>();
										postData3.add("mtype", "GetExamDetail");
										postData3.add("id", uuid);
										postData3.add("orgID", "wuyou.100xuexi.com");
										HttpEntity<MultiValueMap<String, String>> request3 = new HttpEntity<MultiValueMap<String, String>>(
												postData3, headers);
										String json3 = restTemplate.postForEntity(uri, request3, String.class).getBody();
										JSONObject topicObj = JSONObject.parseObject(json3).getJSONObject("data");

										TopicChapter topicChapter = new TopicChapter();

										topicChapter.setUuid(UUIDTool.getUUID());
										topicChapter.setTitle(topicObj.getString("QuestionContent"));
										topicChapter.setAnalysis(topicObj.getString("QuestionAnalysis"));
										topicChapter.setScore(topicObj.getString("QuestionScore"));
										String answer = topicObj.getString("answer");
										String selectAnswer = topicObj.getString("selectAnswer");
										topicChapter.setAnswer_real(answer);
										topicChapter.setSelect_answer_real(selectAnswer);
										List<String> temp = getRandomAnswer(selectAnswer, answer);
										topicChapter.setSelect_answer(temp.get(0));
										topicChapter.setAnswer(temp.get(1));

										topicChapter.setType(topicObj.getString("typeName"));
										topicChapter.setType_tow(topicType.getUuid());
										topicChapter.setTitle_parent(topicObj.getString("QuestionContentParent"));
										topicChapter.setCreate_time(new Date());
										topicChapter.setPlan_catalog_uuid(planCatalog.getUuid());
										topicChapter.setStatus(1);
										topicChapters.add(topicChapter);
									}
									Collections.shuffle(topicChapters);
									Collections.shuffle(topicChapters);
									ArrayListMultimap<String,TopicChapter> topicsMultimap = ArrayListMultimap.create();
									for(int p=0;p<topicChapters.size();p++){
										topicsMultimap.put(topicChapters.get(p).getTitle_parent(),topicChapters.get(p));
									}
									List<TopicChapter> topicChaptersTemp = new ArrayList<>();
									Set<String> titleParent = topicsMultimap.keySet();
									titleParent.forEach((s)->{
										topicChaptersTemp.addAll(topicsMultimap.get(s));
									});
									topicChaptersTemp.forEach((topicChapter)->{
										topicChapterRepository.save(topicChapter);
									});
								}
							}

						}
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static List<String> getRandomAnswer(String selectAnswer, String answer){
		Map<String,Integer> tempA1 = new HashMap<>();
		tempA1.put("A",0);
		tempA1.put("B",1);
		tempA1.put("C",2);
		tempA1.put("D",3);
		tempA1.put("E",4);
		tempA1.put("F",5);
		tempA1.put("G",6);
		tempA1.put("H",7);
		tempA1.put("I",8);
		tempA1.put("J",9);
		Map<Integer,String> temp1A = new HashMap<>();
		temp1A.put(0,"A");
		temp1A.put(1,"B");
		temp1A.put(2,"C");
		temp1A.put(3,"D");
		temp1A.put(4,"E");
		temp1A.put(5,"F");
		temp1A.put(6,"G");
		temp1A.put(7,"H");
		temp1A.put(8,"I");
		temp1A.put(9,"J");
		List<String> options = Stream.of(selectAnswer.split("\\|")).collect(Collectors.toList());
		List<String> answers = Stream.of(answer.split(",")).collect(Collectors.toList());
		List<String> answerOptions = new ArrayList<>();
		List<String> lastAnswers = new ArrayList<>();

		for(int i=0;i<answers.size();i++){
			answerOptions.add(options.get(tempA1.get(answers.get(i).toUpperCase())));
		};
		Collections.shuffle(options);
		for(int i=0;i<options.size();i++){
			if(answerOptions.contains(options.get(i))){
				lastAnswers.add(temp1A.get(i));
			}
		}
		Collections.sort(lastAnswers);
		return Arrays.asList(String.join("|", options),String.join(",", lastAnswers));
	}

	public static List<TopicChapter> getRandomAnswerForBA3A4(List<TopicChapter> topicChapters){
		Map<String,Integer> tempA1 = new HashMap<>();
		tempA1.put("A",0);
		tempA1.put("B",1);
		tempA1.put("C",2);
		tempA1.put("D",3);
		tempA1.put("E",4);
		tempA1.put("F",5);
		tempA1.put("G",6);
		tempA1.put("H",7);
		tempA1.put("I",8);
		tempA1.put("J",9);
		Map<Integer,String> temp1A = new HashMap<>();
		temp1A.put(0,"A");
		temp1A.put(1,"B");
		temp1A.put(2,"C");
		temp1A.put(3,"D");
		temp1A.put(4,"E");
		temp1A.put(5,"F");
		temp1A.put(6,"G");
		temp1A.put(7,"H");
		temp1A.put(8,"I");
		temp1A.put(9,"J");
		//每组题的公共选项
		String questionContentParent = topicChapters.get(0).getTitle_parent();
		//选项处理
		List<String> options = Stream.of(questionContentParent.split("<br />")).collect(Collectors.toList());
		options.remove(0);
		List<String> optionsTemp = new ArrayList<>();
		List<String> lastOptions = new ArrayList<>();
		for (String s: options) {
			if(s.startsWith("A.")){
				optionsTemp.add(s.replace("A.",""));
				lastOptions.add(s.replace("A.",""));
			}
			if(s.startsWith("B.")){
				optionsTemp.add(s.replace("B.",""));
				lastOptions.add(s.replace("B.",""));
			}
			if(s.startsWith("C.")){
				optionsTemp.add(s.replace("C.",""));
				lastOptions.add(s.replace("C.",""));
			}
			if(s.startsWith("D.")){
				optionsTemp.add(s.replace("D.",""));
				lastOptions.add(s.replace("D.",""));
			}
			if(s.startsWith("E.")){
				optionsTemp.add(s.replace("E.",""));
				lastOptions.add(s.replace("E.",""));
			}
			if(s.startsWith("F.")){
				optionsTemp.add(s.replace("F.",""));
				lastOptions.add(s.replace("F.",""));
			}
			if(s.startsWith("G.")){
				optionsTemp.add(s.replace("G.",""));
				lastOptions.add(s.replace("G.",""));
			}
		}
		Collections.shuffle(lastOptions);
		Collections.shuffle(lastOptions);

		//循环每一道题目
		for (TopicChapter topicChapter:topicChapters) {
			String answer = topicChapter.getAnswer();
			List<String> answers = Stream.of(answer.split(",")).collect(Collectors.toList());
			List<String> answerOptions = new ArrayList<>();
			List<String> lastAnswers = new ArrayList<>();

			for(int i=0;i<answers.size();i++){
				answerOptions.add(optionsTemp.get(tempA1.get(answers.get(i).toUpperCase())));
			};

			for(int i=0;i<lastOptions.size();i++){
				if(answerOptions.contains(lastOptions.get(i))){
					lastAnswers.add(temp1A.get(i));
				}
			}
			Collections.sort(lastAnswers);



			List<String> lastOptions1 = new ArrayList<>();
			for(int i=0;i<lastOptions.size();i++){
				lastOptions1.add(temp1A.get(i)+"."+lastOptions.get(i));
			}


			topicChapter.setTitle_parent("（公共选项）"+String.join("<br />", lastOptions));
			topicChapter.setAnswer(String.join(",", lastAnswers));
		}
		return topicChapters;
	}


	public static void main(String[] args) {
		String a = "（共用备选答案）<br />A.维生素缺乏<br />B.能量缺乏<br />C.蛋白质缺乏<br />D.脂肪缺乏<br />E.水缺乏";
		String b = "B";
//		System.out.println(getRandomAnswerForBA3A4(a,b));
	}

	/**
	 * 获取真题目录及题目
	 */
	@Override
	public void getoldExamTopic(String planId) {

		// 获取目录
		String uri = "http://wx.100xuexi.com/handle/tkHandle.ashx";
		MultiValueMap<String, String> postData = new LinkedMultiValueMap<String, String>();
		postData.add("mtype", "GetMenu");
		postData.add("questionPlanID", "3088");
		postData.add("orgID", "");
		postData.add("userID", "");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Origin", "http://wx.100xuexi.com");
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(postData,
				headers);
		String json = restTemplate.postForEntity(uri, request, String.class).getBody();
		JSONObject menu = JSONObject.parseObject(json);
		JSONArray data = menu.getJSONArray("data");
		for (int i = 0; i < data.size(); i++) {
			JSONObject modual = data.getJSONObject(i);
			if (modual.getInteger("moduleID") == 23) {// 获取真题
				JSONArray papers = modual.getJSONArray("paperList");
				for (int j = 0; j < papers.size(); j++) {
					JSONObject paper = papers.getJSONObject(j);
					TopicMenuOldExam oldExam = oldExamRepository.findOldExamByPaperId(paper.getString("paperID"));
					if (oldExam == null) {
						oldExam = new TopicMenuOldExam();
						oldExam.setUuid(UUIDTool.getUUID());
						oldExam.setName(paper.getString("paperName"));
						oldExam.setCreate_time(new Date());
						oldExam.setDesc(paper.getString("paperName"));
						oldExam.setPlan_uuid(planId);
						oldExam.setThird_party_paper_id(paper.getString("paperID"));
						oldExam.setStatus(1);
						oldExamRepository.save(oldExam);
					}

					MultiValueMap<String, String> postData2 = new LinkedMultiValueMap<String, String>();
					postData2.add("mtype", "GetPaperAnswerSheet");
					postData2.add("paperID", paper.getString("paperID"));
					postData2.add("orgID", "wuyou.100xuexi.com");
					postData2.add("paperType", "1");
					HttpEntity<MultiValueMap<String, String>> request2 = new HttpEntity<MultiValueMap<String, String>>(
							postData2, headers);
					String json2 = restTemplate.postForEntity(uri, request2, String.class).getBody();
					JSONArray nodelist = JSONObject.parseObject(json2).getJSONObject("data").getJSONArray("nodeList");
					for (int k = 0; k < nodelist.size(); k++) {
						JSONObject topictype = nodelist.getJSONObject(k);
						String name = topictype.getString("name").replace("型题", "").trim();
						String desc = topictype.getString("desc");

						// 题型处理
						if (name.contains("A1") || name.contains("A2")) {
							name = "A1/A2型选择题";
							desc = "以下每一道考题下面有A、B、C、D、E五个备选答案。请从中选择一个最佳答案．并在答题卡上将相应题号的相应字母所属的方框涂黑。";
						}
						if (name.contains("B1") || name.contains("B")) {
							name = "B1型选择题";
							desc = "以下提供若干组考题，每组考题共同使用在考题前列出的A、B、C、D、E五个备选答案。请从中选择一个与考题关系最密切的答案，并在答题卡上将相应题号的相应字母所属的方框涂黑。某个备选答案可能被选择一次、多次或不被选择。";
						}
						if (name.contains("A3") || name.contains("A4")) {
							name = "A3/A4型选择题";
							desc = "以下提供若干个案例，每个案例有若干个考题。请根据提供的信息，在每题的A、B、C、D、E五个备选答案中选择一个最佳答案，并在答题卡上按照题号，将所选答案对应字母的方框涂黑。";
						}

						TopicType topicType = topicTypeRepository.findTopicTypeByName(name);
						if (topicType == null) {
							topicType = new TopicType();
							topicType.setType_name(name);
							topicType.setDesc(desc);
							topicType.setCreate_time(new Date());
							topicType.setUuid(UUIDTool.getUUID());
							topicTypeRepository.save(topicType);
						}

						if("A1/A2型选择题".equals(name)) {
							JSONArray topics = topictype.getJSONArray("questionList");
							List<String> topicList = new ArrayList<>();
							for (int h = 0; h < topics.size(); h++) {
								topicList.add(topics.getString(h));
							}
							Collections.shuffle(topicList);
							Collections.shuffle(topicList);

							for (int h = 0; h < topicList.size(); h++) {
								String uuid = topicList.get(h);

								MultiValueMap<String, String> postData3 = new LinkedMultiValueMap<String, String>();
								postData3.add("mtype", "GetExamDetail");
								postData3.add("id", uuid);
								postData3.add("orgID", "wuyou.100xuexi.com");
								HttpEntity<MultiValueMap<String, String>> request3 = new HttpEntity<MultiValueMap<String, String>>(
										postData3, headers);
								String json3 = restTemplate.postForEntity(uri, request3, String.class).getBody();
								JSONObject topicObj = JSONObject.parseObject(json3).getJSONObject("data");

								TopicOldExam topicOldExam = topicOldExamRepository
										.findTopicOldExamByQidAndQno(topicObj.getString("qid"), topicObj.getString("qNO"));
								if (topicOldExam == null) {
									topicOldExam = new TopicOldExam();
									topicOldExam.setUuid(UUIDTool.getUUID());
									topicOldExam.setTitle(topicObj.getString("QuestionContent"));
									topicOldExam.setAnalysis(topicObj.getString("QuestionAnalysis"));
									topicOldExam.setScore(topicObj.getString("QuestionScore"));

									String answer = topicObj.getString("answer");
									String selectAnswer = topicObj.getString("selectAnswer");
									topicOldExam.setAnswer_real(answer);
									topicOldExam.setSelect_answer_real(selectAnswer);

									List<String> temp = getRandomAnswer(selectAnswer, answer);
									topicOldExam.setSelect_answer(temp.get(0));
									topicOldExam.setAnswer(temp.get(1));

									topicOldExam.setType(topicObj.getString("typeName"));
									topicOldExam.setType_tow(topicType.getUuid());
									topicOldExam.setTitle_parent(topicObj.getString("QuestionContentParent"));
									topicOldExam.setCreate_time(new Date());

									topicOldExam.setOld_exam_uuid(oldExam.getUuid());
									topicOldExam.setThird_party_qid(topicObj.getString("qid"));
									topicOldExam.setThird_party_qno(topicObj.getString("qNO"));
									topicOldExam.setStatus(1);
									topicOldExamRepository.save(topicOldExam);
								}
							}
						}else if("B1型选择题".equals(name)){

							JSONArray topics = topictype.getJSONArray("questionList");
							List<TopicOldExam> oldExams = new ArrayList<>();
							for (int h = 0; h < topics.size(); h++) {
								String uuid = topics.getString(h);

								MultiValueMap<String, String> postData3 = new LinkedMultiValueMap<String, String>();
								postData3.add("mtype", "GetExamDetail");
								postData3.add("id", uuid);
								postData3.add("orgID", "wuyou.100xuexi.com");
								HttpEntity<MultiValueMap<String, String>> request3 = new HttpEntity<MultiValueMap<String, String>>(
										postData3, headers);
								String json3 = restTemplate.postForEntity(uri, request3, String.class).getBody();
								JSONObject topicObj = JSONObject.parseObject(json3).getJSONObject("data");

								TopicOldExam oldExam1 = new TopicOldExam();

								oldExam1.setUuid(UUIDTool.getUUID());
								oldExam1.setTitle(topicObj.getString("QuestionContent"));
								oldExam1.setAnalysis(topicObj.getString("QuestionAnalysis"));
								oldExam1.setScore(topicObj.getString("QuestionScore"));
								String answer = topicObj.getString("answer");
								String questionContentParent = topicObj.getString("QuestionContentParent");
								oldExam1.setAnswer_real(answer);
								oldExam1.setSelect_answer_real(questionContentParent);
//										List<String> temp = getRandomAnswerForBA3A4(questionContentParent, answer);
								oldExam1.setSelect_answer(topicObj.getString("selectAnswer"));
								oldExam1.setTitle_parent(questionContentParent);
								oldExam1.setAnswer(answer);

								oldExam1.setType(topicObj.getString("typeName"));
								oldExam1.setType_tow(topicType.getUuid());

								oldExam1.setCreate_time(new Date());
								oldExam1.setOld_exam_uuid(oldExam.getUuid());
								oldExam1.setStatus(1);

								oldExams.add(oldExam1);

							}
							Collections.shuffle(oldExams);
							Collections.shuffle(oldExams);
							ArrayListMultimap<String,TopicOldExam> topicsMultimap = ArrayListMultimap.create();
							for(int p=0;p<oldExams.size();p++){
								topicsMultimap.put(oldExams.get(p).getTitle_parent(),oldExams.get(p));
							}
							List<TopicOldExam> topicChaptersTemp = new ArrayList<>();
							Set<String> titleParent = topicsMultimap.keySet();
							titleParent.forEach((s)->{
								topicChaptersTemp.addAll(getRandomAnswerForOldExamB(topicsMultimap.get(s)));
							});

							topicChaptersTemp.forEach((topicOldExam)->{
								topicOldExamRepository.save(topicOldExam);
							});
						}else if("A3/A4型选择题".equals(name)){
							JSONArray topics = topictype.getJSONArray("questionList");
							List<TopicOldExam> topicOldExams = new ArrayList<>();

							for (int h = 0; h < topics.size(); h++) {
								String uuid = topics.getString(h);

								MultiValueMap<String, String> postData3 = new LinkedMultiValueMap<String, String>();
								postData3.add("mtype", "GetExamDetail");
								postData3.add("id", uuid);
								postData3.add("orgID", "wuyou.100xuexi.com");
								HttpEntity<MultiValueMap<String, String>> request3 = new HttpEntity<MultiValueMap<String, String>>(
										postData3, headers);
								String json3 = restTemplate.postForEntity(uri, request3, String.class).getBody();
								JSONObject topicObj = JSONObject.parseObject(json3).getJSONObject("data");

								TopicOldExam oldExam1 = new TopicOldExam();

								oldExam1.setUuid(UUIDTool.getUUID());
								oldExam1.setTitle(topicObj.getString("QuestionContent"));
								oldExam1.setAnalysis(topicObj.getString("QuestionAnalysis"));
								oldExam1.setScore(topicObj.getString("QuestionScore"));
								String answer = topicObj.getString("answer");
								String selectAnswer = topicObj.getString("selectAnswer");
								oldExam1.setAnswer_real(answer);
								oldExam1.setSelect_answer_real(selectAnswer);
								List<String> temp = getRandomAnswer(selectAnswer, answer);
								oldExam1.setSelect_answer(temp.get(0));
								oldExam1.setAnswer(temp.get(1));

								oldExam1.setType(topicObj.getString("typeName"));
								oldExam1.setType_tow(topicType.getUuid());
								oldExam1.setTitle_parent(topicObj.getString("QuestionContentParent"));
								oldExam1.setCreate_time(new Date());
								oldExam1.setOld_exam_uuid(oldExam.getUuid());
								oldExam1.setStatus(1);
								topicOldExams.add(oldExam1);
							}
							Collections.shuffle(topicOldExams);
							Collections.shuffle(topicOldExams);
							ArrayListMultimap<String,TopicOldExam> topicsMultimap = ArrayListMultimap.create();
							for(int p=0;p<topicOldExams.size();p++){
								topicsMultimap.put(topicOldExams.get(p).getTitle_parent(),topicOldExams.get(p));
							}
							List<TopicOldExam> topicChaptersTemp = new ArrayList<>();
							Set<String> titleParent = topicsMultimap.keySet();
							titleParent.forEach((s)->{
								topicChaptersTemp.addAll(topicsMultimap.get(s));
							});
							topicChaptersTemp.forEach((topicOldExam)->{
								topicOldExamRepository.save(topicOldExam);
							});
						}
					}
				}
			}
		}
	}

	public static List<TopicOldExam> getRandomAnswerForOldExamB(List<TopicOldExam> oldExams){
		Map<String,Integer> tempA1 = new HashMap<>();
		tempA1.put("A",0);
		tempA1.put("B",1);
		tempA1.put("C",2);
		tempA1.put("D",3);
		tempA1.put("E",4);
		tempA1.put("F",5);
		tempA1.put("G",6);
		tempA1.put("H",7);
		tempA1.put("I",8);
		tempA1.put("J",9);
		Map<Integer,String> temp1A = new HashMap<>();
		temp1A.put(0,"A");
		temp1A.put(1,"B");
		temp1A.put(2,"C");
		temp1A.put(3,"D");
		temp1A.put(4,"E");
		temp1A.put(5,"F");
		temp1A.put(6,"G");
		temp1A.put(7,"H");
		temp1A.put(8,"I");
		temp1A.put(9,"J");
		//每组题的公共选项
		String questionContentParent = oldExams.get(0).getTitle_parent();
		//选项处理
		List<String> options = Stream.of(questionContentParent.split("<br />")).collect(Collectors.toList());
		options.remove(0);
		List<String> optionsTemp = new ArrayList<>();
		List<String> lastOptions = new ArrayList<>();
		for (String s: options) {
			if(s.startsWith("A.")){
				optionsTemp.add(s.replace("A.",""));
				lastOptions.add(s.replace("A.",""));
			}
			if(s.startsWith("B.")){
				optionsTemp.add(s.replace("B.",""));
				lastOptions.add(s.replace("B.",""));
			}
			if(s.startsWith("C.")){
				optionsTemp.add(s.replace("C.",""));
				lastOptions.add(s.replace("C.",""));
			}
			if(s.startsWith("D.")){
				optionsTemp.add(s.replace("D.",""));
				lastOptions.add(s.replace("D.",""));
			}
			if(s.startsWith("E.")){
				optionsTemp.add(s.replace("E.",""));
				lastOptions.add(s.replace("E.",""));
			}
			if(s.startsWith("F.")){
				optionsTemp.add(s.replace("F.",""));
				lastOptions.add(s.replace("F.",""));
			}
			if(s.startsWith("G.")){
				optionsTemp.add(s.replace("G.",""));
				lastOptions.add(s.replace("G.",""));
			}
		}
		Collections.shuffle(lastOptions);
		Collections.shuffle(lastOptions);

		//循环每一道题目
		for (TopicOldExam oldExam:oldExams) {
			String answer = oldExam.getAnswer();
			List<String> answers = Stream.of(answer.split(",")).collect(Collectors.toList());
			List<String> answerOptions = new ArrayList<>();
			List<String> lastAnswers = new ArrayList<>();

			for(int i=0;i<answers.size();i++){
				answerOptions.add(optionsTemp.get(tempA1.get(answers.get(i).toUpperCase())));
			};

			for(int i=0;i<lastOptions.size();i++){
				if(answerOptions.contains(lastOptions.get(i))){
					lastAnswers.add(temp1A.get(i));
				}
			}
			Collections.sort(lastAnswers);



			List<String> lastOptions1 = new ArrayList<>();
			for(int i=0;i<lastOptions.size();i++){
				lastOptions1.add(temp1A.get(i)+"."+lastOptions.get(i));
			}


			oldExam.setTitle_parent("（公共选项）"+String.join("<br />", lastOptions));
			oldExam.setAnswer(String.join(",", lastAnswers));
		}
		return oldExams;
	}

	public static List<TopicSimulation> getRandomAnswerForSimulationB(List<TopicSimulation> topicSimulations){
		Map<String,Integer> tempA1 = new HashMap<>();
		tempA1.put("A",0);
		tempA1.put("B",1);
		tempA1.put("C",2);
		tempA1.put("D",3);
		tempA1.put("E",4);
		tempA1.put("F",5);
		tempA1.put("G",6);
		tempA1.put("H",7);
		tempA1.put("I",8);
		tempA1.put("J",9);
		Map<Integer,String> temp1A = new HashMap<>();
		temp1A.put(0,"A");
		temp1A.put(1,"B");
		temp1A.put(2,"C");
		temp1A.put(3,"D");
		temp1A.put(4,"E");
		temp1A.put(5,"F");
		temp1A.put(6,"G");
		temp1A.put(7,"H");
		temp1A.put(8,"I");
		temp1A.put(9,"J");
		//每组题的公共选项
		String questionContentParent = topicSimulations.get(0).getTitle_parent();
		//选项处理
		List<String> options = Stream.of(questionContentParent.split("<br />")).collect(Collectors.toList());
		options.remove(0);
		List<String> optionsTemp = new ArrayList<>();
		List<String> lastOptions = new ArrayList<>();
		for (String s: options) {
			if(s.startsWith("A.")){
				optionsTemp.add(s.replace("A.",""));
				lastOptions.add(s.replace("A.",""));
			}
			if(s.startsWith("B.")){
				optionsTemp.add(s.replace("B.",""));
				lastOptions.add(s.replace("B.",""));
			}
			if(s.startsWith("C.")){
				optionsTemp.add(s.replace("C.",""));
				lastOptions.add(s.replace("C.",""));
			}
			if(s.startsWith("D.")){
				optionsTemp.add(s.replace("D.",""));
				lastOptions.add(s.replace("D.",""));
			}
			if(s.startsWith("E.")){
				optionsTemp.add(s.replace("E.",""));
				lastOptions.add(s.replace("E.",""));
			}
			if(s.startsWith("F.")){
				optionsTemp.add(s.replace("F.",""));
				lastOptions.add(s.replace("F.",""));
			}
			if(s.startsWith("G.")){
				optionsTemp.add(s.replace("G.",""));
				lastOptions.add(s.replace("G.",""));
			}
		}
		Collections.shuffle(lastOptions);
		Collections.shuffle(lastOptions);

		//循环每一道题目
		for (TopicSimulation topicSimulation:topicSimulations) {
			String answer = topicSimulation.getAnswer();
			List<String> answers = Stream.of(answer.split(",")).collect(Collectors.toList());
			List<String> answerOptions = new ArrayList<>();
			List<String> lastAnswers = new ArrayList<>();

			for(int i=0;i<answers.size();i++){
				answerOptions.add(optionsTemp.get(tempA1.get(answers.get(i).toUpperCase())));
			};

			for(int i=0;i<lastOptions.size();i++){
				if(answerOptions.contains(lastOptions.get(i))){
					lastAnswers.add(temp1A.get(i));
				}
			}
			Collections.sort(lastAnswers);



			List<String> lastOptions1 = new ArrayList<>();
			for(int i=0;i<lastOptions.size();i++){
				lastOptions1.add(temp1A.get(i)+"."+lastOptions.get(i));
			}


			topicSimulation.setTitle_parent("（公共选项）"+String.join("<br />", lastOptions));
			topicSimulation.setAnswer(String.join(",", lastAnswers));
		}
		return topicSimulations;
	}

	@Override
	public void getSimulationTopic(String planId) {
		// 获取目录
		String uri = "http://wx.100xuexi.com/handle/tkHandle.ashx";
		MultiValueMap<String, String> postData = new LinkedMultiValueMap<String, String>();
		postData.add("mtype", "GetMenu");
		postData.add("questionPlanID", "3088");
		postData.add("orgID", "");
		postData.add("userID", "");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Origin", "http://wx.100xuexi.com");
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(postData,
				headers);
		String json = restTemplate.postForEntity(uri, request, String.class).getBody();
		JSONObject menu = JSONObject.parseObject(json);
		JSONArray data = menu.getJSONArray("data");
		for (int i = 0; i < data.size(); i++) {
			JSONObject modual = data.getJSONObject(i);
			if (modual.getInteger("moduleID") == 3) {// 获取模拟题
				JSONArray papers = modual.getJSONArray("paperList");
				for (int j = 0; j < papers.size(); j++) {
					JSONObject paper = papers.getJSONObject(j);
					TopicMenuSimulation simulation = simulationRepository
							.findSimulationByPaperId(paper.getString("paperID"));
					if (simulation == null) {
						simulation = new TopicMenuSimulation();
						simulation.setUuid(UUIDTool.getUUID());
						simulation.setName(paper.getString("paperName"));
						simulation.setCreate_time(new Date());
						simulation.setDesc(paper.getString("paperName"));
						simulation.setPlan_uuid(planId);
						simulation.setThird_party_paper_id(paper.getString("paperID"));
						simulation.setStatus(1);
						simulationRepository.save(simulation);
					}
					MultiValueMap<String, String> postData2 = new LinkedMultiValueMap<String, String>();
					postData2.add("mtype", "GetPaperAnswerSheet");
					postData2.add("paperID", paper.getString("paperID"));
					postData2.add("orgID", "wuyou.100xuexi.com");
					postData2.add("paperType", "1");
					HttpEntity<MultiValueMap<String, String>> request2 = new HttpEntity<MultiValueMap<String, String>>(
							postData2, headers);
					String json2 = restTemplate.postForEntity(uri, request2, String.class).getBody();
					JSONArray nodelist = JSONObject.parseObject(json2).getJSONObject("data").getJSONArray("nodeList");
					for (int k = 0; k < nodelist.size(); k++) {
						JSONObject topictype = nodelist.getJSONObject(k);
						String name = topictype.getString("name").replace("型题", "").trim();
						String desc = topictype.getString("desc");

						// 题型处理
						if (name.contains("A1") || name.contains("A2")) {
							name = "A1/A2型选择题";
							desc = "以下每一道考题下面有A、B、C、D、E五个备选答案。请从中选择一个最佳答案．并在答题卡上将相应题号的相应字母所属的方框涂黑。";
						}
						if (name.contains("B1") || name.contains("B")) {
							name = "B1型选择题";
							desc = "以下提供若干组考题，每组考题共同使用在考题前列出的A、B、C、D、E五个备选答案。请从中选择一个与考题关系最密切的答案，并在答题卡上将相应题号的相应字母所属的方框涂黑。某个备选答案可能被选择一次、多次或不被选择。";
						}
						if (name.contains("A3") || name.contains("A4")) {
							name = "A3/A4型选择题";
							desc = "以下提供若干个案例，每个案例有若干个考题。请根据提供的信息，在每题的A、B、C、D、E五个备选答案中选择一个最佳答案，并在答题卡上按照题号，将所选答案对应字母的方框涂黑。";
						}

						TopicType topicType = topicTypeRepository.findTopicTypeByName(name);
						if (topicType == null) {
							topicType = new TopicType();
							topicType.setType_name(name);
							topicType.setDesc(desc);
							topicType.setCreate_time(new Date());
							topicType.setUuid(UUIDTool.getUUID());
							topicTypeRepository.save(topicType);
						}

						if("A1/A2型选择题".equals(name)) {
							JSONArray topics = topictype.getJSONArray("questionList");
							List<String> topicList = new ArrayList<>();
							for (int h = 0; h < topics.size(); h++) {
								topicList.add(topics.getString(h));
							}
							Collections.shuffle(topicList);
							Collections.shuffle(topicList);

							for (int h = 0; h < topicList.size(); h++) {
								String uuid = topicList.get(h);

								MultiValueMap<String, String> postData3 = new LinkedMultiValueMap<String, String>();
								postData3.add("mtype", "GetExamDetail");
								postData3.add("id", uuid);
								postData3.add("orgID", "wuyou.100xuexi.com");
								HttpEntity<MultiValueMap<String, String>> request3 = new HttpEntity<MultiValueMap<String, String>>(
										postData3, headers);
								String json3 = restTemplate.postForEntity(uri, request3, String.class).getBody();
								JSONObject topicObj = JSONObject.parseObject(json3).getJSONObject("data");

								TopicSimulation topicSimulation = new TopicSimulation();
								topicSimulation.setUuid(UUIDTool.getUUID());
								topicSimulation.setTitle(topicObj.getString("QuestionContent"));
								topicSimulation.setAnalysis(topicObj.getString("QuestionAnalysis"));
								topicSimulation.setScore(topicObj.getString("QuestionScore"));

								String answer = topicObj.getString("answer");
								String selectAnswer = topicObj.getString("selectAnswer");
								topicSimulation.setAnswer_real(answer);
								topicSimulation.setSelect_answer_real(selectAnswer);

								List<String> temp = getRandomAnswer(selectAnswer, answer);
								topicSimulation.setSelect_answer(temp.get(0));
								topicSimulation.setAnswer(temp.get(1));

								topicSimulation.setType(topicObj.getString("typeName"));
								topicSimulation.setType_tow(topicType.getUuid());
								topicSimulation.setTitle_parent(topicObj.getString("QuestionContentParent"));
								topicSimulation.setCreate_time(new Date());
								topicSimulation.setSimulation_uuid(simulation.getUuid());
								topicSimulation.setStatus(1);
								topicSimulationRepository.save(topicSimulation);
							}
						}else if("B1型选择题".equals(name)){

							JSONArray topics = topictype.getJSONArray("questionList");
							List<TopicSimulation> simulations = new ArrayList<>();
							for (int h = 0; h < topics.size(); h++) {
								String uuid = topics.getString(h);

								MultiValueMap<String, String> postData3 = new LinkedMultiValueMap<String, String>();
								postData3.add("mtype", "GetExamDetail");
								postData3.add("id", uuid);
								postData3.add("orgID", "wuyou.100xuexi.com");
								HttpEntity<MultiValueMap<String, String>> request3 = new HttpEntity<MultiValueMap<String, String>>(
										postData3, headers);
								String json3 = restTemplate.postForEntity(uri, request3, String.class).getBody();
								JSONObject topicObj = JSONObject.parseObject(json3).getJSONObject("data");

								TopicSimulation simulation1 = new TopicSimulation();

								simulation1.setUuid(UUIDTool.getUUID());
								simulation1.setTitle(topicObj.getString("QuestionContent"));
								simulation1.setAnalysis(topicObj.getString("QuestionAnalysis"));
								simulation1.setScore(topicObj.getString("QuestionScore"));
								String answer = topicObj.getString("answer");
								String questionContentParent = topicObj.getString("QuestionContentParent");
								simulation1.setAnswer_real(answer);
								simulation1.setSelect_answer_real(questionContentParent);
//										List<String> temp = getRandomAnswerForBA3A4(questionContentParent, answer);
								simulation1.setSelect_answer(topicObj.getString("selectAnswer"));
								simulation1.setTitle_parent(questionContentParent);
								simulation1.setAnswer(answer);

								simulation1.setType(topicObj.getString("typeName"));
								simulation1.setType_tow(topicType.getUuid());

								simulation1.setCreate_time(new Date());
								simulation1.setSimulation_uuid(simulation.getUuid());
								simulation1.setStatus(1);

								simulations.add(simulation1);

							}
							Collections.shuffle(simulations);
							Collections.shuffle(simulations);
							ArrayListMultimap<String,TopicSimulation> topicsMultimap = ArrayListMultimap.create();
							for(int p=0;p<simulations.size();p++){
								topicsMultimap.put(simulations.get(p).getTitle_parent(),simulations.get(p));
							}
							List<TopicSimulation> topicSimulationsTemp = new ArrayList<>();
							Set<String> titleParent = topicsMultimap.keySet();
							titleParent.forEach((s)->{
								topicSimulationsTemp.addAll(getRandomAnswerForSimulationB(topicsMultimap.get(s)));
							});

							topicSimulationsTemp.forEach((topicSimulation)->{
								topicSimulationRepository.save(topicSimulation);
							});
						}else if("A3/A4型选择题".equals(name)){
							JSONArray topics = topictype.getJSONArray("questionList");
							List<TopicSimulation> topicSimulations = new ArrayList<>();

							for (int h = 0; h < topics.size(); h++) {
								String uuid = topics.getString(h);

								MultiValueMap<String, String> postData3 = new LinkedMultiValueMap<String, String>();
								postData3.add("mtype", "GetExamDetail");
								postData3.add("id", uuid);
								postData3.add("orgID", "wuyou.100xuexi.com");
								HttpEntity<MultiValueMap<String, String>> request3 = new HttpEntity<MultiValueMap<String, String>>(
										postData3, headers);
								String json3 = restTemplate.postForEntity(uri, request3, String.class).getBody();
								JSONObject topicObj = JSONObject.parseObject(json3).getJSONObject("data");

								TopicSimulation topicSimulation = new TopicSimulation();

								topicSimulation.setUuid(UUIDTool.getUUID());
								topicSimulation.setTitle(topicObj.getString("QuestionContent"));
								topicSimulation.setAnalysis(topicObj.getString("QuestionAnalysis"));
								topicSimulation.setScore(topicObj.getString("QuestionScore"));
								String answer = topicObj.getString("answer");
								String selectAnswer = topicObj.getString("selectAnswer");
								topicSimulation.setAnswer_real(answer);
								topicSimulation.setSelect_answer_real(selectAnswer);
								List<String> temp = getRandomAnswer(selectAnswer, answer);
								topicSimulation.setSelect_answer(temp.get(0));
								topicSimulation.setAnswer(temp.get(1));

								topicSimulation.setType(topicObj.getString("typeName"));
								topicSimulation.setType_tow(topicType.getUuid());
								topicSimulation.setTitle_parent(topicObj.getString("QuestionContentParent"));
								topicSimulation.setCreate_time(new Date());
								topicSimulation.setSimulation_uuid(simulation.getUuid());
								topicSimulation.setStatus(1);
								topicSimulations.add(topicSimulation);
							}
							Collections.shuffle(topicSimulations);
							Collections.shuffle(topicSimulations);
							ArrayListMultimap<String,TopicSimulation> topicsMultimap = ArrayListMultimap.create();
							for(int p=0;p<topicSimulations.size();p++){
								topicsMultimap.put(topicSimulations.get(p).getTitle_parent(),topicSimulations.get(p));
							}
							List<TopicSimulation> topicSimulationsTemp = new ArrayList<>();
							Set<String> titleParent = topicsMultimap.keySet();
							titleParent.forEach((s)->{
								topicSimulationsTemp.addAll(topicsMultimap.get(s));
							});
							topicSimulationsTemp.forEach((topicSimulation)->{
								topicSimulationRepository.save(topicSimulation);
							});
						}
					}
				}

			}
		}
	}

	// 获取必做题
	@Override
	public void getMustdoTopic(String planId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mtype", "GetMenu");
		params.put("questionPlanID", "3088");
		params.put("orgID", "wuyou.100xuexi.com");
		String url = "http://eshu.100xuexi.com/uploads/ebook/7d42c88aae11459da346a84cff2334d1/mobile/epub/OEBPS/chap1.html";
		String baseurl = "http://eshu.100xuexi.com/uploads/ebook/7d42c88aae11459da346a84cff2334d1/mobile/epub/OEBPS/";
		try {
			Document doc = Jsoup.connect(url).get();
			Elements ps = doc.select("p");
			String parent_uuid = "";
			for (int j = 0; j < ps.size(); j++) {
				Elements as = ps.get(j).select("a");
				if (as.size() > 0) {
					if (ps.get(j).hasClass("CatH1")) {
						TopicMenuMustDo topicMenuMustDo = new TopicMenuMustDo();
						topicMenuMustDo.setUuid(UUIDTool.getUUID());
						topicMenuMustDo.setParent_uuid("0");
						topicMenuMustDo.setName(as.get(0).text());
						topicMenuMustDo.setCreate_time(new Date());
						topicMenuMustDo.setPlan_uuid(planId);
						topicMenuMustDo.setStatus(1);
						topicMenuMustdoRepository.save(topicMenuMustDo);
						parent_uuid = topicMenuMustDo.getUuid();
					} else {
						TopicMenuMustDo topicMenuMustDo = new TopicMenuMustDo();
						topicMenuMustDo.setUuid(UUIDTool.getUUID());
						topicMenuMustDo.setParent_uuid(parent_uuid);
						topicMenuMustDo.setName(as.get(0).text());
						topicMenuMustDo.setCreate_time(new Date());
						topicMenuMustDo.setPlan_uuid(planId);
						topicMenuMustDo.setStatus(1);
						topicMenuMustdoRepository.save(topicMenuMustDo);

						if (true) {
							// 获取每一个章节的题目
							Document doc2 = Jsoup.connect(baseurl + as.attr("href")).get();

							Elements ps2 = doc2.select("p");
							String lastptext = "";
							String topic_type_uuid = "";
							String topic_type = "";
							String select_answer = "";
							String title_parent = "";
							TopicMustDo topicMustDo = new TopicMustDo();
							for (int k = 0; k < ps2.size(); k++) {
								Element p2 = ps2.get(k);
								if (!p2.hasClass("ArtH1") && !p2.hasClass("ArtH1") && !p2.hasClass("PSplit")
										&& !p2.hasClass("FreePoint") && !"".equals(p2.text().trim())) {
									String pText = p2.text().trim(); // p标签内容
									// 题型处理
									if (pText.contains("A1/A2") || pText.contains("B1") || pText.contains("A3/A4")) { // 当前题型
										if (pText.contains("A1") || pText.contains("A2")) {
											pText = "A1/A2型选择题";
										}
										if (pText.contains("B1") || pText.contains("B")) {
											pText = "B1型选择题";
										}
										if (pText.contains("A3") || pText.contains("A4")) {
											pText = "A3/A4型选择题";
										}
										TopicType topicType = topicTypeRepository.findTopicTypeByName(pText);
										if (topicType == null) {
											topicType = new TopicType();
											topicType.setType_name(pText);
											topicType.setDesc("");
											topicType.setCreate_time(new Date());
											topicType.setUuid(UUIDTool.getUUID());
											topicTypeRepository.save(topicType);
										}
										topic_type_uuid = topicType.getUuid();
									}
									if (pText.contains("A1/A2")) {
										topic_type = "A1";
									}
									if (pText.contains("A3/A4")) {
										topic_type = "A3";
									}
									if (pText.contains("B1")) {
										topic_type = "B";
									}
									if (pText.contains("共用题干")) {
										title_parent = "（共用题干）";
									}
									if (pText.contains("共用备选答案")) {
										title_parent = "（共用备选答案）";
									}
									if (lastptext.contains("共用题干") || "（共用题干）".equals(lastptext)) {
										title_parent += "<br>" + pText;
									}
									System.out.println("=======" + pText);
									if ((pText != null && !"".equals(pText) && isStartWithNumber(pText)
											|| (p2.children().size() > 0 && (p2.child(0).hasClass("QuestionNum1")
													|| p2.child(0).hasClass("QuestionNum2")
													|| p2.child(0).hasClass("QuestionNum3"))))
//                                            && (pText.contains("(")
//                                                    || pText.contains("（"))
									) {// 题干
										if (pText.contains(".") || pText.contains("．")) {
											pText = pText.substring(2, pText.length());
										} else {
											pText = pText.substring(1, pText.length());
										}
										if (topic_type.equals("B") && topicMustDo.getTitle() != null
												&& !"".equals(topicMustDo.getTitle())) {
											topicMustDo.setTitle(topicMustDo.getTitle() + "|" + pText);
										} else {
											topicMustDo.setTitle(pText);
										}
									}
									if (isStartWithAZ(pText)
											&& (".".equals(pText.charAt(1) + "") || "．".equals(pText.charAt(1) + ""))) {

										// 选项
										if ("B".equals(topic_type)) {
											title_parent += "<br>" + pText;
										} else {
											select_answer += pText.substring(2, pText.length()) + "|";
										}

									}
									if (p2.hasClass("TagBoxP")
											|| (p2.children().size() > 0 && p2.child(0).hasClass("ComTag"))) {// 答案
										pText = pText.replace("【答案】", "").replace("\n", " ");
										if ("B".equals(topic_type) && pText.length() > 6) {
											String[] titles = topicMustDo.getTitle().split("\\|");
											pText = pText.replaceAll(" +", "|");
											System.out.println("----" + pText);
											String[] answers = pText.split("\\|");

											for (int l = 0; l < titles.length; l++) {
												topicMustDo.setType_tow(topic_type_uuid);
												topicMustDo.setTitle(titles[l]);
												if (answers[l].contains("．")) {
													topicMustDo.setAnswer(answers[l].split("．")[1]);
												} else if (answers[l].contains(".")) {
													topicMustDo.setAnswer(answers[l].split("\\.")[1]);
												}
												topicMustDo.setAnalysis("");
												topicMustDo.setTitle_parent(title_parent);
												topicMustDo.setUuid(UUIDTool.getUUID());
												topicMustDo.setScore("1");
												topicMustDo.setStatus(1);
												topicMustDo.setType(topic_type.equals("A1") ? "single" : "compose");
												topicMustDo.setCreate_time(new Date());
												topicMustDo.setSelect_answer("");
												topicMustDo.setMenu_mustdo_uuid(topicMenuMustDo.getUuid());

												topicMustdoRepository.save(topicMustDo);
												topicMustDo = null;
												topicMustDo = new TopicMustDo();
											}
											select_answer = "";
											// [0].split("．")[1]);
											// System.out.println(s.split("\\|")[1].split("．")[1]);

										} else {
											topicMustDo.setAnswer(pText);
											topicMustDo.setAnalysis("");
											topicMustDo.setTitle_parent(title_parent);
											topicMustDo.setType_tow(topic_type_uuid);
											topicMustDo.setUuid(UUIDTool.getUUID());
											topicMustDo.setScore("1");
											topicMustDo.setStatus(1);
											topicMustDo.setType(topic_type.equals("A1") ? "single" : "compose");
											topicMustDo.setCreate_time(new Date());
											topicMustDo.setSelect_answer(select_answer);
											topicMustDo.setMenu_mustdo_uuid(topicMenuMustDo.getUuid());
											topicMustdoRepository.save(topicMustDo);
											topicMustDo = null;
											topicMustDo = new TopicMustDo();
											select_answer = "";
										}
									}
									lastptext = pText;
								}
							}
						}
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isStartWithNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str.charAt(0) + "");
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean isStartWithAZ(String str) {
		String regex = "^[A-Z]*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher isNum = pattern.matcher(str.charAt(0) + "");
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	@Override
	public void getK51ChapterTopic(String planId) {
		List<String> optionsList = new ArrayList<>();
		optionsList.add("A");
		optionsList.add("B");
		optionsList.add("C");
		optionsList.add("D");
		optionsList.add("E");
		optionsList.add("F");
		optionsList.add("G");
		// 土建施工员 SubjectID=22144 & 22143
		//
		String menuurl = "http://www.k51.com.cn/ExamRoom/OnlinePractice/Chapter.aspx?ChapterID=199&SubjectID=22144";
		String topicurl = "http://www.k51.com.cn/Service/ExamRoom/ExamHandler.ashx";
		try {
			Document doc = Jsoup.connect(menuurl).get();
			// 父级菜单
			Element ps = doc.getElementsByClass("chapercontupfirst").first();
			System.out.println(ps.selectFirst("a").text());
			// 子集菜单
			Elements li = doc.getElementById("chaper0").select("li");
			for (int i = 0; i < li.size(); i++) {
				Element temp = li.get(i);
				System.out.println(
						temp.selectFirst("a").text() + temp.getElementsByClass("chaprigconherf2").attr("data-id"));

				if (i == 0) {
					// 获取题目
					MultiValueMap<String, String> postData = new LinkedMultiValueMap<String, String>();
					postData.add("key", "GetShowTypeName");
					postData.add("CID", temp.getElementsByClass("chaprigconherf2").attr("data-id"));
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
					headers.set("Origin", "http://www.k51.com.cn");
					headers.set("Host", "www.k51.com.cn");
					headers.set("User-Agent",
							"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");
					HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
							postData, headers);
					String json = restTemplate.postForEntity(topicurl, request, String.class).getBody();
					JSONObject topic = JSONObject.parseObject(json);
					JSONArray showType = topic.getJSONArray("showType");
					for (int j = 0; j < showType.size(); j++) {
						JSONObject object = showType.getJSONObject(j);
						System.out.println("题型--" + object.getString("showTypeName"));
						JSONArray topics = object.getJSONArray("quesNormalData");
						for (int k = 0; k < topics.size(); k++) {
							JSONObject topic1 = topics.getJSONObject(k);
							System.out.println("题干--" + topic1.getString("titleText"));
							System.out.println("分析--" + topic1.getString("thinking"));
							System.out.println("选项--" + topic1.getString("jsonStr"));

							JSONObject optionObj = JSONObject.parseObject(topic1.getString("jsonStr"));
							String answerID = optionObj.getString("answerID");
							String answerOption = "";
							System.out.println("答案编号--" + answerID);

							JSONArray options = optionObj.getJSONArray("options");
							StringBuffer answers = new StringBuffer();
							for (int l = 0; l < options.size(); l++) {
								JSONObject option = options.getJSONObject(l);
								String iD = option.getString("iD");
								if (answerID.equals(iD)) {
									answerOption = optionsList.get(l);
								}
								answers = answers.append(option.get("optionText")).append("|");
							}
							System.out.println("选项--" + answers.toString());
							System.out.println("答案--" + answerOption);
						}
					}
				}

			}
			String parent_uuid = "";
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void getJHChapterTopic(String planId, String filename) {
		List<String> optionsList = new ArrayList<>();
		optionsList.add("A");
		optionsList.add("B");
		optionsList.add("C");
		optionsList.add("D");
		optionsList.add("E");
		optionsList.add("F");
		optionsList.add("G");
		optionsList.add("H");
		optionsList.add("I");
		optionsList.add("J");
		optionsList.add("K");
		optionsList.add("L");
		optionsList.add("M");
		try {
			String path = "/Users/guoxin/Downloads/";
			InputStream is = new FileInputStream(path+filename);
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
			XSSFRow titleCell = xssfSheet.getRow(0);
			
			//预先生成目录
			TopicMenuChapter menuChapter0 = new TopicMenuChapter();
			menuChapter0.setName("目录");
			menuChapter0.setUuid(UUIDTool.getUUID());
			menuChapter0.setCreate_time(new Date());
			menuChapter0.setParent_uuid("0");
			menuChapter0.setPlan_uuid(planId);
			menuChapter0.setStatus(1);
			menuChapter0 = planCatalogRepository.save(menuChapter0);
			
			for (int i = 1; i <= xssfSheet.getLastRowNum(); i++) {
				XSSFRow xssfRow = xssfSheet.getRow(i);
				int minCell = xssfRow.getFirstCellNum();
				int maxCell = xssfRow.getLastCellNum();
				XSSFCell xz_desc = xssfRow.getCell(0);
				XSSFCell xz_category = xssfRow.getCell(1);
				XSSFCell analysis = xssfRow.getCell(2);
				XSSFCell paper_name = xssfRow.getCell(3);
				XSSFCell content = xssfRow.getCell(4);
				XSSFCell type1 = xssfRow.getCell(5);
				
				
				//处理章节菜单
				String name = xz_category.getStringCellValue().trim();
				TopicMenuChapter menuChapter = planCatalogRepository.findByNameAndPlanUuid(name, planId);
				if(menuChapter==null) {
					menuChapter = new TopicMenuChapter();
					menuChapter.setName(name);
					menuChapter.setUuid(UUIDTool.getUUID());
					menuChapter.setCreate_time(new Date());
					menuChapter.setParent_uuid(menuChapter0.getUuid());
					menuChapter.setPlan_uuid(planId);
					menuChapter.setStatus(1);
					menuChapter = planCatalogRepository.save(menuChapter);
				}
				
				//处理答案及选项
				String answer = "";
				String select = "";
				String type = "single";
				String type_row = "单选题";
				if(content!=null) {
					String contents = content.getStringCellValue().trim();
					String[] answers = contents.split("\\|");
					for(int j = 0;j<answers.length;j++) {
						String[] temps = answers[j].split(":");
						if(!select.contains(temps[0].trim()+"|")) {
							select += temps[0].trim()+"|";
							if("1".equals(temps[1])) {
								answer += optionsList.get(j)+",";
							}
						}
					}
					if(answer.length()>1) {
						type = "compose";
						type_row = "多选题";
					}
				}
				
				if(type1!=null && type1.getStringCellValue().equals("1")) {
					type_row = "单选题";
					type = "single";
				}else if(type1!=null && type1.getStringCellValue().equals("2")) {
					type_row = "多选题";
					type = "compose";
				}
				
				if(select.endsWith(",")) {
					select = select.substring(0,select.length()-1);
				}
				if(answer.endsWith(",")) {
					answer = answer.substring(0,answer.length()-1);
				}
				TopicType topicType = topicTypeRepository.findTopicTypeByName(type_row);
				if(topicType==null) {
					topicType = new TopicType();
					topicType.setType_name(type_row);
					topicType.setDesc(type_row);
					topicType.setCreate_time(new Date());
					topicType.setUuid(UUIDTool.getUUID());
					topicType = topicTypeRepository.save(topicType);
				}
				
				
				String xz_descs = xz_desc.getStringCellValue().trim().replace("<p>", "").replace("</p>", "");
				System.out.println(xz_descs);
				int index = xz_descs.indexOf("、");
				if(index<5 && index>0) {
					xz_descs = xz_descs.substring(index+1, xz_descs.length());
				}
				index = xz_descs.indexOf(",");
				if(index<5 && index>0) {
					xz_descs = xz_descs.substring(index+1, xz_descs.length());
				}
				System.out.println(xz_descs);
				TopicChapter topicChapter = new TopicChapter();
				topicChapter.setAnalysis(analysis.getStringCellValue().trim());
				topicChapter.setUuid(UUIDTool.getUUID());
				topicChapter.setTitle(xz_descs);
				topicChapter.setScore("1");
				topicChapter.setSelect_answer(select);
				topicChapter.setAnswer(answer);
				topicChapter.setType(type);
				topicChapter.setType_tow(topicType.getUuid());
				topicChapter.setTitle_parent("");
				topicChapter.setCreate_time(new Date());
				topicChapter.setPlan_catalog_uuid(menuChapter.getUuid());
				topicChapter.setStatus(1);
				
				topicChapterRepository.save(topicChapter);
				
				
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
