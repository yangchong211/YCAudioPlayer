package cn.ycbjie.ycaudioplayer.ui.news.ui.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import cn.ycbjie.ycaudioplayer.R;
import cn.ycbjie.ycaudioplayer.base.view.BaseDelegateAdapter;
import cn.ycbjie.ycaudioplayer.base.view.BaseLazyFragment;
import cn.ycbjie.ycaudioplayer.constant.Constant;
import cn.ycbjie.ycaudioplayer.ui.main.ui.activity.MainActivity;


public class PractiseFragment extends BaseLazyFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_view)
    SwipeRefreshLayout refreshView;

    /**
     * 存放各个模块的适配器
     */
    private List<DelegateAdapter.Adapter> mAdapters;
    private DelegateAdapter delegateAdapter;

    private MainActivity activity;
    private VirtualLayoutManager layoutManager;
    private int lastPosition;
    private BaseDelegateAdapter bannerAdapter;
    private BaseDelegateAdapter buttonAdapter;
    private BaseDelegateAdapter hotAdapter;
    private BaseDelegateAdapter adAdapter;
    private BaseDelegateAdapter popularizeAdapter;
    private BaseDelegateAdapter titleAdapter;

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
        return R.layout.base_bar_recycler_view;
    }

    @Override
    public void initView(View view) {
        initVLayout();
        initRefreshView();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onLazyLoad() {
        getData();
        //设置适配器
        delegateAdapter.setAdapters(mAdapters);
        recyclerView.requestLayout();
    }


    private void initVLayout() {
        mAdapters = new LinkedList<>();
        //初始化
        //创建VirtualLayoutManager对象
        layoutManager = new VirtualLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        //设置适配器
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setAdapter(delegateAdapter);

        //自定义各种不同适配器
        initAllTypeView();
        //设置适配器
        //delegateAdapter.setAdapters(mAdapters);
    }

    private void initRefreshView() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (lastPosition == delegateAdapter.getItemCount() - 1) {
                        //上拉加载更多
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastPosition = layoutManager.findLastVisibleItemPosition();

            }
        });

        // 下拉刷新
        refreshView.setColorSchemeColors(0xff412f63, 0xff412f63, 0xff412f63);
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
            }
        });
    }


    private void initAllTypeView() {
        initBannerView();
        initFiveButtonView();
        initListHotView();
        initFirstAdView();
        initListSecondView();
        initSecondAdView();
        initListThirdView();
        initListFourView();
        initListFiveView();
        initListSixView();
    }


    private void initBannerView() {
        //banner
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        bannerAdapter = new BaseDelegateAdapter(activity,
                linearLayoutHelper, R.layout.view_vlayout_banner, 1, Constant.ViewType.TYPE_BANNER);
        mAdapters.add(bannerAdapter);
    }

    private void initFiveButtonView() {
        //5个按钮
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        buttonAdapter = new BaseDelegateAdapter(activity,
                linearLayoutHelper, R.layout.view_vlayout_button, 1, Constant.ViewType.TYPE_VIEW);
        mAdapters.add(buttonAdapter);

    }

    private void initListHotView() {
        initTitleView(1);
        final GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setAutoExpand(true);
        gridLayoutHelper.setPadding(0, 16, 0, 16);
        // 控制子元素之间的垂直间距
        gridLayoutHelper.setVGap(16);
        // 控制子元素之间的水平间距
        gridLayoutHelper.setHGap(0);
        gridLayoutHelper.setBgColor(Color.WHITE);
        gridLayoutHelper.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (delegateAdapter.getItemViewType(position) == Constant.ViewType.TYPE_TITLE
                        || delegateAdapter.getItemViewType(position) == Constant.ViewType.TYPE_MORE) {
                    return gridLayoutHelper.getSpanCount();
                }else{
                    return 1;
                }
            }
        });
        hotAdapter = new BaseDelegateAdapter(activity, gridLayoutHelper,
                R.layout.view_vlayout_grid, 4, Constant.ViewType.TYPE_GV);
        mAdapters.add(hotAdapter);
        initMoreView(1);
    }


    private void initFirstAdView() {
        adAdapter = new BaseDelegateAdapter(activity, new LinearLayoutHelper(),
                R.layout.view_vlayout_ad, 1, Constant.ViewType.TYPE_AD);
        mAdapters.add(adAdapter);
    }


    private void initListSecondView() {
        initTitleView(2);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setAspectRatio(4.0f);
        linearLayoutHelper.setDividerHeight(5);
        linearLayoutHelper.setMargin(0, 0, 0, 0);
        linearLayoutHelper.setPadding(0, 0, 0, 10);
        popularizeAdapter = new BaseDelegateAdapter(activity,
                linearLayoutHelper, R.layout.view_vlayout_news, 3, Constant.ViewType.TYPE_LIST2);
        mAdapters.add(popularizeAdapter);
        initMoreView(2);
    }



    private void initSecondAdView() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        BaseDelegateAdapter adAdapter = new BaseDelegateAdapter(activity,
                linearLayoutHelper, R.layout.view_vlayout_ad, 1, Constant.ViewType.TYPE_AD2) ;
        mAdapters.add(adAdapter);
    }


    private void initListThirdView() {
        initTitleView(3);
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setPadding(0, 16, 0, 16);
        // 控制子元素之间的垂直间距
        gridLayoutHelper.setVGap(16);
        // 控制子元素之间的水平间距
        gridLayoutHelper.setHGap(0);
        gridLayoutHelper.setBgColor(Color.WHITE);
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(activity,
                gridLayoutHelper, R.layout.view_vlayout_grid, 2, Constant.ViewType.TYPE_GV2) ;
        mAdapters.add(adapter);
        initMoreView(3);
    }


    private void initListFourView() {
        initTitleView(4);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setAspectRatio(4.0f);
        linearLayoutHelper.setDividerHeight(5);
        linearLayoutHelper.setMargin(0, 0, 0, 0);
        linearLayoutHelper.setPadding(0, 0, 0, 10);
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(activity,
                linearLayoutHelper, R.layout.view_vlayout_news, 3, Constant.ViewType.TYPE_LIST3) ;
        mAdapters.add(adapter);
        initMoreView(4);
    }


    private void initListFiveView() {
        initTitleView(5);
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
        gridLayoutHelper.setPadding(0, 16, 0, 16);
        // 控制子元素之间的垂直间距
        gridLayoutHelper.setVGap(16);
        // 控制子元素之间的水平间距
        gridLayoutHelper.setHGap(0);
        gridLayoutHelper.setBgColor(Color.WHITE);
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(activity,
                gridLayoutHelper, R.layout.view_vlayout_grid, 6, Constant.ViewType.TYPE_GV_BOTTOM) ;
        mAdapters.add(adapter);
        initMoreView(5);
    }


    private void initListSixView() {
        initTitleView(6);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setAspectRatio(4.0f);
        linearLayoutHelper.setDividerHeight(5);
        linearLayoutHelper.setMargin(0, 0, 0, 0);
        linearLayoutHelper.setPadding(0, 0, 0, 10);
        BaseDelegateAdapter adapter = new BaseDelegateAdapter(activity,
                linearLayoutHelper, R.layout.view_vlayout_news, 3, Constant.ViewType.TYPE_LIST4);
        mAdapters.add(adapter);
        initMoreView(6);
    }


    private void initTitleView(final int type) {
        titleAdapter = new BaseDelegateAdapter(activity,
                new LinearLayoutHelper(), R.layout.view_vlayout_title, 1, Constant.ViewType.TYPE_TITLE);
        mAdapters.add(titleAdapter);
    }


    private void initMoreView(final int type) {
        BaseDelegateAdapter moreAdapter = new BaseDelegateAdapter(activity,
                new LinearLayoutHelper(), R.layout.view_vlayout_more, 1, Constant.ViewType.TYPE_MORE);
        mAdapters.add(moreAdapter);
    }


    private void getData() {
        final ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
        arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");
        arrayList.add("http://bpic.wotucdn.com/11/66/23/55bOOOPIC3c_1024.jpg!/fw/780/quality/90/unsharp/true/compress/true/watermark/url/L2xvZ28ud2F0ZXIudjIucG5n/repeat/true");
        arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505470629546&di=194a9a92bfcb7754c5e4d19ff1515355&imgtype=0&src=http%3A%2F%2Fpics.jiancai.com%2Fimgextra%2Fimg01%2F656928666%2Fi1%2FT2_IffXdxaXXXXXXXX_%2521%2521656928666.jpg");
        bannerAdapter.setBannerData(arrayList);

        TypedArray mButtonView = this.getResources().obtainTypedArray(R.array.button_view);
        String[] mButtonTitle = this.getResources().getStringArray(R.array.button_view_title);
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(mButtonTitle));
        mButtonView.recycle();
        buttonAdapter.setButtonData(list);

        List<String> hotList = new ArrayList<>();
        hotList.add("1");
        hotList.add("1");
        hotList.add("1");
        hotList.add("1");
        hotAdapter.setData(hotList);
    }



}
