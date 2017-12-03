package tevins.com.weizhishop.utils;

import android.content.Context;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import tevins.com.weizhishop.R;
import tevins.com.weizhishop.model.bean.PageInfo;
import tevins.com.weizhishop.presenter.ApiCallback;
import tevins.com.weizhishop.utils.http.OkHttpHelper;

/**
 * Created by tevins on 2017/12/3 003.
 */

public class Pager {
    private static Builder builder;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFREH = 1;
    private static final int STATE_MORE = 2;
    private final OkHttpHelper mOkHttpHelper;
    private int state = STATE_NORMAL;//默认normal

    private Pager() {
        mOkHttpHelper = new OkHttpHelper();
        initRefreshLayout();
    }


    private void initRefreshLayout() {
        builder.mRefreshlayout.setLoadMore(builder.mCanLoadMore);
        builder.mRefreshlayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                builder.mRefreshlayout.setLoadMore(builder.mCanLoadMore);
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (builder.mPageIndex <= builder.mTotalPage) {
                    loadMoreData();
                } else {
                    ToastUtils.show(builder.mContext, R.string.nothing_more, Toast.LENGTH_SHORT);
                    builder.mRefreshlayout.finishRefreshLoadMore();
                    builder.mRefreshlayout.setLoadMore(false);//不能再出现加载更多的样式
                }

            }
        });

    }

    /**
     * 给外界调用，请求数据
     */
    public void getData() {
        requestData();
    }

    /**
     * 加载更多的数据
     */
    private void loadMoreData() {
        state = STATE_MORE;
        builder.mPageIndex++;
        requestData();//
    }

    /**
     * 请求数据
     */
    private void requestData() {
        HashMap params = buildParams();
        mOkHttpHelper.post(builder.mUrl, params, new RequestCallback());
    }

    /**
     * 构建请求页数和size的参数
     */
    private HashMap buildParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("curPage", builder.mPageIndex + "");
        params.put("pageSize", builder.mPageSize + "");
        return params;
    }

    /**
     * 重新请求数据
     */
    private void refreshData() {
        state = STATE_REFREH;
        builder.mPageIndex = 1;
        requestData();
    }

    public static Builder newBuilder() {
        builder = new Builder();
        return builder;
    }


    class RequestCallback<T> extends ApiCallback<PageInfo<T>> {
        public RequestCallback() {
            super.mType = builder.mType;
        }

        @Override
        public void onSuccess(Response response, PageInfo<T> pageInfo) {
            builder.mPageIndex = pageInfo.getCurrentPage();
            builder.mTotalPage = pageInfo.getTotalPage();
            builder.mPageSize = pageInfo.getPageSize();
            showData(pageInfo.getList(), pageInfo.getTotalPage(), pageInfo.getTotalCount());
        }

        @Override
        public void onError(Response response, int code, Exception e) {
            ToastUtils.show(builder.mContext, "请求错误:" + e);
            if (state == STATE_REFREH) {
                builder.mRefreshlayout.finishRefresh();
            } else if (state == STATE_MORE) {
                builder.mRefreshlayout.finishRefreshLoadMore();
            }
        }

        @Override
        public void onFailure(Call call, Exception e) {
            ToastUtils.show(builder.mContext, "请求失败:" + e);
            if (state == STATE_REFREH) {
                builder.mRefreshlayout.finishRefresh();
            } else if (state == STATE_MORE) {
                builder.mRefreshlayout.finishRefreshLoadMore();
            }
        }
    }

    /**
     * 拿到数据展示在view上
     *
     * @param list
     * @param totalPage
     * @param totalCount
     * @param <T>
     */
    private <T> void showData(List<T> list, int totalPage, int totalCount) {
        if (list == null || list.size() <= 0) {
            ToastUtils.show(builder.mContext, "没有数据");
            return;
        }

        switch (state) {
            case STATE_NORMAL://正常状态
                if (builder.mOnPageListener != null) {
                    builder.mOnPageListener.load(list, totalPage, totalCount);
                }
                break;
            case STATE_REFREH://刷新
                builder.mRefreshlayout.finishRefresh();
                if (builder.mOnPageListener != null) {
                    builder.mOnPageListener.refresh(list, totalPage, totalCount);
                }
                break;
            case STATE_MORE://加载更多
                builder.mRefreshlayout.finishRefreshLoadMore();
                if (builder.mOnPageListener != null) {
                    builder.mOnPageListener.loadMore(list, totalPage, totalCount);
                }
                break;

        }
    }

    public static class Builder {
        private Context mContext;
        private Type mType;

        private int mTotalPage = 1;
        private int mPageIndex = 1;
        /**
         * 默认是请求10条
         */
        private int mPageSize = 10;
        private MaterialRefreshLayout mRefreshlayout;
        private OnPageListener mOnPageListener;
        private String mUrl;
        private boolean mCanLoadMore = false;

        public Pager build(Context context, Type type) {
            mContext = context;
            mType = type;
            valid();
            return new Pager();
        }

        /**
         * 验证context是否为空，url是否有，MaterialRefreshLayout是否有
         */
        private void valid() {
            if (mContext == null) {
                throw new RuntimeException("content can't be null");
            } else if (mUrl == null || "".equals(mUrl)) {
                throw new RuntimeException("url can't be  null");
            } else if (mRefreshlayout == null) {
                throw new RuntimeException("mRefreshlayout can't be  null");
            }
        }

        public Builder setUrl(String url) {
            mUrl = url;
            return builder;
        }

        public Builder setLoadMore(boolean canLoadMore) {
            mCanLoadMore = canLoadMore;
            return builder;
        }


        public Builder setPageSize(int pageSize) {
            mPageSize = pageSize;
            return builder;
        }

        public Builder setRefreshLayout(MaterialRefreshLayout refreshlayoutHotWares) {
            mRefreshlayout = refreshlayoutHotWares;
            return builder;
        }

        public Builder setOnPageListener(OnPageListener onPageListener) {
            mOnPageListener = onPageListener;
            return builder;
        }
    }


    public interface OnPageListener<T> {
        void load(List<T> dataList, int totalPage, int totalCount);

        void refresh(List<T> dataList, int totalPage, int totalCount);

        void loadMore(List<T> dataList, int totalPage, int totalCount);
    }
}
