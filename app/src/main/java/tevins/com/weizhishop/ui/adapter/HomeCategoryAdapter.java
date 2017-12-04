package tevins.com.weizhishop.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import tevins.com.weizhishop.R;
import tevins.com.weizhishop.model.bean.HomeCategoryInfo;

/**
 * Created by tevins on 2017/11/29/0029.
 */

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {
    private static final int HOME_ITME_TYPE_LEFT = 0;
    private static final int HOME_ITME_TYPE_RIGHT = 1;

    private List<HomeCategoryInfo> mHomeCategories = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public HomeCategoryAdapter(Context context) {
        mContext = context;
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
        HomeCategoryInfo homeCategory = mHomeCategories.get(position);
        if (homeCategory != null) {
            holder.mTvHomeItemTitle.setText(homeCategory.getTitle());//设置标题
            Picasso.with(mContext).load(homeCategory.getCpOne().getImgUrl()).into(holder.mIvHomeItemBig);
            Picasso.with(mContext).load(homeCategory.getCpTwo().getImgUrl()).into(holder.mIvHomeItemSmallTop);
            Picasso.with(mContext).load(homeCategory.getCpThree().getImgUrl()).into(holder.mIvHomeItemSmallBottom);
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

    public void setData(List<HomeCategoryInfo> homeCategories) {
        mHomeCategories = homeCategories;
        this.notifyDataSetChanged();
    }
}
