package cn.lsmya.myphoto

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import cn.lsmya.myphoto.databinding.ActivityMainBinding
import cn.lsmya.smart.base.BaseActivity
import cn.lsmya.smart.base.WebActivity
import cn.lsmya.smart.extension.loge
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val adapter by lazy { MyAdapter() }
    override fun createViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initUI() {
        getBinding().recyclerView.adapter = adapter
        adapter.setOnItemClickListener { _, v, position ->
            loge(position)
            when (adapter.getItem(position)) {
                WebActivity::class.java -> {
                    WebActivity.start(this, "测试标题", "http://www.xiaoshoukeji.cn/privacy.html")
                }

                else -> {
                    startActivity(Intent(this, adapter.getItem(position)))
                }
            }
        }

    }

    override fun initData() {
        adapter.addAll(
            arrayListOf(
                TestSplashActivity::class.java,
                WebActivity::class.java,
                VideoActivity::class.java,
                SmsCodeActivity::class.java
            )
        )
    }

    class MyAdapter : BaseQuickAdapter<Class<*>, QuickViewHolder>() {

        override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: Class<*>?) {
            holder.setText(R.id.text, item?.simpleName)
        }

        override fun onCreateViewHolder(
            context: Context,
            parent: ViewGroup,
            viewType: Int
        ): QuickViewHolder {
            return QuickViewHolder(R.layout.item_main, parent)
        }

    }

}