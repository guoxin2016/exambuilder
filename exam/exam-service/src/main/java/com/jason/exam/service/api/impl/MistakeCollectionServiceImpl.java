package com.jason.exam.service.api.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jason.exam.common.util.UUIDTool;
import com.jason.exam.dao.repository.api.MistakeCollectionRepository;
import com.jason.exam.model.MistakeCollection;
import com.jason.exam.service.api.IMistakeCollectionService;

@Service
public class MistakeCollectionServiceImpl implements IMistakeCollectionService{
	
	@Resource
    MistakeCollectionRepository mistakeCollectionRepository;

	@Override
	public MistakeCollection addMistake(Map<String, String> params) {
		String uuid2 = params.get("uuid2");
		String plan_uuid = params.get("planUuid");
		String menu_uuid = params.get("menuUuid");
		String type = params.get("type");
		String user_uuid = params.get("userUuid");
		MistakeCollection collection = new MistakeCollection();
		collection.setMenu_uuid(menu_uuid);
		collection.setPlan_uuid(plan_uuid);
		collection.setStatus(1);
		collection.setCreatetime(new Date());
		collection.setType(Integer.valueOf(type));
		collection.setUser_uuid(user_uuid);
		collection.setUuid2(uuid2);
		collection.setUuid(UUIDTool.getUUID());
		collection = mistakeCollectionRepository.save(collection);
		return collection;
	}

	@Override
	public void removeMistake(Map<String, String> params) {
		String uuid = params.get("uuid");
		mistakeCollectionRepository.deleteByUuid(uuid);
	}

	@Override
	public Page<Map<String, Object>> getMistakeCollectionByType(Map<String, String> params) {
		String type = params.get("type");//1:章节练习  3:真题 4:必做 5:模拟考试
		String planUuid = params.get("planUuid");
		String userUuid = params.get("userUuid");
		String pageNumber = params.get("pageNumber");
		String pageSize = params.get("pageSize");
		Pageable pageable = new PageRequest(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), Sort.Direction.ASC, "id");
		Page<Map<String, Object>> page = null;
		switch (type) {
		case "1"://章节练习
			page = mistakeCollectionRepository.getMistakesChapterTopicsByTypeAndPlanUuidAndUserUuid(planUuid,userUuid,type,pageable);
			break;
		case "3"://真题
			page = mistakeCollectionRepository.getMistakesOldExamTopicsByTypeAndPlanUuidAndUserUuid(planUuid,userUuid,type,pageable);
			break;
		case "4"://必做
			page = mistakeCollectionRepository.getMistakesMustdoTopicsByTypeAndPlanUuidAndUserUuid(planUuid,userUuid,type,pageable);
			break;
		case "5"://模拟考试
			page = mistakeCollectionRepository.getMistakesSimulationTopicsByTypeAndPlanUuidAndUserUuid(planUuid,userUuid,type,pageable);
			break;

		default:
			break;
		}
		return page;
	}

	@Override
	public void addMistakeBatch(Map<String, String> params) {
		String uuid2 = params.get("uuid2");
		String plan_uuid = params.get("planUuid");
		String menu_uuid = params.get("menuUuid");
		String type = params.get("type");
		String user_uuid = params.get("userUuid");
		String [] uuid2s = uuid2.split(",");
		for(int i=0;i<uuid2s.length;i++) {
			MistakeCollection collection = mistakeCollectionRepository.findByUuid2(uuid2s[i]);
			if(collection == null) {
				collection = new MistakeCollection();
				collection.setMenu_uuid(menu_uuid);
				collection.setPlan_uuid(plan_uuid);
				collection.setStatus(1);
				collection.setCreatetime(new Date());
				collection.setType(Integer.valueOf(type));
				collection.setUser_uuid(user_uuid);
				collection.setUuid2(uuid2s[i]);
				collection.setUuid(UUIDTool.getUUID());
				collection = mistakeCollectionRepository.save(collection);
			}
		}
	}

	@Override
	public void removeMistakeBatch(Map<String, String> params) {
		String uuid = params.get("uuid");
		String [] uuids = uuid.split(",");
		for(int i=0;i<uuids.length;i++) {
			mistakeCollectionRepository.deleteByUuid(uuids[i]);
		}
	}

	

}
