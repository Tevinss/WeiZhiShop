package tevins.com.weizhishop.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import tevins.com.weizhishop.Constant;
import tevins.com.weizhishop.R;
import tevins.com.weizhishop.model.bean.WaresInfo;
import tevins.com.weizhishop.model.bean.PageInfo;
import tevins.com.weizhishop.presenter.ApiCallback;
import tevins.com.weizhishop.ui.adapter.HotWaresAdapter;
import tevins.com.weizhishop.ui.adapter.decoration.DividerItemDecoration;
import tevins.com.weizhishop.utils.LogUtils;
import tevins.com.weizhishop.utils.Pager;
import tevins.com.weizhishop.utils.ToastUtils;
import tevins.com.weizhishop.utils.http.OkHttpHelper;

/**
 * Created by yewyw on 2017/11/29/0029.
 */

public class HotFragment extends Fragment implements Pager.OnPageListener {

    private View view;
    private RecyclerView mRecyclerviewHotWares;
    private MaterialRefreshLayout mRefreshlayoutHotWares;
    private OkHttpHelper mOkHttpHelper;
    private HotWaresAdapter mHotWaresAdapter;
    private int currPage = 1;
    private int totalPage = 1;
    private int pageSize = 10;

    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFREH = 1;
    private static final int STATE_MORE = 2;
    private int state = STATE_NORMAL;
    private List<WaresInfo> mDataList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        mOkHttpHelper = OkHttpHelper.getInstance();
        initView(view);
//        getData();
        Pager pager = Pager.newBuilder().setUrl(Constant.API.WARES_HOT)
                .setLoadMore(true)
                .setOnPageListener(this)
                .setPageSize(20)
                .setRefreshLayout(mRefreshlayoutHotWares)
                .build(this.getActivity().getApplicationContext(),
                        new TypeToken<PageInfo<WaresInfo>>() {
                        }.getType());

        pager.getData();
        return view;
    }

    @Override
    public void load(List dataList, int totalPage, int totalCount) {
        mHotWaresAdapter = new HotWaresAdapter(this.getContext(), dataList, R.layout.item_hot_wares);
        mRecyclerviewHotWares.setAdapter(mHotWaresAdapter);
        mRecyclerviewHotWares.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerviewHotWares.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void refresh(List dataList, int totalPage, int totalCount) {
        mHotWaresAdapter.clearData();
        mHotWaresAdapter.addData(dataList);
    }

    @Override
    public void loadMore(List dataList, int totalPage, int totalCount) {
        mHotWaresAdapter.addData(mHotWaresAdapter.getDataList().size(), dataList);
    }


    /**
     * 请求数据
     */
    private void getData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("curPage", currPage + "");
        params.put("pageSize", pageSize + "");
        mOkHttpHelper.post(Constant.API.WARES_HOT, params, new ApiCallback<PageInfo<WaresInfo>>() {

            @Override
            public void onSuccess(Response response, PageInfo<WaresInfo> hotWaresInfoPageInfo) {
                if (hotWaresInfoPageInfo != null) {
                    mDataList = (List<WaresInfo>) hotWaresInfoPageInfo.getList();
                    currPage = hotWaresInfoPageInfo.getCurrentPage();
                    totalPage = hotWaresInfoPageInfo.getTotalPage();
                    showData();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                LogUtils.e("HotFragment", "onError: " + e);
                ToastUtils.show(getContext(), R.string.error, Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call call, Exception e) {
                ToastUtils.show(getContext(), R.string.failure, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 拿到数据后进行展示
     */
    private void showData() {
        switch (state) {
            case STATE_NORMAL://正常状态
                mHotWaresAdapter = new HotWaresAdapter(this.getContext(), mDataList, R.layout.item_hot_wares);
                mRecyclerviewHotWares.setAdapter(mHotWaresAdapter);
                mRecyclerviewHotWares.setLayoutManager(new LinearLayoutManager(this.getContext()));
                mRecyclerviewHotWares.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
                break;
            case STATE_REFREH://刷新
                mHotWaresAdapter.clearData();
                mHotWaresAdapter.addData(mDataList);
                mRefreshlayoutHotWares.finishRefresh();
                break;
            case STATE_MORE://加载更多
                mHotWaresAdapter.addData(mHotWaresAdapter.getDataList().size(), mDataList);
                mRefreshlayoutHotWares.finishRefreshLoadMore();
                break;
        }
    }

    private void initView(View view) {
        mRecyclerviewHotWares = (RecyclerView) view.findViewById(R.id.recyclerview_hot_wares);
        mRefreshlayoutHotWares = (MaterialRefreshLayout) view.findViewById(R.id.refreshlayout_hot_wares);
//        mRefreshlayoutHotWares.setMaterialRefreshListener(new MaterialRefreshListener() {
//            @Override
//            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
//                refreshData();
//            }
//
//            @Override
//            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
////                super.onRefreshLoadMore(materialRefreshLayout);
//                if (currPage <= totalPage) {
//                    loadMoreData();
//                } else {
//                    ToastUtils.show(getContext(), R.string.nothing_more, Toast.LENGTH_SHORT);
//                    mRefreshlayoutHotWares.finishRefreshLoadMore();
//                }
//            }
//        });
    }

    /**
     * 刷新，请求第一页的数据
     */
    private void refreshData() {
        currPage = 1;
        state = STATE_REFREH;
        getData();
    }

    /**
     * 加载更多的数据
     */
    private void loadMoreData() {
        currPage++;
        state = STATE_MORE;
        getData();
    }


}
