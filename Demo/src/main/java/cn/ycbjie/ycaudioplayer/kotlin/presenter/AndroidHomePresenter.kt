package cn.ycbjie.ycaudioplayer.kotlin.presenter


import cn.ycbjie.ycaudioplayer.db.dl.TaskViewHolderImp.id
import cn.ycbjie.ycaudioplayer.kotlin.contract.AndroidHomeContract
import cn.ycbjie.ycaudioplayer.kotlin.model.model.HomeModel
import cn.ycbjie.ycaudioplayer.kotlin.network.ResponseTransformer
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import network.schedules.BaseSchedulerProvider
import network.schedules.SchedulerProvider

class AndroidHomePresenter : AndroidHomeContract.Presenter {

    private var mView: AndroidHomeContract.View
    private var scheduler: BaseSchedulerProvider? = null

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val model: HomeModel by lazy {
        HomeModel()
    }

    constructor(androidView: AndroidHomeContract.View){
        this.mView = androidView
        scheduler = SchedulerProvider.getInstatnce()
    }


    override fun subscribe() {

    }

    override fun unSubscribe() {
        compositeDisposable.dispose()
    }


    override fun getHomeList(page: Int) {
        val disposable: Disposable = model.getHomeList(page)
                //网络请求在子线程，所以是在io线程，避免阻塞线程
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ bean ->
                    LogUtils.e("getHomeList-----"+"onNext")
                    mView.setDataView(bean)
                }, { t ->
                    LogUtils.e("getHomeList-----"+"onError"+t.localizedMessage)
                    if(NetworkUtils.isConnected()){
                        mView.setDataErrorView()
                    }else{
                        mView.setNetWorkErrorView()
                    }
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun getBannerData(isRefresh: Boolean) {
        val disposable: Disposable = model.getBannerData()
                //网络请求在子线程，所以是在io线程，避免阻塞线程
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ bean ->
                        LogUtils.e("getBanner-----"+"onNext")
                        mView.setBannerView(bean)
                    }, { t ->
                        LogUtils.e("getBanner-----"+"onError"+t.localizedMessage)
                    }
                )
        compositeDisposable.add(disposable)
    }

    override fun unCollectArticle(selectId: Int) {
        var disposable = model.unCollectArticle(selectId)
                .compose(ResponseTransformer.handleResult())
                .compose(scheduler?.applySchedulers())
                .subscribe(
                        {
                            mView?.unCollectArticleSuccess()
                        }
                        ,
                        {t: Throwable ->
                            mView?.unCollectArticleFail(t)
                        }
                )
        compositeDisposable.add(disposable)
    }

    override fun collectInArticle(selectId: Int) {
        var disposable = model.collectInArticle(id)
                .compose(ResponseTransformer.handleResult())
                .compose(scheduler?.applySchedulers())
                .subscribe(
                        {
                            mView?.collectInArticleSuccess()
                        }, { t: Throwable ->
                    mView?.collectInArticleFail(t)
                }
                )
        compositeDisposable.add(disposable)
    }


}
