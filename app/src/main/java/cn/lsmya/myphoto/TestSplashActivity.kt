package cn.lsmya.myphoto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import cn.lsmya.smart.base.SplashActivity
import cn.lsmya.smart.extension.loge

class TestSplashActivity : SplashActivity() {

    override fun getContentViewId(): Int =R.layout.activity_test_splash
    override fun getAnimationListener(): Animation.AnimationListener {
        return object :Animation.AnimationListener{
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
}