package tevins.com.weizhishop.ui.adapter;

import android.content.Context;

import java.util.List;

import tevins.com.weizhishop.ui.viewholder.BaseViewHolder;

/**
 * Created by Tevin on 2017/12/1 001.
 */

public abstract class SimpleAdapter<T> extends BaseAdapter<T, BaseViewHolder> {
    public SimpleAdapter(Context context, List dataList, int layoutId) {
        super(context, dataList, layoutId);
    }
}
