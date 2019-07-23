package com.jason.exam.api.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jason.exam.model.common.R;
import com.jason.exam.service.api.IAppService;


@Controller
@RequestMapping("app")
public class AppController
{
    @Autowired
    IAppService appService;

    /**
     * 章节菜单
     */
    @RequestMapping("u")
	@ResponseBody
    public R papers(@RequestParam Map<String, String> params)
    {
    	try {
			String uuid = params.get("uuid");
			Map<String, Object> data= appService.findByUuid(uuid);
			return R.isOk().data(data);
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
    }
    
    @RequestMapping("/download")
    public String downloadFile(String fileName,HttpServletRequest request,
                               HttpServletResponse response) throws UnsupportedEncodingException {

        // 如果文件名不为空，则进行下载
        if (fileName != null) {
            //设置文件路径
//            String realPath = "/Users/guoxin/Downloads/";
            String realPath = "/home/jason/package/";
            File file = new File(realPath, fileName);

            // 如果文件名存在，则进行下载
            if (file.exists()) {

                // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

                // 实现文件下载
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("Download the apk successfully!");
                }
                catch (Exception e) {
                    System.out.println("Download the apk failed!");
                }
                finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }else {
            	System.out.println("File not exist! Download the apk failed!");
            }
        }
        return null;
    }

    
}
