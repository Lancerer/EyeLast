package com.lancer.eyelast.ui.fragment.notifications.push

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lancer.eyelast.R
import com.lancer.eyelast.bean.PushMessage
import com.lancer.eyelast.extension.load
import com.lancer.eyelast.utils.DateUtil

/**
 * @author lancer
 * @des
 * @Date 2020/7/2 15:11
 */
class PushAdapter :
    BaseQuickAdapter<PushMessage.Message, BaseViewHolder>(R.layout.item_notification_push, null),LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: PushMessage.Message) {
        val imageView = holder.getView(R.id.ivAvatar) as ImageView
      //  imageView.load(item.icon) {error(R.mipmap.ic_launcher)}
        holder.setText(R.id.tvTitle, item.title)
        holder.setText(R.id.tvContent, item.content)
        holder.setText(R.id.tvTime, DateUtil.getConvertedDate(item.date))
    }
}