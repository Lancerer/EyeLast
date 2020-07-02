package com.lancer.eyelast

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.lancer.eyelast.utils.DisplayManager
import kotlin.properties.Delegates

/**
 * @author lancer
 * @des
 * @Date 2020/7/2 13:07
 */
class BaseApplication : Application() {
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