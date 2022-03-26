package network.schedules

import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 线程切换实现类
 */
class SchedulerProvider : BaseSchedulerProvider {

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    companion object {
        private var mInstance: SchedulerProvider? = null
        fun getInstatnce(): SchedulerProvider? {
            if (mInstance == null) {
                synchronized(SchedulerProvider::class) {
                    if (mInstance == null) {
                        mInstance = SchedulerProvider()
                    }
                }
            }
            return mInstance
        }

    }

    /**
     * 切换线程操作
     */
    override fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { obserable ->
            obserable.observeOn(ui())
                .subscribeOn(io())
        }
    }

}