package cn.ycbjie.ycaudioplayer.kotlin.view.activity

import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import cn.ycbjie.ycaudioplayer.R
import cn.ycbjie.ycaudioplayer.kotlin.base.KotlinConstant
import cn.ycbjie.ycaudioplayer.kotlin.contract.AndroidCollectContract
import cn.ycbjie.ycaudioplayer.kotlin.model.bean.HomeData
import cn.ycbjie.ycaudioplayer.kotlin.presenter.AndroidCollectPresenter
import cn.ycbjie.ycaudioplayer.ui.web.WebViewActivity


/**
 * <pre>
 *     @author 杨充
 *     blog  :
 *     time  : 2017/05/30
 *     desc  : 详情页面
 *     revise:
 * </pre>
 */
class AndroidDetailActivity : WebViewActivity() , AndroidCollectContract.View{

    private var isCollect: Boolean = false

    private var homeData: HomeData? = null

    private var articleId: Int = 0

    private val presenter: AndroidCollectPresenter by lazy {
        AndroidCollectPresenter(this)
    }

    companion object {
        const val INTENT_TAG_HOME_DATA = "homeData"
        const val INTENT_TAG_BOOLEAN_IS_COLLECT = "isCollect"
        const val INTENT_TAG_INT_ARTICLE_ID = "articleId"
        fun lunch(context: Activity?, homeData: HomeData, isCollect: Boolean, articleId: Int) {
            val intent = Intent(context, AndroidDetailActivity::class.java)
            intent.putExtra(INTENT_TAG_HOME_DATA, homeData)
            intent.putExtra(INTENT_TAG_BOOLEAN_IS_COLLECT, isCollect)
            intent.putExtra(INTENT_TAG_INT_ARTICLE_ID, articleId)
            context?.startActivity(intent)
        }
    }

    override fun initIntentData() {
        homeData = intent.getParcelableExtra(INTENT_TAG_HOME_DATA)
        isCollect = intent.getBooleanExtra(INTENT_TAG_BOOLEAN_IS_COLLECT, false)
        articleId = intent.getIntExtra(INTENT_TAG_INT_ARTICLE_ID, 0)
        if (homeData == null) {
            finish()
            return
        }
        url = homeData?.link
        title = homeData?.title
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isCollect) {
            menu?.add(0, 10, 0, "unCollect")?.setIcon(R.drawable.ic_like)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        } else {
            menu?.add(0, 10, 0, "collect")?.setIcon(R.drawable.ic_like_empty)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        }
        menu?.add(0, 11, 0, "collect")?.setIcon(R.drawable.ic_menu_share)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == 10) {
            if (isCollect) {
                presenter.unCollectArticle(articleId)
            } else {
                presenter.collectInArticle(articleId)
            }
            isCollect = !isCollect
            val intent = Intent()
            intent.putExtra(KotlinConstant.INTENT_IS_COLLECT, isCollect)
            intent.putExtra(KotlinConstant.COLLECT_ID, articleId)
            intent.action = KotlinConstant.COLLECT_STATUS
            sendBroadcast(intent)
            invalidateOptionsMenu()
        }else if(item?.itemId == 11){
            //ShareUtils.shareText(this,homeData?.title +" "+homeData?.link,homeData?.title,homeData?.title)
        }

        return super.onOptionsItemSelected(item)
    }
}