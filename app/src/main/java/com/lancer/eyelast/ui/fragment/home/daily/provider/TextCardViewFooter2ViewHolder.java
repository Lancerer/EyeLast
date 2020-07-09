package com.lancer.eyelast.ui.fragment.home.daily.provider;

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
public class TextCardViewFooter2ViewHolder extends BaseItemProvider<Daily.Item> {
    private DailyFragment dailyFragment;

    public TextCardViewFooter2ViewHolder(DailyFragment dailyFragment) {
        this.dailyFragment = dailyFragment;
    }

    @Override
    public int getItemViewType() {
        return Const.ItemViewType.TEXT_CARD_FOOTER2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_text_card_type_footer_two;
    }

    @Override
    public void convert(@NotNull BaseViewHolder holder, Daily.Item item) {
        holder.setText(R.id.tvFooterRightText2, item.getData().getText());
        //TODO
    }
}
