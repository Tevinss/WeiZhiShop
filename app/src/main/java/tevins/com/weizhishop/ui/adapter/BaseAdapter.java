package tevins.com.weizhishop.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tevins.com.weizhishop.ui.viewholder.BaseViewHolder;

/**
 * Created by Tevin on 2017/12/1 001.
 */

public abstract class BaseAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int HOME_ITME_TYPE_NORMAL = 0;
    private static final int HOME_ITME_TYPE_OTHER = 1;
    public Context mContext;
    private List<T> mDataList;
    private int mLayoutId;
    private OnItemClickListener mOnItemClickListener;

    public BaseAdapter(Context context, List<T> dataList, int layoutId) {
        mContext = context;
        mDataList = dataList;
        mLayoutId = layoutId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        BaseViewHolder baseViewHolder = new BaseViewHolder(view, mOnItemClickListener);
        return baseViewHolder;
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T t = getItemData(position);
        updateView(holder, t);
    }

    /**
     * 更新頁面数据，具体实现由子类实现
     *
     * @param holder 持有视图引用的holder
     * @param t      条目的数据
     */
    public abstract void updateView(BaseViewHolder holder, T t);


    private T getItemData(int position) {
        if (position > mDataList.size()) {
            return null;
        }
        return mDataList.get(position);
    }

    @Override
    public int getItemCount() {
        if (mDataList == null || mDataList.size() <= 0) {
            return 0;
        }
        return mDataList.size();
    }


    public void addData(List<T> dataList) {
        addData(0, dataList);
    }

    public List<T> getDataList() {
        return mDataList;
    }

    /**
     * 从位置position开始添加数据
     *
     * @param dataList
     */
    public void addData(int position, List<T> dataList) {
        if (dataList != null && dataList.size() > 0) {
            mDataList.addAll(position, dataList);
            this.notifyItemRangeChanged(position, dataList.size());
        }
    }

    /**
     * 清空数据
     */
    public void clearData() {
        int size = mDataList.size();
        mDataList.clear();
        this.notifyItemRangeRemoved(0, size);
    }

    /**
     * item点击接口回调
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
