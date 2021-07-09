package cn.lsmya.smart.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

open class BaseViewModel (application: Application) : AndroidViewModel(application) {
    val mLoading = MutableLiveData<Boolean>()
    val mToast = MutableLiveData<String>()

    fun showLoading() {
        mLoading.value = true
    }

    fun hideLoading() {
        mLoading.value = false
    }

    fun toast(msg: String) {
        mToast.value = msg
    }

    fun toast(msg: Int) {
        mToast.value = getApplication<Application>().getString(msg)
    }
}