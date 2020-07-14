package com.lancer.eyelast.ui.activity.login

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import com.lancer.eyelast.R
import com.lancer.eyelast.base.BaseActivity
import com.lancer.eyelast.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    private val animatorSetOne by lazy {
        AnimatorSet()
    }
    private val animatorSetTwo by lazy {
        AnimatorSet()
    }
    private val animatorSet by lazy {
        AnimatorSet()
    }

    override fun initView() {
        initAnimation()
    }

    private fun initAnimation() {
        val animatorAlpha1 = ObjectAnimator.ofFloat(binding.loginBgImage1, "alpha", 1.0f, 0f)
        val animatorAlpha2 = ObjectAnimator.ofFloat(binding.loginBgImage2, "alpha", 0f, 1.0f)
        val animatorScale1 = ObjectAnimator.ofFloat(binding.loginBgImage1, "scaleX", 1.0f, 1.3f)
        val animatorScale2 = ObjectAnimator.ofFloat(binding.loginBgImage1, "scaleY", 1.0f, 1.3f)
        animatorSetOne.duration = 5000
        animatorSetOne.play(animatorAlpha1).with(animatorAlpha2).with(animatorScale1)
            .with(animatorScale2)
        animatorSetOne.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                // 放大的View复位
                binding.loginBgImage1.scaleX = 1.0f;
                binding.loginBgImage1.scaleY = 1.0f;
            }

            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
        })

        val animator3 =
            ObjectAnimator.ofFloat(binding.loginBgImage2, "alpha", 1.0f, 0f)
        val animator4 =
            ObjectAnimator.ofFloat(binding.loginBgImage1, "alpha", 0f, 1.0f)
        val animatorScale3 =
            ObjectAnimator.ofFloat(binding.loginBgImage2, "scaleX", 1.0f, 1.3f)
        val animatorScale4 =
            ObjectAnimator.ofFloat(binding.loginBgImage2, "scaleY", 1.0f, 1.3f)
        animatorSetTwo.duration = 5000
        animatorSetTwo.play(animator3).with(animator4).with(animatorScale3).with(animatorScale4)
        animatorSetTwo.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                // 放大的View复位
                binding.loginBgImage2.scaleX = 1.0f
                binding.loginBgImage2.scaleY = 1.0f
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

        animatorSet.playSequentially(animatorSetOne, animatorSetTwo)
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                //循环播放
                animation.start()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animatorSet.start()
    }

    override fun onPause() {
        super.onPause()
        animatorSet.cancel()
    }

    override fun initData() {
    }

    override fun initLayout(): Int = R.layout.activity_login

}