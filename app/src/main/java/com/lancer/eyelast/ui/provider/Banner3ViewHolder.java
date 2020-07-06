package com.lancer.eyelast.ui.provider;

import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lancer.eyelast.Const;
import com.lancer.eyelast.R;
import com.lancer.eyelast.bean.Daily;
import com.lancer.eyelast.extension.ImageViewKt;
import com.lancer.eyelast.ui.fragment.home.daily.DailyFragment;

import org.jetbrains.annotations.NotNull;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * @author lancer
 * @des
 * @Date 2020/7/6 8:52
 */
public class Banner3ViewHolder extends BaseItemProvider<Daily.Item> {
    private DailyFragment dailyFragment;

    public Banner3ViewHolder(DailyFragment dailyFragment) {
        this.dailyFragment = dailyFragment;
    }

    @Override
    public int getItemViewType() {
        return Const.ItemViewType.BANNER3;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_banner_three_type;
    }

    @Override
    public void convert(@NotNull BaseViewHolder holder, Daily.Item item) {
        ImageView ivPicture = holder.getView(R.id.ivPicture);
        ImageView ivAvatar = holder.getView(R.id.ivAvatar);
        ImageViewKt.load(ivPicture, item.getData().getImage(), 4f, RoundedCornersTransformation.CornerType.ALL);
        ImageViewKt.load(ivAvatar, item.getData().getHeader().getIcon(), 4f, RoundedCornersTransformation.CornerType.ALL);
        holder.setText(R.id.tvTitle, item.getData().getHeader().getTitle());
        holder.setText(R.id.tvDescription, item.getData().getHeader().getDescription());
        if (item.getData().getLabel() != null) {
            if (TextUtils.isEmpty(item.getData().getLabel().getText())) {
                holder.setVisible(R.id.tvLabel, false);
            } else {
                holder.setVisible(R.id.tvLabel, true);
            }
            holder.setText(R.id.tvLabel, item.getData().getLabel().getText());
        } else {
            holder.setText(R.id.tvLabel, "");
        }

        //TODO

    }
}
