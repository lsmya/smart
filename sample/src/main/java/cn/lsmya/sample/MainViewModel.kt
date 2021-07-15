package cn.lsmya.sample

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import cn.lsmya.smart.base.BaseViewModel
import cn.lsmya.smart.extension.launch

class MainViewModel(application: Application) : BaseViewModel(application) {
    val msg = MutableLiveData<ArrayList<NewTypesModel>>()
    val adapter: MyAdapter by lazy { MyAdapter() }

    fun click(v: View) {
        launch({ RetrofitUtils.get().getNews() }) {
            msg.value = it
            adapter.setNewInstance(it)
        }
    }
}