package com.lancer.eyelast.ui.fragment.home.daily.provider;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lancer.eyelast.Const;
import com.lancer.eyelast.R;
import com.lancer.eyelast.bean.Daily;
import com.lancer.eyelast.bean.FollowCard;
import com.lancer.eyelast.ui.activity.video.VideoActivity;
import com.lancer.eyelast.ui.fragment.home.daily.DailyFragment;

import org.jetbrains.annotations.NotNull;

/**
 * @author lancer
 * @des
 * @Date 2020/7/3 16:28
 */
public class FollowCardViewHolder extends BaseItemProvider<Daily.Item> {
    private DailyFragment dailyFragment;

    public FollowCardViewHolder(DailyFragment dailyFragment) {
        this.dailyFragment = dailyFragment;
    }

    @Override
    public int getItemViewType() {
        return Const.ItemViewType.FOLLOW_CARD;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_follow_card_type;
    }

    @Override
    public void convert(@NotNull BaseViewHolder holder, Daily.Item item) {
        ImageView imageView = holder.getView(R.id.ivVideo);
        ImageView imageView1 = holder.getView(R.id.ivAvatar);
        Glide.with(context).load(item.getData().getContent().getData().getCover().getFeed()).into(imageView);
        Glide.with(context).load(item.getData().getHeader().getIcon()).into(imageView1);
        holder.setText(R.id.tvVideoDuration, item.getData().getHeader().getDescription());
        holder.setText(R.id.tvTitle, item.getData().getHeader().getTitle());
        if (item.getData().getContent().getData().getAd()) {
            holder.setVisible(R.id.tvLabel, true);
        } else {
            holder.setGone(R.id.tvLabel, true);
        }
        if (item.getData().getContent().getData().getLibrary().equals("DAILY")) {
            holder.setVisible(R.id.ivChoiceness, true);
        } else {
            holder.setVisible(R.id.ivChoiceness, false);
        }
        holder.itemView.setOnClickListener(v -> {
            FollowCard followCard = item.getData().getContent().getData();
            if (followCard.getAd() || followCard.getAuthor() == null) {
                VideoActivity.Companion.start(dailyFragment.getActivity(), followCard.getId());
            } else {
                VideoActivity.Companion.start(dailyFragment.getActivity(), new VideoActivity.VideoInfo(
                        followCard.getId(),
                        followCard.getPlayUrl(),
                        followCard.getTitle(),
                        followCard.getDescription(),
                        followCard.getCategory(),
                        followCard.getLibrary(),
                        followCard.getConsumption(),
                        followCard.getCover(),
                        followCard.getAuthor(),
                        followCard.getWebUrl()
                ));
            }
        });

    }
}
