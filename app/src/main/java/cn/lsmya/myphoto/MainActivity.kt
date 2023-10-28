package cn.lsmya.myphoto

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import cn.lsmya.myphoto.databinding.ActivityMainBinding
import cn.lsmya.smart.base.BaseActivity
import cn.lsmya.smart.base.WebActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.QuickAdapterHelper
import com.chad.library.adapter.base.viewholder.QuickViewHolder

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val adapter by lazy { MyAdapter() }
    override fun createViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initUI() {
        getBinding().recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, v, position ->
            when (adapter.getItem(position)) {
                WebActivity::class.java -> {
                    WebActivity.start(this, "", "http://www.xiaoshoukeji.cn/privacy.html")
                }

                else -> {
                    startActivity(Intent(this, adapter.getItem(position)))
                }
            }
        }

    }

    override fun initData() {
        adapter.add(TestSplashActivity::class.java)
        adapter.add(TestGestureActivity::class.java)
        adapter.add(WebActivity::class.java)
    }

    class MyAdapter : BaseQuickAdapter<Class<*>, QuickViewHolder>() {

        override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: Class<*>?) {
            holder.setText(R.id.text, item?.name)
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