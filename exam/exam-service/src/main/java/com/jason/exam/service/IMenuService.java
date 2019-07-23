package com.jason.exam.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.jason.exam.model.Menu;

public interface IMenuService {
    Page<Menu> findMenuNoCriteria(Integer page,Integer size);

    Menu menuSave(Menu role);

    Menu findMenuById(int id);

	void menuDeleteById(int id);

	List<Menu> findMenuAll();
}
