package com.jason.exam.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.jason.exam.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer>,JpaSpecificationExecutor<Menu> {

}
