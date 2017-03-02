package com.wc.android.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 天气
 * Created by Administrator on 2017/2/23.
 */

public class WeatherbyCityNameActivity extends AppCompatActivity {
    private TextView tv_cityname;
    private LinearLayout ll_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        tv_cityname = (TextView) findViewById(R.id.tv_cityname);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        String CityName = getIntent().getStringExtra("CityName");
        String CityID = getIntent().getStringExtra("CityID");
        tv_cityname.setText(String.format("%s天气", CityName));

        WebServiceUtils.postWebServiceMap(WebServiceUtils.WEATHERWEBSERVICE, WebServiceUtils.METHODNAME_GGETWEATHERBYCITYNAME, WebServiceUtils.getWeatherbyCityName(CityID), new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    List<String> infos = PulCityService.getWeathers(s);
                    for (String str : infos) {
                        TextView tv = new TextView(WeatherbyCityNameActivity.this);
                        tv.setPadding(0, 4, 0, 4);
                        tv.setText(str);
                        ll_content.addView(tv);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);

            }
        });
    }
}
