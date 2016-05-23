package com.same.androidclass.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 *
 * Created by alic on 16-4-28.
 */
public class NetworkUtil {
    /**
     * 获取登陆input 随机变的数
     *
     * @return name value
     */
    public static String[] getHiddenMap() {
        String[] hidden = new String[2];
        try {
            Document document = Jsoup.connect("http://class.sise.com.cn:7001/sise/").get();
            Element hiddenElement = document.getElementsByTag("input").first();
            hidden[0] = hiddenElement.attr("name");
            hidden[1] = hiddenElement.attr("value");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hidden;
    }
}
