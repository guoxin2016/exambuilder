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
import com.jason.exam.dao.repository.api.FavoritesRepository;
import com.jason.exam.model.Favorites;
import com.jason.exam.service.api.IFavoritesService;

@Service
public class FavoritesServiceImpl implements IFavoritesService{
	
	@Resource
    FavoritesRepository favoritesRepository;

	@Override
	public Favorites addFavorites(Map<String, String> params) {
		String uuid2 = params.get("uuid2");
		String plan_uuid = params.get("planUuid");
		String menu_uuid = params.get("menuUuid");
		String type = params.get("type");
		String user_uuid = params.get("userUuid");
		Favorites favorites = new Favorites();
		favorites.setMenu_uuid(menu_uuid);
		favorites.setPlan_uuid(plan_uuid);
		favorites.setStatus(1);
		favorites.setCreatetime(new Date());
		favorites.setType(Integer.valueOf(type));
		favorites.setUser_uuid(user_uuid);
		favorites.setUuid2(uuid2);
		favorites.setUuid(UUIDTool.getUUID());
		favorites = favoritesRepository.save(favorites);
		return favorites;
	}

	@Override
	public void removeFavorites(Map<String, String> params) {
		String uuid = params.get("uuid");
		favoritesRepository.deleteByUuid(uuid);
	}

	@Override
	public Page<Map<String, Object>> getFavoritesByType(Map<String, String> params) {
		String type = params.get("type");//1:章节练习  2:咨询  3:真题 4:必做 5:模拟考试
		String planUuid = params.get("planUuid");
		String userUuid = params.get("userUuid");
		String pageNumber = params.get("pageNumber");
		String pageSize = params.get("pageSize");
		Pageable pageable = new PageRequest(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), Sort.Direction.ASC, "id");
		Page<Map<String, Object>> page = null;
		switch (type) {
		case "1"://章节练习
			page = favoritesRepository.getFavoritesChapterTopicsByTypeAndPlanUuidAndUserUuid(planUuid,userUuid,type,pageable);
			break;
		case "2"://咨询
			page = favoritesRepository.getFavoritesNewsByTypeAndPlanUuidAndUserUuid(planUuid,userUuid,type,pageable);
			break;
		case "3"://真题
			page = favoritesRepository.getFavoritesOldExamTopicsByTypeAndPlanUuidAndUserUuid(planUuid,userUuid,type,pageable);
			break;
		case "4"://必做
			page = favoritesRepository.getFavoritesMustdoTopicsByTypeAndPlanUuidAndUserUuid(planUuid,userUuid,type,pageable);
			break;
		case "5"://模拟考试
			page = favoritesRepository.getFavoritesSimulationTopicsByTypeAndPlanUuidAndUserUuid(planUuid,userUuid,type,pageable);
			break;

		default:
			break;
		}
		return page;
	}

	

}
