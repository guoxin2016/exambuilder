package com.jason.exam.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jason.exam.model.Menu;
import com.jason.exam.model.common.R;
import com.jason.exam.service.IMenuService;

@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController{
	
    @Autowired
    IMenuService menuService; 
	
	@RequestMapping(value = "/listview")
    public String listview() {
		
        return "/admin/menulist";
    }
	
	@RequestMapping(value = "/treedata")
	@ResponseBody
    public List<Menu> treedata() {
		
		List<Menu> datas = menuService.findMenuAll();
		
		return datas;
    }
	
	@RequestMapping(value = "/roleadd")
    public String roleadd(@RequestParam("id") int id,Model model) {
		System.out.println(id);
		if(id !=0) {
			Menu menu = menuService.findMenuById(id);
			model.addAttribute("menu", menu);
		}
        return "/admin/roleadd";
    }
	
	@RequestMapping(value = "/rolesave")
	@ResponseBody
	public R rolesave(@RequestParam Map<String, Object> data) {
		System.out.println(data);
		try {
			Menu menu = (Menu) convertMapToBean(data, Menu.class);
			menu = menuService.menuSave(menu);
			return R.isOk().data(menu);
		} catch (InstantiationException e) {
			e.printStackTrace();
			return R.isFail(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return R.isFail(e);
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
	}
	
	@RequestMapping(value = "/menudelete")
	@ResponseBody
	public R menudelete(@RequestParam("id") int id) {
		try {
			menuService.menuDeleteById(id);
			return R.isOk();
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
	}
	
}
