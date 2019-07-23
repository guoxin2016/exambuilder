package com.jason.exam.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jason.exam.model.Favorites;
import com.jason.exam.model.common.Pager;
import com.jason.exam.model.common.R;
import com.jason.exam.service.api.IFavoritesService;


@Controller
@RequestMapping("favorites")
public class FavoritesController
{
    @Autowired
    IFavoritesService favoritesService;

    /**
     * 添加收藏
     */
    @RequestMapping("add")
	@ResponseBody
    public R papers(@RequestParam Map<String, String> params)
    {
    	try {
			Favorites favorites = favoritesService.addFavorites(params);
			return R.isOk().data(favorites);
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
    }
    
    /**
     * 取消收藏
     */
    @RequestMapping("remove")
	@ResponseBody
    public R remove(@RequestParam Map<String, String> params)
    {
    	try {
    		favoritesService.removeFavorites(params);
    		return R.isOk();
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
    }
    
    /**
     * 获取收藏内容
     */
    @RequestMapping("data")
	@ResponseBody
    public Pager menu(@RequestParam Map<String, String> params)
    {
    	try {
    		Page<Map<String, Object>> datas = favoritesService.getFavoritesByType(params);
    		Pager p = Pager.isOk();
    		p.setData(datas.getContent());
    		p.setRecordsTotal(datas.getTotalElements());
			return p;
		} catch (Exception e) {
			e.printStackTrace();
			return Pager.isFail();
		}
    }
    
}
