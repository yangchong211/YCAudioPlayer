package cn.ycbjie.ycaudioplayer.ui.home.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.pedaily.yc.ycdialoglib.popupWindow.CustomPopupWindow;
import com.pedaily.yc.ycdialoglib.toast.ToastUtils;
import com.yc.cn.ycbannerlib.BannerView;
import com.yc.cn.ycbannerlib.banner.util.SizeUtil;

import org.yczbj.ycrefreshviewlib.YCRefreshView;
import org.yczbj.ycrefreshviewlib.adapter.RecyclerArrayAdapter;
import org.yczbj.ycrefreshviewlib.item.RecycleViewItemLine;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.view.BaseLazyFragment;
import cn.ycbjie.ycaudioplayer.ui.detail.view.activity.DetailVideoActivity;
import cn.ycbjie.ycaudioplayer.ui.home.ui.activity.LocalOfficeActivity;
import cn.ycbjie.ycaudioplayer.ui.home.ui.activity.LocalVideoActivity;
import cn.ycbjie.ycaudioplayer.ui.home.ui.activity.LocalZipFileActivity;
import cn.ycbjie.ycaudioplayer.ui.home.ui.adapter.BannerPagerAdapter;
import cn.ycbjie.ycaudioplayer.ui.home.ui.adapter.InnovationAdapter;
import cn.ycbjie.ycaudioplayer.ui.main.ui.activity.MainActivity;
import cn.ycbjie.ycaudioplayer.utils.app.ImageUtil;
import cn.ycbjie.ycaudioplayer.utils.logger.AppLogUtils;


/**
 * <pre>
 *     @author yangchong
 *     blog  :
 *     time  : 2018/3/1
 *     desc  : 视频页面
 *     revise:
 * </pre>
 */
public class InnovationFragment extends BaseLazyFragment {

