package cn.ycbjie.ycaudioplayer.ui.main.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.blankj.utilcode.util.ActivityUtils;

import java.util.List;

import cn.ycbjie.ycaudioplayer.ui.guide.ui.GuideActivity;
import cn.ycbjie.ycaudioplayer.ui.me.view.activity.MeSettingActivity;
import cn.ycbjie.ycaudioplayer.utils.app.AppToolUtils;


/**
 * Android业务组件化之URL Scheme使用
 */

public class SchemeFirstActivity extends AppCompatActivity {

    /*URL Scheme协议格式：
    String urlStr="http://www.orangecpp.com:80/tucao?id=hello&name=lily";
    //url =            protocol + authority(host + port) + path + query
    //协议protocol=    http
    //域名authority=   www.orangecpp.com:80
    //页面path=          /tucao
    //参数query=       id=hello&name=lily

    //authority =      host + port
    //主机host=        www.orangecpp.com
    //端口port=        80*/


    /**
     * URL Scheme使用场景，目前1，2，5使用场景很广
     * 1.通过小程序，利用Scheme协议打开原生app
     * 2.H5页面点击锚点，根据锚点具体跳转路径APP端跳转具体的页面
     * 3.APP端收到服务器端下发的PUSH通知栏消息，根据消息的点击跳转路径跳转相关页面
     * 4.APP根据URL跳转到另外一个APP指定页面
     * 5.通过短信息中的url打开原生app
     */


    /**
     * 协议部分，随便设置 yc://ycbjie.cn:8888/from?type=yangchong
     * 如果携带参数，则：yc://ycbjie.cn:8888/from?type=yangchong&level=20
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri uri = getIntent().getData();
        if (uri != null) {
            //解析一个url
            // 完整的url信息
            String urlStr = uri.toString();
            Log.e( "UrlUtils","url: " + urlStr);
            // scheme部分
            String scheme = uri.getScheme();
            Log.e( "UrlUtils","scheme: " + scheme);
            // host部分
            String host = uri.getHost();
            Log.e( "UrlUtils","host: " + host);
            //port部分
            int port = uri.getPort();
            Log.e( "UrlUtils","port: " + port);
            // 访问路劲
            String path = uri.getPath();
            Log.e( "UrlUtils","path: " + path);
            List<String> pathSegments = uri.getPathSegments();
            Log.e( "UrlUtils","pathSegments: " + pathSegments.toString());
            // Query部分
            String query = uri.getQuery();
            Log.e( "UrlUtils","query: " + query);
            //获取此URI的解码权限部分。对于服务器地址，权限的结构如下：Examples: "google.com", "bob@google.com:80"
            String authority = uri.getAuthority();
            Log.e( "UrlUtils","authority: " + authority);
            //从权限获取已解码的用户信息。例如，如果权限为“任何人@google.com”，此方法将返回“任何人”。
            String userInfo = uri.getUserInfo();
            Log.e( "UrlUtils","userInfo: " + userInfo);
            //获取指定参数值
            String type = uri.getQueryParameter("type");
            Log.e( "UrlUtils","type: " + type);

            //获取指定参数值，该方法获取值一直是空
            //String level = uri.getQueryParameter("level");
            ///Log.e( "UrlUtils","level: " + level);

            String level = getValueByName(urlStr, "level");
            Log.e( "UrlUtils","level: " + level);


            switch (type){
                //yc://ycbjie.cn:8888/from?type=yangchong
                case "yangchong":
                    ActivityUtils.startActivity(GuideActivity.class);
                    break;
                //yc://ycbjie.cn:8888/from?type=main
                case "main":
                    readGoActivity(new Intent(this,MainActivity.class),this);
                    break;
                //yc://ycbjie.cn:8888/from?type=setting
                case "setting":
                    readGoActivity(new Intent(this, MeSettingActivity.class),this);
                    break;
            }
        }
        finish();
    }


    /***
     * 获取url 指定name的value;
     * @param url                       url
     * @param name                      参数名
     * @return                          获取某个参数值
     */
    private String getValueByName(String url, String name) {
        String result = "";
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        if(temp.contains("&")){
            String[] keyValue = temp.split("&");
            for (String str : keyValue) {
                if (str.contains(name)) {
                    result = str.replace(name + "=", "");
                    break;
                }
            }
        }
        return result;
    }


    private void readGoActivity(Intent intent, Context context) {
        // 如果app 运行中，直接打开页面，没有运行中就先打开主界面，在打开
        if (AppToolUtils.isAppRunning(context, context.getPackageName())) {
            openActivity(intent, context);
        } else {
            reStartActivity(intent, context);
        }
    }

    private void openActivity(Intent intent, Context context) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void reStartActivity(Intent intent, Context context) {
        Intent[] intents = new Intent[2];
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intents[0] = mainIntent;
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intents[1] = intent;
        context.startActivities(intents);
    }


}
