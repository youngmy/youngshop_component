package com.young.module.demo.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.young.module.demo.R;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.young.module.demo.bean.StaggerdRecyclerViewBean;
import com.young.module.demo.databinding.ItemStaggerdRecyclerViewBinding;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class StaggerdRecyclerViewAdapter extends RecyclerView.Adapter<StaggerdRecyclerViewAdapter.VH> {
    private Context context;
    private List<StaggerdRecyclerViewBean> rvBeans;
    private final static String TAG = "StaggerdRecyclerViewAdapter";

    public StaggerdRecyclerViewAdapter(Context context, List<StaggerdRecyclerViewBean> rvBeans) {
        this.context = context;
        this.rvBeans = rvBeans;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.item_staggerd_recycler_view, parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        //try {
        ItemStaggerdRecyclerViewBinding binding = DataBindingUtil.bind(holder.itemView);
        //binding.rvTextView.setText(rvBeans.get(position).getText());

        binding.rvTextView.setHeight((int) (Math.random() * 300 + 200));
        binding.setItem(rvBeans.get(position));
            /*
            //Set size
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;//这个参数设置为true才有效，
            Bitmap bmp = BitmapFactory.decodeFile(rvBeans.get(position).getUrl(), options);
            //这里的bitmap是个空
            int outHeight = options.outHeight;
            int outWidth = options.outWidth;
            Glide.with(context).load(rvBeans.get(position).getUrl()).override(outWidth,
                    outHeight).into(binding.rvImageView);
        } catch (Exception e) {
            Log.e(TAG, ">>>>>>onbindViewHolder error: " + e.getMessage(), e);
        }
             */
    }

    @Override
    public int getItemCount() {
        return rvBeans.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }

    //增加外部调用增加一条记录
    public void refreshDatas(List<StaggerdRecyclerViewBean> datas) {
        int pc=0;
        if (datas != null && datas.size() > 0) {
            int oldSize = rvBeans.size();
            //List<RVBean> refreshedData = new ArrayList<RVBean>();
            boolean isItemExisted = false;
            for (Iterator<StaggerdRecyclerViewBean> newData = datas.iterator(); newData.hasNext(); ) {
                StaggerdRecyclerViewBean a = newData.next();
                for (Iterator<StaggerdRecyclerViewBean> existedData = rvBeans.iterator(); existedData.hasNext(); ) {
                    StaggerdRecyclerViewBean b = existedData.next();
                    if (b.equals(a)) {
                        {
                            isItemExisted = true;
                            //Log.i(TAG, b.getText() + " -> " + b.getUrl() + " is existed");
                            break;
                        }
                    }
                }
                if (!isItemExisted) {
                    pc+=1;
                    rvBeans.add(a);
                }
            }
            Log.i(TAG,">>>>>>pc->"+pc);
            if(pc>0){
                notifyItemRangeChanged(oldSize,rvBeans.size());
            }
        }
    }


}
