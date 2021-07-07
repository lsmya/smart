package cn.lsmya.sample

import android.os.Bundle
import cn.lsmya.sample.databinding.ActivityMainBinding
import cn.lsmya.smart.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel().adapter.setOnItemClickListener { _, view, position ->

        }
        getViewModel().msg.observe(this){

        }
    }
}