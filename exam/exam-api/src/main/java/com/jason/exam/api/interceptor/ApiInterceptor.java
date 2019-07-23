package com.jason.exam.api.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.jason.exam.api.App;
import com.jason.exam.api.response.RestResp;
import com.jason.exam.api.response.RestRespError;
import com.jason.exam.api.util.MD5Util2;
import com.jason.exam.service.api.IChannelService;

/**
 * 验证接口权限
 * @author guoxin
 *
 */
@Service
public class ApiInterceptor implements HandlerInterceptor{
	
	@Autowired
	IChannelService channelService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Map<String, String> reqParams = new HashMap<>();
		Enumeration<String> paramsNames = request.getParameterNames();
        while (paramsNames.hasMoreElements()) {
            String paramsName = paramsNames.nextElement();
            reqParams.put(paramsName, request.getParameter(paramsName));
        }
		try {
			RestResp resp = checkReqeustParam(request,reqParams);
			if(resp.getStatus().equals(RestRespError.success.getCode())) {
				return true;
			}else {
				returnJson(response, JSONObject.toJSONString(resp));
			}
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
	}
	
	private void returnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
//            logger.error("response error",e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
	
	protected RestResp checkReqeustParam(HttpServletRequest request , Map<String, String> reqPara) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        RestResp resp = new RestResp();
        try {
            if (reqPara.containsKey("channelid")&&
                    reqPara.containsKey("devid")&&reqPara.containsKey("sign")&&reqPara.containsKey("time")) {
                String signkey = App.CHANNEl_MAP.get(reqPara.get("devid"));
                Map<String, Object> channel = channelService.findChannelByDevId(reqPara.get("devid"));
                if (signkey == null && (signkey = channel.get("signKey").toString()) == null) {
                    resp.setStatus(RestRespError.devid_invalid.getCode());
                    resp.setMsg(RestRespError.devid_invalid.getMsg());
                }else if (Calendar.getInstance().getTime().getTime() - dateTimeFormat.parse(reqPara.get("time")).getTime() > 3000000000L) {//超过5分钟
                    resp.setStatus(RestRespError.outdated_request.getCode());
                    resp.setMsg(RestRespError.outdated_request.getMsg());
                }else {
//                    String linkStr = request.getServletPath() + "?" + createLinkString(reqPara) + signkey;
                    String linkStr = "?" + createLinkString(reqPara) + signkey;
                    System.out.println("111"+linkStr);
                    if (reqPara.get("sign").equals(MD5Util2.MD5(linkStr))) {
                        resp.setStatus(RestRespError.success.getCode());
                        resp.setMsg(RestRespError.success.getMsg());
                    }else {
                        resp.setStatus(RestRespError.sign_invalid.getCode());
                        resp.setMsg(RestRespError.sign_invalid.getMsg());
                    }
                }
            } else {
                resp.setStatus(RestRespError.Required_base_parameter_missing.getCode());
                resp.setMsg(RestRespError.Required_base_parameter_missing.getMsg());
            }
        } catch (Exception e) {
            resp.setStatus(RestRespError.system_error.getCode());
            resp.setMsg(RestRespError.system_error.getMsg());
            e.printStackTrace();
        }
        return resp;
    }
	
	
	public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            if ("sign".equals(key)) {
                continue;
            }
            String value = params.get(key);
            prestr = prestr + key + "=" + value + "&";
        }
        return prestr.substring(0, prestr.length() - 1);
    }
	
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    	System.out.println("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    	System.out.println("---------------视图渲染之后的操作-------------------------0");
    }
}
