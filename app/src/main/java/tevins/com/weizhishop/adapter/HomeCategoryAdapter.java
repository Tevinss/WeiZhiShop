package tevins.com.weizhishop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tevins.com.weizhishop.R;
import tevins.com.weizhishop.bean.HomeCategory;

/**
 * Created by yewyw on 2017/11/29/0029.
 */

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {
    private static final int HOME_ITME_TYPE_LEFT = 0;
    private static final int HOME_ITME_TYPE_RIGHT = 1;

    private ArrayList<HomeCategory> mHomeCategories;
    private LayoutInflater mLayoutInflater;

    public HomeCategoryAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mLayoutInflater = LayoutInflater.from(parent.getContext());
        if (HOME_ITME_TYPE_LEFT == viewType) {
            View view = mLayoutInflater.inflate(R.layout.item_home_cardviewfir, parent, false);
            return new ViewHolder(view);
        }
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_home_cardviewsec, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeCategory homeCategory = mHomeCategories.get(position);
        if (homeCategory != null) {
            holder.mTvHomeItemTitle.setText(homeCategory.getName());
            holder.mIvHomeItemBig.setImageResource(homeCategory.getImgBig());
            holder.mIvHomeItemSmallTop.setImageResource(homeCategory.getImgSmallTop());
            holder.mIvHomeItemSmallBottom.setImageResource(homeCategory.getImgSmallBottom());
        }
    }

    @Override
    public int getItemCount() {
        return mHomeCategories.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return HOME_ITME_TYPE_RIGHT;
        }
        return HOME_ITME_TYPE_LEFT;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvHomeItemTitle;
        ImageView mIvHomeItemBig;
        ImageView mIvHomeItemSmallTop;
        ImageView mIvHomeItemSmallBottom;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvHomeItemTitle = (TextView) itemView.findViewById(R.id.tv_home_item_title);
            mIvHomeItemBig = (ImageView) itemView.findViewById(R.id.iv_home_item_big);
            mIvHomeItemSmallTop = (ImageView) itemView.findViewById(R.id.iv_home_item_small_top);
            mIvHomeItemSmallBottom = (ImageView) itemView.findViewById(R.id.iv_home_item_small_bottom);
        }
    }

    public void setData(ArrayList<HomeCategory> homeCategories) {
        mHomeCategories = homeCategories;
        this.notifyDataSetChanged();
    }
}
