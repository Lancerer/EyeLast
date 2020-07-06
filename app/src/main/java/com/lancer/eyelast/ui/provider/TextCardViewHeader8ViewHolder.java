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
public class TextCardViewHeader8ViewHolder extends BaseItemProvider<Daily.Item> {
    private DailyFragment dailyFragment;


    public TextCardViewHeader8ViewHolder(DailyFragment dailyFragment) {
        this.dailyFragment = dailyFragment;

    }

    @Override
    public int getItemViewType() {
        return Const.ItemViewType.TEXT_CARD_HEADER8;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_text_card_type_header_eight;
    }

    @Override
    public void convert(@NotNull BaseViewHolder holder, Daily.Item item) {
        holder.setText(R.id.tvTitle8, item.getData().getText());
        holder.setText(R.id.tvRightText8, item.getData().getRightText());
    }
}
