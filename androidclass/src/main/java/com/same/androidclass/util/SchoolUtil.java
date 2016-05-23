package com.same.androidclass.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;


/**
 *
 * Created by alic on 16-4-28.
 */
public class SchoolUtil {
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

    /**
     * 学生登陆myscse的URL
     */
    public static final String LOGIN_URL="http://class.sise.com.cn:7001/sise/login_check.jsp";

    /**
     * 登陆成功后的url菜单list
     */
    public static final String MYSCSE_MENU_URL="http://class.sise.com.cn:7001/sise/module/student_states/student_select_class/main.jsp";

    /**
     * 学生个人信息的URL
     * 注意 后面需要接上学生的id
     */
    public static final String STUDENT_MESSAGE_URL="http://class.sise.com.cn:7001/SISEWeb/pub/course/courseViewAction.do?method=doMain&";
    /**
     * post方式模拟header之user-agent
     */
    public static final String BROWSER_USER_AGENT="Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:45.0) Gecko/20100101 Firefox/45.0";
    /**
     * post方式模拟header之Host
     */
    public static final String BROWSER_HOST = "class.sise.com.cn:7001";
}
