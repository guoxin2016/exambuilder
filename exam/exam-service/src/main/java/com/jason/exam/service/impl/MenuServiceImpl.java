package com.jason.exam.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jason.exam.dao.repository.MenuRepository;
import com.jason.exam.model.Menu;
import com.jason.exam.service.IMenuService;

@Service
public class MenuServiceImpl implements IMenuService{

    @Resource
    MenuRepository menuRepository;
	
	@Override
	public Page<Menu> findMenuNoCriteria(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
        return menuRepository.findAll(pageable);
	}

	@Override
	public Menu menuSave(Menu menu) {
		Menu m = menuRepository.save(menu);
		return m;
	}

	@Override
	public Menu findMenuById(int id) {
		Menu m = menuRepository.findById(id).get();
		return m;
	}

	@Override
	public void menuDeleteById(int id) {
		if(menuRepository.existsById(id)) {
			menuRepository.deleteById(id);
		}
	}

	@Override
	public List<Menu> findMenuAll() {
		Sort sort = new Sort(Sort.Direction.ASC,"seq");
		List<Menu> menus = menuRepository.findAll(sort);
		return menus;
	}

}
