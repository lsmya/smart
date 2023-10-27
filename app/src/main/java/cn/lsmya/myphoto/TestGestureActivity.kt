package cn.lsmya.myphoto

import android.os.Bundle
import cn.lsmya.smart.base.GestureActivity
import cn.lsmya.smart.extension.loge

class TestGestureActivity : GestureActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_gesture)

    }

    override fun onLeftFling() {
        //TODO:向左滑动
        loge("向左滑动")
    }

    override fun onRightFling(): Boolean {
        //TODO:向右滑动，默认执行finish，返回为true表示拦截事件。
        loge("向右滑动")
        return false
    }
}