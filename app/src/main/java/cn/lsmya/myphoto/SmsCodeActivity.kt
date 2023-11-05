package cn.lsmya.myphoto

import cn.lsmya.myphoto.databinding.ActivitySmsCodeBinding
import cn.lsmya.smart.base.BaseActivity

class SmsCodeActivity : BaseActivity<ActivitySmsCodeBinding>() {
    override fun createViewBinding(): ActivitySmsCodeBinding {
        return  ActivitySmsCodeBinding.inflate(layoutInflater)
    }

    override fun initUI() {
    }

    override fun initData() {
    }

}