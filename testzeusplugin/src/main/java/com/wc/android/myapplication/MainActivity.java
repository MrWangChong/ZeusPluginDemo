package com.wc.android.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.callback.StringCallback;
import com.wc.android.demo.PulCityService;
import com.wc.android.demo.WebServiceUtils;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import zeus.plugin.ZeusBaseActivity;

public class MainActivity extends ZeusBaseActivity {
    private LinearLayout ll_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_cityname = (TextView) findViewById(R.id.tv_cityname);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        final String CityName = getIntent().getStringExtra("CityName");
        String CityID = getIntent().getStringExtra("CityID");
        tv_cityname.setText(String.format("%s天气", CityName));

        tv_cityname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AMapActivity.class).putExtra("CityName", CityName));
            }
        });
        WebServiceUtils.postWebServiceMap(WebServiceUtils.WEATHERWEBSERVICE, WebServiceUtils.METHODNAME_GGETWEATHERBYCITYNAME, WebServiceUtils.getWeatherbyCityName(CityID), new StringCallback() {

            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    List<String> infos = PulCityService.getWeathers(s);
                    for (String str : infos) {
                        TextView tv = new TextView(MainActivity.this);
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
