package com.wc.android.demo;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 链接WebService工具类
 * Created by Administrator on 2017/2/23.
 */

public class WebServiceUtils {
    private static final String NAMESPACE = "http://www.webxml.com.cn/WebServices/";
    public static final String WEATHERWEBSERVICE = NAMESPACE + "WeatherWebService.asmx";  //天气预报

    public static final String METHODNAME_GETSUPPORTCITY = "getSupportCity";       //查询本天气预报Web Services支持的国内外城市或地区信息
    public static final String METHODNAME_GGETSUPPORTDATASET = "getSupportDataSet";       //获得本天气预报Web Services支持的洲、国内外省份和城市信息
    public static final String METHODNAME_GGETSUPPORTPROVINCE = "getSupportProvince";       //获得本天气预报Web Services支持的洲、国内外省份和城市信息
    public static final String METHODNAME_GGETWEATHERBYCITYNAME = "getWeatherbyCityName";       //根据城市或地区名称查询获得未来三天内天气情况、现在的天气实况、天气和生活指数
    public static final String METHODNAME_GGETWEATHERBYCITYNAMEPRO = "getWeatherbyCityNamePro";       //根据城市或地区名称查询获得未来三天内天气情况、现在的天气实况、天气和生活指数（For商业用户）

    /**
     * WEB 通讯
     *
     * @param interfaceUrl 接口地址
     * @param methodName   方法名
     * @param map          值
     * @param callback     反馈
     */
    public static void postWebServiceMap(String interfaceUrl, String methodName, Map<String, String> map,
                                         StringCallback callback) {
        String url = interfaceUrl + "/" + methodName;
        OkGo.post(url).params(map != null ? map : null)
                .execute(callback);
    }

    public static Map<String, String> getSupportCity() {
        Map<String, String> maps = new HashMap<>();
        maps.put("byProvinceName", "ALL");
        return maps;
    }
    public static Map<String, String> getWeatherbyCityName(String CityID) {
        Map<String, String> maps = new HashMap<>();
        maps.put("theCityName", CityID);
        return maps;
    }
}
