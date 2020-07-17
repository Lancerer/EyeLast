package com.lancer.eyelast.demo.bar

import android.app.Activity
import android.view.View
import android.view.Window
import com.gyf.immersionbar.ImmersionBar

/**
 * @author lancer
 * @des
 * @Date 2020/7/17 13:38
 */
object StatusBarUtils {

    /**
     * statusBar高度
     */
    fun statusBarHeight(activity: Activity): Int {
        return ImmersionBar.getStatusBarHeight(activity)
    }

    /**
     * actionBar高度
     */
    fun actionBarHeight(activity: Activity): Int {
        return ImmersionBar.getActionBarHeight(activity)
    }

    /**
     * 是否有刘海屏
     */
    fun hasNotchScreen(activity: Activity): Boolean {
        return ImmersionBar.hasNotchScreen(activity)
    }

    /**
     * checkFitsSystemWindows
     */
    fun checkFitsSystemWindows(view: View): Boolean {
        return ImmersionBar.checkFitsSystemWindows(view)
    }

    /**
     * isSupportStatusBarDarkFont 是否支持改变状态栏字体改变颜色
     */
    fun isSupportStatusBarDarkFont(): Boolean {
        return ImmersionBar.isSupportStatusBarDarkFont()
    }

    /**
     * 隐藏状态栏
     */
    fun hideStatusBar(window: Window) {
        ImmersionBar.hideStatusBar(window)
    }

    /**
     * 显示状态栏
     */
    fun showStatusBar(window: Window) {
        ImmersionBar.showStatusBar(window)
    }

}