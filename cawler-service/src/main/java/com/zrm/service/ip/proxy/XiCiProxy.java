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
            System.out.println(document.html());
            Element table = document.getElementById("ip_list");
            if(table != null){
                Element tbody = table.child(0);
                Elements elements = tbody.children();
                for(int i=0;i<elements.size();i++){
                    Element trElement = elements.get(i);
                    if(trElement.hasAttr("class") && !trElement.hasClass("subtitle")){
                        //System.out.println(trElement.html());
                        System.out.println("----------------");
                        Elements tds = trElement.children();
                        System.out.println("ip="+tds.get(1).text()+",port="+tds.get(2).text()+",protcol="+tds.get(5).text());
                    }
                }
            }

        }
    }
}
