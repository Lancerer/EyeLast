package com.lancer.eyelast

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.lancer.eyelast.utils.DisplayManager
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import leakcanary.LeakCanary
import kotlin.properties.Delegates

/**
 * @author lancer
 * @des
 * @Date 2020/7/2 13:07
 */
class BaseApplication : Application() {
    init {
        SmartRefreshLayout.setDefaultRefreshInitializer { context, layout ->
            layout.setEnableLoadMore(true)
            layout.setEnableLoadMoreWhenContentNotFull(true)
        }

        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setEnableHeaderTranslationContent(true)
            ClassicsHeader(context).setDrawableSize(20f)
        }

        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            layout.setEnableFooterFollowWhenNoMoreData(true)
            layout.setEnableFooterTranslationContent(true)
            layout.setFooterHeight(153f)
            layout.setFooterTriggerRate(0.6f)
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }
    companion object {
        var context: Context by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        DisplayManager.init(this)
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}