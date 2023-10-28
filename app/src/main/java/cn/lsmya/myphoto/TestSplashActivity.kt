package cn.lsmya.myphoto

import android.view.animation.Animation
import cn.lsmya.myphoto.databinding.ActivityTestSplashBinding
import cn.lsmya.smart.base.SplashActivity
import cn.lsmya.smart.extension.countDown
import cn.lsmya.smart.extension.loge
import cn.lsmya.smart.extension.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

class TestSplashActivity : SplashActivity<ActivityTestSplashBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_test_splash
    override fun getAnimationListener(): Animation.AnimationListener {
        return object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                loge("动画开始")
            }

            override fun onAnimationEnd(p0: Animation?) {
                loge("动画结束")
            }

            override fun onAnimationRepeat(p0: Animation?) {
                loge("动画onAnimationRepeat")
            }

        }
    }

    override fun createViewBinding(): ActivityTestSplashBinding {
        return  ActivityTestSplashBinding.inflate(layoutInflater)
    }

    override fun initUI() {
    }

    override fun initData() {
        startCountDown()
    }

    private fun startCountDown() {
        var timeDownScope: CoroutineScope? = null
        countDown(
            countTime = 5,

            start = {
                timeDownScope = it
            },
            end = {
                toast("结速倒计时")
            },
            next = {
                if (it == 50) {
                    timeDownScope?.cancel()
                }
            })
    }

}