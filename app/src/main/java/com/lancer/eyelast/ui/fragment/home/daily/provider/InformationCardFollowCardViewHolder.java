package com.lancer.eyelast.ui.fragment.home.daily.provider;

import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lancer.eyelast.Const;
import com.lancer.eyelast.R;
import com.lancer.eyelast.bean.Daily;
import com.lancer.eyelast.extension.ImageViewKt;
import com.lancer.eyelast.ui.fragment.home.commend.CommendAdapter;
import com.lancer.eyelast.ui.fragment.home.daily.DailyFragment;
import com.lancer.eyelast.utils.ActionUrl;

import org.jetbrains.annotations.NotNull;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * @author lancer
 * @des
 * @Date 2020/7/6 8:52
 */
public class InformationCardFollowCardViewHolder extends BaseItemProvider<Daily.Item> {
    private DailyFragment dailyFragment;

    public InformationCardFollowCardViewHolder(DailyFragment dailyFragment) {
        this.dailyFragment = dailyFragment;

    }

    @Override
    public int getItemViewType() {
        return Const.ItemViewType.INFORMATION_CARD;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_information_card_type;
    }

    @Override
    public void convert(@NotNull BaseViewHolder holder, Daily.Item item) {
        ImageView ivCover = holder.getView(R.id.ivCover);
        RecyclerView recyclerView = holder.getView(R.id.recyclerView);
        ImageViewKt.load(ivCover,
                item.getData().getBackgroundImage(),
                4f,
                RoundedCornersTransformation.CornerType.TOP
        );

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new CommendAdapter.InformationCardFollowCardAdapter(dailyFragment.getActivity()
                , item.getData().getActionUrl(), item.getData().getTitleList()));
        holder.itemView.setOnClickListener(v -> ActionUrl.INSTANCE.process(
                dailyFragment, item.getData().getActionUrl(), ""
        ));
    }
}
