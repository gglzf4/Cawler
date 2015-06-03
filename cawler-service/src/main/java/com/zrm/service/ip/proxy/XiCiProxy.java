package com.zrm.service.ip.proxy;

import com.zrm.cawler.core.utils.HttpKit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author 张锐敏
 * @version V1.0
 * @description
 * @time 2015-06-03 17:44
 */
public class XiCiProxy implements IpProxy{

    public static void main(String[] args){


        String content = null;
        try {
            content = HttpKit.get("http://www.xici.net.co");

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(content != null){
            Document document = Jsoup.parse(content);
            Element table = document.getElementById("ip_list");
            Elements elements = table.children();
            for(Element trElement : elements){
                if(!trElement.hasClass("subtitle")){
                    Elements tds = trElement.getElementsByTag("td");
                    for(Element td:tds){
                        System.out.println("ip="+tds.get(1).text()+",port="+tds.get(2).text()+",protcol="+tds.get(5).text());
                    }
                }
            }
        }
    }
}
