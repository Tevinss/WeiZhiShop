package tevins.com.weizhishop.ui.adapter;

import android.content.Context;

import java.util.List;

import tevins.com.weizhishop.R;
import tevins.com.weizhishop.model.bean.CategoryInfo;
import tevins.com.weizhishop.ui.viewholder.BaseViewHolder;

/**
 * Created by tevins on 2017/12/4/0004.
 */

public class CategoyrAdapter extends SimpleAdapter<CategoryInfo> {
    public CategoyrAdapter(Context context, List dataList, int layoutId) {
        super(context, dataList, layoutId);
    }


    @Override
    public void updateView(BaseViewHolder holder, CategoryInfo categoryInfo) {
        holder.getTextView(R.id.tv_category).setText(categoryInfo.getName());
    }
}
