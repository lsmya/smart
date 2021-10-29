package cn.lsmya.smart.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import cn.lsmya.smart.BR
import cn.lsmya.smart.VMUtils

abstract class BaseMvvmFragment<VB : ViewDataBinding, VM : BaseViewModel>(@LayoutRes private val layoutId: Int) :
    BaseFragment() {
    private lateinit var binding: VB
    private var viewModel: VM? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, getViewModel())
        getViewModel().mLoading.observe(viewLifecycleOwner) {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        }
        getViewModel().mToast.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    fun getViewModel(): VM {
        if (viewModel == null) {
            val cls: Class<VM> = VMUtils.getVMClass(javaClass)
            viewModel = ViewModelProvider(this).get(cls)
        }
        return viewModel!!
    }

}