//package com.xuebashangxian.exam.util;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.jfinal.plugin.activerecord.Db;
//import com.jfinal.plugin.activerecord.Record;
//
//import io.jboot.Jboot;
//
///**
// * 
//* @ClassName: GetTopicUtil
//* @Description: 获取模拟
//* @author guoxin
//* @date 2018年6月2日 下午8:25:21
//*
// */
//public class GetNewsTopicUtil
//{
//    private final static Logger logger_hushi = LoggerFactory.getLogger("log_hushi");
//    public static void main(String[] args) throws Exception
//    {
//        Jboot.run(args);
//        String baseUrl = "http://www.med66.com/chujihushi/fudaoziliao";
//        String baseUrl2 = "http://www.med66.com";
//        //获取护师试卷目录
//        for(int i=35;i<=288;i++) {//288
//            String url = baseUrl + i + ".shtm";
//            System.out.println(url);
//            Document doc = Jsoup.connect(url).get();
//            
//            Elements uls = doc.select("ul");
//            Elements lis = uls.get(0).select("li");
//            for (Element element : lis)
//            {
//                if(!element.hasClass("xian")) {
//                    Element a = element.select("a").get(0);
//                    System.out.println(a.text() + a.attr("href"));
//                    //详细内容
//                    Document doc2 = Jsoup.connect(baseUrl2+a.attr("href")).get();
//                    if(doc2.getElementsByClass("news_con").size()>0) {
//                        Element div = doc2.getElementsByClass("news_con").get(0);
//                        Elements as  = div.select("a");
//                        for(Element aa : as) {
//                            aa.removeAttr("href");
//                            aa.removeAttr("class");
//                            aa.removeAttr("target");
//                            aa.removeAttr("title");
//                        }
//                        div.select("p").get(0).remove();
//                        if(div.getElementsByClass("editor").size()>0) {
//                            div.getElementsByClass("editor").get(0).remove();
//                        }
//                        
//                        Record news = new Record();
//                        news.set("uuid", UUIDTool.getUUID());
//                        news.set("createtime", TimeTool.getCurrectTime());
//                        news.set("title", a.text());
//                        news.set("content", div.html());
//                        news.set("status", 0);
//                        news.set("author", "");
//                        news.set("type", 1);
//                        news.set("source", baseUrl2+a.attr("href"));
//                        news.set("plan_uuid", "16425af48b6c4b3ebebbe8f1cede5a14");
//                        Db.save("tb_news", news);
//                    }else {
//                        
//                        Element div = doc2.getElementById("fontzoom");
//                        Elements as  = div.select("a");
//                        for(Element aa : as) {
//                            aa.removeAttr("href");
//                            aa.removeAttr("class");
//                            aa.removeAttr("target");
//                            aa.removeAttr("title");
//                        }
//                        div.select("p").get(0).remove();
//                        if(div.getElementsByClass("editor").size()>0) {
//                            div.getElementsByClass("editor").get(0).remove();
//                        }
//                        
//                        Record news = new Record();
//                        news.set("uuid", UUIDTool.getUUID());
//                        news.set("createtime", TimeTool.getCurrectTime());
//                        news.set("title", a.text());
//                        news.set("content", div.html());
//                        news.set("status", 0);
//                        news.set("author", "");
//                        news.set("type", 1);
//                        news.set("source", baseUrl2+a.attr("href"));
//                        news.set("plan_uuid", "16425af48b6c4b3ebebbe8f1cede5a14");
//                        Db.save("tb_news", news);
//                    }
//                    
//                }
//                
//            }
//        }
//        
//        
//        
//        
//        
//    }
//}
