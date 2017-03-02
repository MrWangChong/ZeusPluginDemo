package zeus.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

/**
 * 基础的activity
 * Created by huangjian on 2016/6/21.
 */
public class ZeusBaseActivity extends Activity {

    //---------------------插件相关的代码-----------------------start
    ZeusHelper helper = new ZeusHelper();

    @Override
    public Object getSystemService(String name) {
        return helper.getSystemService(this, super.getSystemService(name), name);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        ZeusHelper.attachBaseContext(newBase,this);
    }

    @Override
    public Resources getResources() {
        return PluginManager.getResources();
    }

    /**
     * 解决有时插件通过inflate找不到资源的问题
     * @return Resources.Theme
     */
    public Resources.Theme getTheme() {
        return helper.getTheme(super.getTheme());
    }
    //---------------------------插件相关代码-------------------------end
}
