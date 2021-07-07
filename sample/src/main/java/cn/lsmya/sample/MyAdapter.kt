package cn.lsmya.sample

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class MyAdapter :
    BaseQuickAdapter<NewTypesModel, BaseViewHolder>(R.layout.item) {

    override fun convert(holder: BaseViewHolder, item: NewTypesModel) {
        holder.setText(R.id.text, item.typeName)
    }
}