package tevins.com.weizhishop.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import tevins.com.weizhishop.Constant;
import tevins.com.weizhishop.R;
import tevins.com.weizhishop.model.bean.BannerInfo;
import tevins.com.weizhishop.model.bean.CategoryInfo;
import tevins.com.weizhishop.model.bean.WaresInfo;
import tevins.com.weizhishop.model.bean.PageInfo;
import tevins.com.weizhishop.presenter.ApiCallback;
import tevins.com.weizhishop.ui.adapter.BaseAdapter;
import tevins.com.weizhishop.ui.adapter.CategoryWaresAdapter;
import tevins.com.weizhishop.ui.adapter.CategoyrAdapter;
import tevins.com.weizhishop.ui.adapter.decoration.DividerItemDecoration;
import tevins.com.weizhishop.utils.Pager;
import tevins.com.weizhishop.utils.http.OkHttpHelper;

/**
 * Created by yewyw on 2017/11/29/0029.
 */

public class CategoryFragment extends Fragment implements Pager.OnPageListener {
    private View view;
    private RecyclerView mRecyclerviewCategory;
    private SliderLayout mSliderCategoryWares;
    private RecyclerView mRecyclerviewCategoryWares;
    private MaterialRefreshLayout mRefreshlayoutCategoryWares;
    private OkHttpHelper mOkHttpHelper;
    private CategoyrAdapter mCategoyrAdapter;
    private CategoryWaresAdapter mCategoryWaresAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initView(view);
        mOkHttpHelper = OkHttpHelper.getInstance();
        getCategoryData();
        getBannerData();
        return view;
    }

    /**
     * 请求banner的信息
     */
    private void getBannerData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", "1");
        mOkHttpHelper.get(Constant.API.HOME_BANNER, params, new ApiCallback<List<BannerInfo>>() {

            @Override
            public void onSuccess(Response response, List<BannerInfo> bannerInfos) {
                if (bannerInfos != null && bannerInfos.size() > 0) {
                    showSliderData(bannerInfos);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }

            @Override
            public void onFailure(Call call, Exception e) {

            }
        });
    }


    /**
     * 请求分类信息
     */
    private void getCategoryData() {
        mOkHttpHelper.get(Constant.API.CATEGORY_LIST, null, new ApiCallback<List<CategoryInfo>>() {

            @Override
            public void onSuccess(Response response, List<CategoryInfo> categoryInfos) {
                if (categoryInfos != null && categoryInfos.size() > 0) {
                    showCategoryData(categoryInfos);//显示分类信息
                    getCategoryWaresData(categoryInfos.get(0).getId());
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }

            @Override
            public void onFailure(Call call, Exception e) {

            }
        });

    }

    /**
     * 请求分类的商品信息
     *
     * @param categoryId
     */
    private void getCategoryWaresData(int categoryId) {
        HashMap<String, String> params = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        sb.append(categoryId);
        params.put("categoryId", sb.toString());
        Pager pager = Pager.newBuilder().setUrl(Constant.API.WARES_LIST)
                .setLoadMore(true)
                .setRefreshLayout(mRefreshlayoutCategoryWares)
                .setOnPageListener(this)
                .setParams(params)
                .build(this.getActivity().getApplicationContext(), new TypeToken<PageInfo<WaresInfo>>() {
                }.getType());
        pager.getData();
    }


    private void initView(View view) {
        mRecyclerviewCategory = (RecyclerView) view.findViewById(R.id.recyclerview_category);
        mSliderCategoryWares = (SliderLayout) view.findViewById(R.id.slider_category_wares);
        mRecyclerviewCategoryWares = (RecyclerView) view.findViewById(R.id.recyclerview_category_wares);
        mRefreshlayoutCategoryWares = (MaterialRefreshLayout) view.findViewById(R.id.refreshlayout_category_wares);
    }

    /**
     * 展示左侧分类数据
     *
     * @param categoryInfos
     */
    private void showCategoryData(final List<CategoryInfo> categoryInfos) {
        mCategoyrAdapter = new CategoyrAdapter(getContext(), categoryInfos, R.layout.item_category);
        mRecyclerviewCategory.setAdapter(mCategoyrAdapter);
        mRecyclerviewCategory.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerviewCategory.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        //分类的点击事件
        mCategoyrAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position < categoryInfos.size()) {
                    getCategoryWaresData(categoryInfos.get(position).getId());
                }
            }
        });
    }

    /**
     * 展示banner信息
     *
     * @param bannerInfos
     */
    private void showSliderData(List<BannerInfo> bannerInfos) {
        TextSliderView textSliderView;
        for (BannerInfo bannerInfo : bannerInfos) {
            textSliderView = new TextSliderView(getContext());
            textSliderView
                    .description(bannerInfo.getName())
                    .image(bannerInfo.getImgUrl());
            mSliderCategoryWares.addSlider(textSliderView);
            textSliderView = null;
        }
    }


    @Override
    public void onStop() {
        mSliderCategoryWares.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void load(List dataList, int totalPage, int totalCount) {
        mCategoryWaresAdapter = new CategoryWaresAdapter(this.getContext(), dataList, R.layout.item_category_wares);
        mRecyclerviewCategoryWares.setAdapter(mCategoryWaresAdapter);
        mRecyclerviewCategoryWares.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        mRecyclerviewCategoryWares.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void refresh(List dataList, int totalPage, int totalCount) {
        mCategoryWaresAdapter.clearData();
        mCategoryWaresAdapter.addData(dataList);
    }

    @Override
    public void loadMore(List dataList, int totalPage, int totalCount) {
        mCategoryWaresAdapter.addData(mCategoryWaresAdapter.getDataList().size(), dataList);
    }
}
