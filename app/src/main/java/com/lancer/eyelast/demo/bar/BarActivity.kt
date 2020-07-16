package com.lancer.eyelast.demo.bar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lancer.eyelast.R
import kotlinx.android.synthetic.main.activity_bar.*

class BarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar)
        init()
    }

    private fun init() {
        toolBar.title = "bar测试"
    }
}