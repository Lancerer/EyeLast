package com.lancer.eyelast.ui.provider;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lancer.eyelast.Const;
import com.lancer.eyelast.R;
import com.lancer.eyelast.bean.Daily;
import com.lancer.eyelast.ui.fragment.home.daily.DailyFragment;

import org.jetbrains.annotations.NotNull;

/**
 * @author lancer
 * @des
 * @Date 2020/7/6 8:52
 */
public class TextCardViewHeader7ViewHolder extends BaseItemProvider<Daily.Item> {
    private DailyFragment dailyFragment;


    public TextCardViewHeader7ViewHolder(DailyFragment dailyFragment) {
        this.dailyFragment = dailyFragment;

    }

    @Override
    public int getItemViewType() {
        return Const.ItemViewType.TEXT_CARD_HEADER7;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_text_card_type_header_seven;
    }

    @Override
    public void convert(@NotNull BaseViewHolder holder, Daily.Item item) {
        holder.setText(R.id.tvTitle7, item.getData().getText());
        holder.setText(R.id.tvRightText7, item.getData().getRightText());
        //TODO 点击事件

    }
}
