package cn.lsmya.smart.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import cn.lsmya.smart.BR
import cn.lsmya.smart.VMUtils

open class BaseMvvmActivity<VB : ViewDataBinding, VM : BaseViewModel>(@LayoutRes private val layoutId: Int) :
    BaseActivity() {
    private lateinit var binding: VB
    private var viewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
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
}