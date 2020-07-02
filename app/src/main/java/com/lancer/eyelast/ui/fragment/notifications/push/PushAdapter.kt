package com.lancer.eyelast.ui.fragment.notifications.push

import android.text.format.DateUtils
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
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
    BaseQuickAdapter<PushMessage.Message, BaseViewHolder>(R.layout.item_notification_push, null) {

    override fun convert(helper: BaseViewHolder, item: PushMessage.Message) {
        val imageView = helper.getView(R.id.ivAvatar) as ImageView
        imageView.load(item.icon)
        helper.setText(R.id.tvTitle, item.title)
        helper.setText(R.id.tvContent, item.content)
        helper.setText(R.id.tvTime, DateUtil.getConvertedDate(item.date))

    }
}