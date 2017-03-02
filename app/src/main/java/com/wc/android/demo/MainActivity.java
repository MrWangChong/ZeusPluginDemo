package com.wc.android.demo;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lzy.okgo.callback.StringCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import zeus.plugin.PluginConfig;
import zeus.plugin.PluginManager;
import zeus.plugin.PluginUtil;
import zeus.plugin.ZeusBaseActivity;
import zeus.plugin.ZeusPlugin;

public class MainActivity extends ZeusBaseActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City city = (City) (parent.getAdapter().getItem(position));
                PluginManager.loadLastVersionPlugin(PluginConfig.PLUGIN_TEST);
                try {
                    Class cl = PluginManager.mNowClassLoader.loadClass(PluginManager.getPlugin(PluginConfig.PLUGIN_TEST).getPluginMeta().mainClass);
                    Intent intent = new Intent(MainActivity.this, cl).putExtra("CityName", city.name).putExtra("CityID", city.id);

                    //这种方式为通过在宿主AndroidManifest.xml中预埋activity实现
//            startActivity(intent);
                    //这种方式为通过欺骗android系统的activity存在性校验的方式实现
                    PluginManager.startActivity(MainActivity.this, intent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
//                startActivity(new Intent(MainActivity.this, WeatherbyCityNameActivity.class).putExtra("CityName", city.name).putExtra("CityID", city.id));
            }
        });
        WebServiceUtils.postWebServiceMap(WebServiceUtils.WEATHERWEBSERVICE, WebServiceUtils.METHODNAME_GETSUPPORTCITY, WebServiceUtils.getSupportCity(), new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    List<City> infos = PulCityService.getCitys(s);
                    listView.setAdapter(new CityAdapter(MainActivity.this, infos));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);

            }
        });
        installPlugin();
        installHotfix();
    }

    /**
     * 安装assets中高版本插件plugin_test_version2.apk
     * 先拷贝到PluginUtil.getZipPath(PluginConfig.PLUGIN_TEST)
     * 然后调用install()安装。
     */
    public void installPlugin() {
//        if (PluginManager.isInstall(PluginConfig.PLUGIN_TEST)) {
//            Toast.makeText(this, "插件"+PluginConfig.PLUGIN_TEST+"已经被安装,不用再次安装", Toast.LENGTH_SHORT).show();
//            return;
//        }
        ZeusPlugin zeusPlugin = PluginManager.getPlugin(PluginConfig.PLUGIN_TEST);
        FileOutputStream out = null;
        InputStream in = null;
        try {
            String filePath = Environment.getExternalStorageDirectory() + "/zeusplugin_installinfo/zeusplugin_test.apk";
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
//                String newFilePath = Environment.getExternalStorageDirectory() + "/zeusplugin_installinfo/zeusplugin_test.apk";
//                if (PluginUtil.rename(filePath, newFilePath)) {
//                    Log.v("MainActivity", "重命名成功");
                in = new FileInputStream(file);
//                } else {
//                    Log.v("MainActivity", "重命名失败");
//                }
            } else {
                Log.v("MainActivity", "无插件文件");
                return;
            }
            PluginUtil.createDirWithFile(PluginUtil.getZipPath(PluginConfig.PLUGIN_TEST));
            out = new FileOutputStream(PluginUtil.getZipPath(PluginConfig.PLUGIN_TEST), false);
            byte[] temp = new byte[2048];
            int len;
            while ((len = in.read(temp)) > 0) {
                out.write(temp, 0, len);
            }
            boolean delete = file.delete();
            if (delete) {
                Log.v("MainActivity", "插件文件删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PluginUtil.close(in);
            PluginUtil.close(out);
        }

        boolean installed = zeusPlugin.install();
        if (installed) {
            Log.v("MainActivity", "插件安装成功");
        }
    }

    /**
     * 应用补丁
     */
    public void installHotfix() {
//        if (PluginManager.isInstall(PluginConfig.HOTFIX_TEST)) {
//            Toast.makeText(this, "补丁" + PluginConfig.HOTFIX_TEST + "已经被安装,不用再次安装", Toast.LENGTH_SHORT).show();
//            return;
//        }

        ZeusPlugin zeusPlugin = PluginManager.getPlugin(PluginConfig.HOTFIX_TEST);
        FileOutputStream out = null;
        InputStream in = null;
        try {
            String filePath = Environment.getExternalStorageDirectory() + "/zeusplugin_installinfo/zeushotfix_test.apk";
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                in = new FileInputStream(file);
            } else {
                Log.v("MainActivity", "无热修复文件");
                return;
            }
//            AssetManager am = PluginManager.mBaseResources.getAssets();
//            in = am.open("zeushotfix_test.apk");
            PluginUtil.createDirWithFile(PluginUtil.getZipPath(PluginConfig.HOTFIX_TEST));
            out = new FileOutputStream(PluginUtil.getZipPath(PluginConfig.HOTFIX_TEST), false);
            byte[] temp = new byte[2048];
            int len;
            while ((len = in.read(temp)) > 0) {
                out.write(temp, 0, len);
            }
            boolean delete = file.delete();
            if (delete) {
                Log.v("MainActivity", "热修复文件删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PluginUtil.close(in);
            PluginUtil.close(out);
        }
        boolean result = zeusPlugin.install();
        if (result) {
            Log.v("MainActivity", "热修修复文件安装成功");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
