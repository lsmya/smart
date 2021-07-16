package cn.lsmya.smart.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import cn.lsmya.smart.BR
import cn.lsmya.smart.R
import cn.lsmya.smart.VMUtils
import cn.lsmya.smart.immersionbar.immersionBar

open class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel>(@LayoutRes private val layoutId: Int) :
    AppCompatActivity() {
    private lateinit var binding: VB
    private var viewModel: VM? = null
    private var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        if (enableTranslucent()) {
            immersionBar {
                navigationBarColor("#00000000")
                statusBarDarkFont(enableLightMode())
                keyboardEnable(keyboardEnable())  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
            }
        }
        getBinding().lifecycleOwner = this
        getBinding().setVariable(BR.viewModel, getViewModel())
        getViewModel().mLoading.observe(this) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }
        getViewModel().mToast.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    fun getBinding(): VB = binding
    fun getViewModel(): VM {
        if (viewModel == null) {
            val cls: Class<VM> = VMUtils.getVMClass(javaClass)
            viewModel = ViewModelProvider(this).get(cls)
        }
        return viewModel!!
    }


    /**
     * 显示加载loading动画
     */
    open fun showLoading() {
        if (loadingDialog == null) {
            val view = LayoutInflater.from(this).inflate(R.layout.common_loading, null);
            loadingDialog = Dialog(this, R.style.common_dialog_loading)
            loadingDialog!!.setContentView(view)
        }
        if (loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        } else {
            loadingDialog!!.show()
        }
    }

    /**
     * 隐藏加载loading动画
     */
    open fun hideLoading() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }
    }

    /**
     * 是否需要沉浸式
     *
     * @return
     */
    protected open fun enableTranslucent(): Boolean {
        return true
    }

    /**
     * 解决软键盘与底部输入框冲突问题
     *
     * @return
     */
    protected open fun keyboardEnable(): Boolean {
        return true
    }

    /**
     * 是否开启亮色状态栏
     *
     * @return
     */
    protected open fun enableLightMode(): Boolean {
        return true
    }
}