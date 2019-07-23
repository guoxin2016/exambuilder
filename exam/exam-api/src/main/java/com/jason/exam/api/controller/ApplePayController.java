package com.jason.exam.api.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jason.exam.model.Order;
import com.jason.exam.model.common.R;
import com.jason.exam.service.api.IAppService;
import com.jason.exam.service.api.IOrderService;
import com.jason.exam.service.api.IPayService;

@Controller
@RequestMapping("applepay")
public class ApplePayController {
	
	@Autowired
	IPayService payService;
	
	@Autowired
	IOrderService orderService;
	
	// 购买凭证验证地址
	private static final String certificateUrl = "https://buy.itunes.apple.com/verifyReceipt";

	// 测试的购买凭证验证地址
	private static final String certificateUrlTest = "https://sandbox.itunes.apple.com/verifyReceipt";

	/**
	 * 重写X509TrustManager
	 */
	private static TrustManager myX509TrustManager = new X509TrustManager() {

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

		}

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

		}
	};
	
	
	@RequestMapping("setIapCertificate")
	@ResponseBody
    public R papers(@RequestParam Map<String, String> params) {
		R r = null;
		String userUuid = params.get("userUuid");
		String receipt = params.get("receipt");
		String chooseEnv = params.get("chooseEnv");
		String orderUuid = params.get("orderUuid");
		if(StringUtils.isEmpty(userUuid) || StringUtils.isEmpty(receipt)){
			return null;
		}
		String url = null;
		if("1".equals(chooseEnv)) {
			url = certificateUrl;
		}else {
			url = certificateUrlTest;
		}
		final String certificateCode = receipt;
		if(StringUtils.isNotEmpty(certificateCode)){
			String resultString = sendHttpsCoon(url, certificateCode);
			 if(resultString.contains("\"status\":0")){//说明支付成功
				//支付成功
				//更新用户权限
				//先更新用户权限
				Order order = orderService.findOrderByUUid(orderUuid);
				if(order!=null) {
					payService.updateUserAuth(order);
					//更新order 的状态
					orderService.updateStatus(order);
				}
				r = R.isOk();
	        }else if(resultString.contains("\"status\":21007")){
	        	String resultString2 = sendHttpsCoon(certificateUrlTest, certificateCode);
				 if(resultString2.contains("\"status\":0")){//说明支付成功
					//支付成功
						//更新用户权限
						//先更新用户权限
						Order order = orderService.findOrderByUUid(orderUuid);
						if(order!=null) {
							payService.updateUserAuth(order);
							//更新order 的状态
							orderService.updateStatus(order);
						}
						r = R.isOk();
		        }else{
		        	r = R.isFail();
		        }
	        }else{
	        	r = R.isFail();
	        }
		}
		return r;
	}
	
	/**
	 * 发送请求
	 * @param url
	 * @param strings
	 * @return
	 */
	private String sendHttpsCoon(String url, String code){
		if(url.isEmpty()){
			return null;
		}
		try {
			//设置SSLContext
			SSLContext ssl = SSLContext.getInstance("SSL");
			ssl.init(null, new TrustManager[]{myX509TrustManager}, null);
			
			//打开连接
			HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
			//设置套接工厂
			conn.setSSLSocketFactory(ssl.getSocketFactory());
			//加入数据
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type","text/json");
//			conn.setRequestProperty("Content-type","application/json");
            conn.setRequestProperty("Proxy-Connection", "Keep-Alive");  
            conn.setDoInput(true);  
            conn.setDoOutput(true);  
			
			
			
	        JSONObject obj = new JSONObject();
	        obj.put("receipt-data", IOS_Verify.getBASE64(code));
	        
	        BufferedOutputStream buffOutStr = new BufferedOutputStream(conn.getOutputStream());
	        String str= String.format(Locale.CHINA,"{\"receipt-data\":\"" + IOS_Verify.getBASE64(code)+"\"}");
	        //buffOutStr.write(obj.toString().getBytes());
	        buffOutStr.write(str.getBytes());
	        buffOutStr.flush();
	        buffOutStr.close();
	        
	        //获取输入流
	        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String line = null;
	        StringBuffer sb = new StringBuffer();
	        while((line = reader.readLine())!= null){
	        	sb.append(line);
	        }
	        System.out.println(sb.toString());
	        return sb.toString();
		
		} catch (Exception e) {
			return null;
		}
	}

}
