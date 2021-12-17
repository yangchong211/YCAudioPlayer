package cn.ycbjie.ycaudioplayer.base.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ns.yc.ycutilslib.activityManager.AppManager;

import butterknife.ButterKnife;
import cn.ycbjie.ycaudioplayer.base.BaseAppHelper;
import cn.ycbjie.ycaudioplayer.base.density.BaseAutoFragment;
import cn.ycbjie.ycaudioplayer.base.mvp.BasePresenter;
import cn.ycbjie.ycaudioplayer.service.PlayService;
import cn.ycbjie.ycaudioplayer.ui.guide.ui.GuideActivity;


/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/5/18
 * 描    述：所有Fragment的父类
 * 修订历史：
 * ================================================
 */
public abstract class BaseFragment<T extends BasePresenter> extends BaseAutoFragment {

    /**
     * 将代理类通用行为抽出来
     */
    protected T mPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container , false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null){
            mPresenter.subscribe();
        }
        initView(view);
        initListener();
        initData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.unSubscribe();
        }
        initLeakCanary();             //测试内存泄漏，正式一定要隐藏
    }

    /** 返回一个用于显示界面的布局id */
    public abstract int getContentView();

    /** 初始化View的代码写在这个方法中
     * @param view*/
    public abstract void initView(View view);

    /** 初始化监听器的代码写在这个方法中 */
    public abstract void initListener();

    /** 初始数据的代码写在这个方法中，用于从服务器获取数据 */
    public abstract void initData();

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 获取到播放音乐的服务
     * @return              PlayService对象
     */
    protected PlayService getPlayService() {
        PlayService playService = BaseAppHelper.get().getPlayService();
        if (playService == null) {
            //throw new NullPointerException("play service is null");
            startActivity(new Intent(getActivity(), GuideActivity.class));
            AppManager.getAppManager().finishAllActivity();
        }
        return playService;
    }


    /**用来检测所有Fragment的内存泄漏*/
    private void initLeakCanary() {
        /*RefWatcher refWatcher = BaseApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);*/
    }


}