    @BindView(R.id.recyclerView)
    YCRefreshView recyclerView;
    private MainActivity activity;
    private InnovationAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }


    @Override
    public int getContentView() {
        return R.layout.base_easy_recycle;
    }

    @Override
    public void initView(View view) {
        initYCRefreshView();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onLazyLoad() {
        List<String> data = new ArrayList<>();
        for(int a=0 ; a<10 ; a++){
            data.add("假数据"+a);
        }
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }


    private void initYCRefreshView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        final RecycleViewItemLine line = new RecycleViewItemLine(activity, LinearLayout.HORIZONTAL,
                SizeUtils.dp2px(1), Color.parseColor("#f5f5f7"));
        recyclerView.addItemDecoration(line);
        adapter = new InnovationAdapter(activity);
        recyclerView.setAdapter(adapter);
        addHeader();
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefreshLayout swipeToRefresh = recyclerView.getSwipeToRefresh();
                if(swipeToRefresh.isRefreshing()){
                    recyclerView.setRefreshing(false);
                }
            }
        });

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.getRecyclerView().setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);
    }


    /**
     * 添加头部
     */
    private void addHeader() {
        adapter.removeAllHeader();
        initTopHeaderView();
        initButtonView();
        initContentView();
    }

    private void initTopHeaderView() {
        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
        arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");
        arrayList.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
        arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(activity).inflate(R.layout.head_audio_banner, parent, false);
            }


            @Override
            public void onBindView(View headerView) {
                // 绑定数据
                BannerView mBanner = (BannerView) headerView.findViewById(R.id.banner);
                mBanner.setHintGravity(5);
                mBanner.setAnimationDuration(1000);
                mBanner.setPlayDelay(2000);
                mBanner.setHintPadding(0,0, SizeUtil.dip2px(activity,10), SizeUtil.dip2px(activity,10));
                mBanner.setAdapter(new BannerPagerAdapter(activity, arrayList));
            }
        });
    }

    private void initButtonView() {
        final LinearLayout linearLayout = initFiveButtonView();
        if (linearLayout == null) {
            return;
        }
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return linearLayout;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
    }



    private void initContentView() {
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                return LayoutInflater.from(activity).inflate(R.layout.head_audio_announce, parent, false);
            }

            @Override
            public void onBindView(View headerView) {
                final TextView tv_content = headerView.findViewById(R.id.tv_content);
                tv_content.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        tv_content.setBackgroundColor(activity.getResources().getColor(R.color.alpha_40_black));
                        View contentView = LayoutInflater.from(activity)
                                .inflate(R.layout.pop_layout_copy,null);
                        TextView tv_copy = contentView.findViewById(R.id.tv_copy);
                        final CustomPopupWindow popWindow = new CustomPopupWindow.PopupWindowBuilder(activity)
                                //.setView(R.layout.pop_layout)
                                .setView(contentView)
                                .setFocusable(true)
                                //弹出popWindow时，背景是否变暗
                                .enableBackgroundDark(false)
                                //控制亮度
                                .setBgDarkAlpha(0.0f)
                                .setOutsideTouchable(true)
                                //.setAnimationStyle(R.style.popWindowStyle)
                                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                                    @Override
                                    public void onDismiss() {
                                        //对话框销毁时
                                        tv_content.setBackgroundColor(activity.getResources().getColor(R.color.white));
                                    }
                                })
                                .create()
                                .showAsDropDown(tv_content, 0, 10);

                        tv_copy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popWindow.dismiss();
                                ToastUtils.showRoundRectToast("复制内容");
                            }
                        });
                        return false;
                    }
                });

            }
        });
    }

    @SuppressLint("ResourceType")
    private LinearLayout initFiveButtonView() {
        ArrayList<String> title = new ArrayList<>();
        title.add("视频");
        title.add("图片");
        title.add("压缩包");
        title.add("文档");
        title.add("其他");
        //四个按钮
        LinearLayout btnLinearLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams params = new
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnLinearLayout.setLayoutParams(params);
        btnLinearLayout.setPadding(0, SizeUtils.dp2px(15), 0, SizeUtils.dp2px(15));
        btnLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        //重点：根据服务器返回数据动态创建按钮。如果返回5条数据，就创建5个按钮
        for (int i = 0; i < 5; i++) {
            LinearLayout llBtn = new LinearLayout(activity);
            LinearLayout.LayoutParams llLayoutParams = new
                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            llLayoutParams.width = 0;
            llLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            llLayoutParams.weight = 1;
            llLayoutParams.gravity = Gravity.CENTER;
            llBtn.setLayoutParams(llLayoutParams);
            llBtn.setOrientation(LinearLayout.VERTICAL);
            llBtn.setId(1000 + i);
            llBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppLogUtils.e("onClick"+v.getId());
                    switch (v.getId()) {
                        case 1000:
                            ActivityUtils.startActivity(LocalVideoActivity.class);
                            break;
                        case 1001:
                            ActivityUtils.startActivity(LocalOfficeActivity.class);
                            break;
                        case 1002:
                            ActivityUtils.startActivity(LocalZipFileActivity.class);
                            break;
                        case 1003:
                            ActivityUtils.startActivity(DetailVideoActivity.class);
                            break;
                        case 1004:
                            ActivityUtils.startActivity(DetailVideoActivity.class);
                            break;
                        default:
                            break;
                    }
                }
            });

            ImageView imageView = new ImageView(activity);
            LinearLayout.LayoutParams ivLayoutParams = new LinearLayout.LayoutParams(SizeUtils.dp2px(45), SizeUtils.dp2px(45));
            ivLayoutParams.gravity = Gravity.CENTER;
            imageView.setLayoutParams(ivLayoutParams);
            ImageUtil.loadImgByPicasso(activity, R.drawable.ic_home_first, imageView);

            TextView textView = new TextView(activity);
            LinearLayout.LayoutParams tvLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tvLayoutParams.topMargin = SizeUtils.dp2px(8);
            tvLayoutParams.gravity = Gravity.CENTER;
            textView.setLayoutParams(tvLayoutParams);
            textView.setTextSize(14);
            textView.setText(title.get(i));
            llBtn.addView(imageView);
            llBtn.addView(textView);
            btnLinearLayout.addView(llBtn);
        }
        return btnLinearLayout;
    }


}
