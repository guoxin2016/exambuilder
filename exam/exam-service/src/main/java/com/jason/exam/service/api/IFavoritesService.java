package com.jason.exam.service.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.jason.exam.model.Favorites;

public interface IFavoritesService {
	
	Favorites addFavorites(Map<String, String> params);
	
	void removeFavorites(Map<String, String> params);

	Page<Map<String, Object>> getFavoritesByType(Map<String, String> params);
    
}
