package com.jason.exam.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jason.exam.model.Role;
import com.jason.exam.model.common.Pager;
import com.jason.exam.model.common.R;
import com.jason.exam.service.IRoleService;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	
    @Autowired
    IRoleService roleService; 
	
	@RequestMapping(value = "/listview")
    public String listview() {
		
        return "/admin/rolelist";
    }
	
	@RequestMapping(value = "/listdata")
	@ResponseBody
    public Pager listdata(Integer draw,Integer start,Integer length) {
		
		int page = start/length;
		
		Page<Role> datas = roleService.findRoleNoCriteria(page, length);
		Pager p = Pager.isOk();
		p.setDraw(draw);
		p.setData(datas.getContent());
		p.setRecordsTotal(datas.getTotalElements());
		p.setRecordsFiltered(datas.getTotalElements());
		return p;
    }
	
	@RequestMapping(value = "/roleadd")
    public String roleadd(@RequestParam("id") int id,Model model) {
		System.out.println(id);
		if(id !=0) {
			Role role = roleService.findRoleById(id);
			model.addAttribute("role", role);
		}
        return "/admin/roleadd";
    }
	
	@RequestMapping(value = "/rolesave")
	@ResponseBody
	public R rolesave(@RequestParam Map<String, Object> data) {
		System.out.println(data);
		try {
			Role role = (Role) convertMapToBean(data, Role.class);
			role = roleService.roleSave(role);
			return R.isOk().data(role);
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
	
	@RequestMapping(value = "/roledelete")
	@ResponseBody
	public R roledelete(@RequestParam("id") int id) {
		try {
			roleService.roleDeleteById(id);
			return R.isOk();
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
	}
	
}
