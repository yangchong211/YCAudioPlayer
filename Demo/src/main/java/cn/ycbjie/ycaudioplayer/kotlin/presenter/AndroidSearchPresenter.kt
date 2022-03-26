package cn.ycbjie.ycaudioplayer.kotlin.presenter

import cn.ycbjie.ycaudioplayer.kotlin.contract.AndroidSearchContract
import cn.ycbjie.ycaudioplayer.kotlin.model.helper.AndroidHelper
import cn.ycbjie.ycaudioplayer.kotlin.network.ResponseTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import network.schedules.BaseSchedulerProvider
import network.schedules.SchedulerProvider

class AndroidSearchPresenter : AndroidSearchContract.Presenter {

    private var mView: AndroidSearchContract.View
    private var scheduler: BaseSchedulerProvider? = null
    private var page: Int = 0

    constructor (androidView: AndroidSearchContract.View, scheduler: SchedulerProvider){
        this.mView = androidView
        this.scheduler = scheduler
    }

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {
        compositeDisposable.clear()
    }


    override fun getSearchTag() {
        val instance = AndroidHelper.instance()
        var disposable: Disposable = instance.getRecommendSearchTag()
                .compose(ResponseTransformer.handleResult())
                .compose(scheduler?.applySchedulers())
                .subscribe(
                        { t -> mView.setSearchTagSuccess(t) },
                        { t -> mView.setSearchTagFail(t.message!!) }
                )
        compositeDisposable.add(disposable)
    }


    override fun search(str: String, isRefresh: Boolean) {
        if (isRefresh) {
            page = 0
        }
        val instance = AndroidHelper.instance()
        var disposable = instance.search(page, str)
                .compose(ResponseTransformer.handleResult())
                .compose(scheduler?.applySchedulers())
                .subscribe(
                        { t ->
                            if (t.size * (page + 1) >= t.total) {
                                mView.setAllData(t, isRefresh)
                            } else {
                                mView.setSearchResultSuccess(t, isRefresh)
                                page++
                            }

                        },
                        { t: Throwable -> mView.setSearchResultFail(t.message!!) }
                )
        compositeDisposable.add(disposable)
    }

}
