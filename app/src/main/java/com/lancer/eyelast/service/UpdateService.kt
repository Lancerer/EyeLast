package com.lancer.eyelast.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * @author lancer
 * @des
 * @Date 2020/7/6 17:25
 */
class UpdateService:Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}