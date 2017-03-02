package com.wc.android.demo;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析城市
 * Created by Administrator on 2017/2/23.
 */

public class PulCityService {
    public static List<City> getCitys(String xml) throws Exception {
        List<City> citys = null;
        City city = null;
        XmlPullParser parser = Xml.newPullParser();//得到Pull解析器
        parser.setInput(new ByteArrayInputStream(xml.getBytes()), "UTF-8");//设置下输入流的编码
        int eventType = parser.getEventType();//得到第一个事件类型
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT://如果是文档开始事件
                    citys = new ArrayList<>();//  创建一个person集合
                    break;
                case (XmlPullParser.START_TAG)://如果遇到标签开始
                    String tagName = parser.getName();// 获得解析器当前元素的名称
//                    Log.v("MainActivity", "parser.getName()" + tagName);
                    if ("string".equals(tagName)) {//如果当前标签名称是<string>
                        city = new City();
                        String info = parser.nextText();
                        int start = info.indexOf("(");
                        if (start != -1) {
                            city.name = info.substring(0, start - 1);
                            city.id = info.substring(start + 1, info.length() - 1);
                        }
                    }
                case XmlPullParser.END_TAG://如果遇到标签结束
                    if ("string".equals(parser.getName())) {//如果是string标签结束
                        citys.add(city);//将创建完成的city加入集合
                        city = null;//并且置空
                    }
                    break;
            }
            eventType = parser.next();//进入下一个事件处理
        }
        return citys;
    }

    public static List<String> getWeathers(String xml) throws Exception {
        List<String> strs = null;
        String str = null;
        XmlPullParser parser = Xml.newPullParser();//得到Pull解析器
        parser.setInput(new ByteArrayInputStream(xml.getBytes()), "UTF-8");//设置下输入流的编码
        int eventType = parser.getEventType();//得到第一个事件类型
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT://如果是文档开始事件
                    strs = new ArrayList<>();//  创建一个person集合
                    break;
                case (XmlPullParser.START_TAG)://如果遇到标签开始
                    String tagName = parser.getName();// 获得解析器当前元素的名称
//                    Log.v("MainActivity", "parser.getName()" + tagName);
                    if ("string".equals(tagName)) {//如果当前标签名称是<string>
                        str = parser.nextText();
                    }
                case XmlPullParser.END_TAG://如果遇到标签结束
                    if ("string".equals(parser.getName())) {//如果是string标签结束
                        strs.add(str);//将创建完成的city加入集合
                        str = null;//并且置空
                    }
                    break;
            }
            eventType = parser.next();//进入下一个事件处理
        }
        return strs;
    }
}
