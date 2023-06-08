package com.young.module.home.adapter;

import android.content.Context;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.young.module.home.R;
import com.young.module.home.bean.SongBean;
import com.young.utils.DateTimeUtils;

import java.util.List;

/**
 * 音乐列表适配器
 *
 * @author llw
 */


public class MusicListAdapter extends BaseQuickAdapter<SongBean, BaseViewHolder> {

    private Context mContext;

    public MusicListAdapter(Context mContext, int layoutResId, @Nullable List<SongBean> data) {
        super(layoutResId, data);
        this.mContext = mContext;
    }


    @Override
    protected void convert(BaseViewHolder helper, SongBean item) {
        //给控件赋值
        int duration = item.duration;
        String time = DateTimeUtils.formatTime(duration);

        //歌曲名称
        helper.setText(R.id.songNameView, item.getSong().trim())
                //歌手 - 专辑
                .setText(R.id.singerTextView, item.getSinger() + " - " + item.getAlbum())
                //歌曲时间
                .setText(R.id.durationTimeView, time)
                //歌曲序号，因为getAdapterPosition得到的位置是从0开始，故而加1，
                //是因为位置和1都是整数类型，直接赋值给TextView会报错，故而拼接了""
                .setText(R.id.positionTextView, helper.getAdapterPosition() + 1 + "");

        //给item添加点击事件，点击之后传递数据到播放页面或者在本页面进行音乐播放
//        helper.addOnClickListener(R.id.item_music);

        //点击后改变文字颜色
        if (item.isCheck()) {
            helper.setTextColor(R.id.positionTextView, mContext.getColor(R.color.gold_color))
                    .setTextColor(R.id.songNameView, mContext.getColor(R.color.gold_color))
                    .setTextColor(R.id.singerTextView, mContext.getColor(R.color.gold_color))
                    .setTextColor(R.id.durationTimeView, mContext.getColor(R.color.gold_color));
        } else {
            helper.setTextColor(R.id.positionTextView, mContext.getColor(R.color.white))
                    .setTextColor(R.id.songNameView, mContext.getColor(R.color.white))
                    .setTextColor(R.id.singerTextView, mContext.getColor(R.color.white))
                    .setTextColor(R.id.durationTimeView, mContext.getColor(R.color.white));
        }

    }

    /**
     * 刷新数据
     */
    public void changeState() {
        notifyDataSetChanged();
    }


}
