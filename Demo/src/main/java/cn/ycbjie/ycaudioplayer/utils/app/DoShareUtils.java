package cn.ycbjie.ycaudioplayer.utils.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;

import cn.ycbjie.ycaudioplayer.R;


/**
 * <pre>
 *     @author 杨充
 *     blog  :
 *     time  : 2016/01/30
 *     desc  : 分享
 *     revise:
 * </pre>
 */
public class DoShareUtils {

    /**
     * 分享链接
     * @param context
     * @param text
     * @param title
     */
    public static void shareText(Context context, String text, String title){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        context.startActivity(Intent.createChooser(intent,title));
    }


    /**
     * 分享单张图片
     */
    public static void shareImage(Context context, Uri uri, String title){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(intent,title));
    }


    /**
     * 分享单张图片
     */
    public static void shareImage(Context context, String imagePath) {
        //String imagePath = Environment.getExternalStorageDirectory() + File.separator + "test.jpg";
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(imagePath));
        //输出：file:///storage/emulated/0/test.jpg
        Log.d("share", "uri:" + imageUri);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }



    /**
     * 分享单张图片
     */
    public static void shareImage(Context context, Bitmap bitmap) {
        String s = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                bitmap, null, null);
        Uri uri = Uri.parse(s);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        //设置分享行为
        intent.setType("image/*");
        //设置分享内容的类型
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent = Intent.createChooser(intent, "分享到");
        context.startActivity(intent);
    }


    /**
     * 分享功能
     * @param context       上下文
     * @param msgTitle      消息标题
     * @param msgText       消息内容
     * @param imgPath       图片路径，不分享图片则传null
     */
    public static void shareTextAndImage(Context context, String msgTitle, String msgText, String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            // 纯文本
            intent.setType("text/plain");
        } else {
            File f = new File(imgPath);
            if (f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "分享到"));
    }

    /**
     * 分享app
     * @param context           上下文
     */
    public static void shareApp(Context context) {
        shareText(context, context.getString(R.string.share_app_content),
                context.getString(R.string.app_name));
    }

}
