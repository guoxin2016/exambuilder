package com.jason.exam.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jason.exam.model.common.Pager;
import com.jason.exam.model.common.R;
import com.jason.exam.service.api.IMistakeCollectionService;


@Controller
@RequestMapping("errors")
public class MistakesCollectionController
{
    @Autowired
    IMistakeCollectionService mistakeCollectionService; 

    /**
     * 添加错题
     */
    @RequestMapping("add")
	@ResponseBody
    public R papers(@RequestParam Map<String, String> params)
    {
    	try {
    		mistakeCollectionService.addMistake(params);
			return R.isOk();
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
    }
    
    /**
     * 添加错题
     */
    @RequestMapping("addbatch")
	@ResponseBody
    public R addbatch(@RequestParam Map<String, String> params)
    {
    	try {
    		mistakeCollectionService.addMistakeBatch(params);
			return R.isOk();
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
    		mistakeCollectionService.removeMistake(params);
    		return R.isOk();
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
    }
    
    /**
    * 取消错误batch
    */
   @RequestMapping("removebatch")
	@ResponseBody
   public R removebatch(@RequestParam Map<String, String> params)
   {
   	try {
   		mistakeCollectionService.removeMistakeBatch(params);
   		return R.isOk();
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
   }
    
    /**
     * 获取错误内容
     */
    @RequestMapping("data")
	@ResponseBody
    public Pager menu(@RequestParam Map<String, String> params)
    {
    	try {
    		Page<Map<String, Object>> datas = mistakeCollectionService.getMistakeCollectionByType(params);
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
