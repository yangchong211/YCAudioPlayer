package cn.ycbjie.ycaudioplayer.ui.home.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yc.cn.ycbannerlib.banner.adapter.AbsStaticPagerAdapter;

import java.util.List;

import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.utils.app.ImageUtil;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2016/11/12
 * 描    述：会议轮播图适配器
 * 修订历史：
 * ================================================
 */
public class BannerPagerAdapter extends AbsStaticPagerAdapter {

    private Context ctx;
    private List<String> list;

    public BannerPagerAdapter(Context ctx, List<String> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public View getView(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(ctx);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //加载图片
        if(list!=null){
            ImageUtil.loadImgByPicasso(ctx,list.get(position), R.drawable.bg_small_autumn_tree_min,imageView);
        }else {
            ImageUtil.loadImgByPicasso(ctx, R.drawable.bg_small_autumn_tree_min,imageView);
        }
        return imageView;
    }

    @Override
    public int getCount() {
        return list==null ? 0 : list.size();
    }

}