package tevins.com.weizhishop.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Transformers.RotateUpTransformer;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.List;

import tevins.com.weizhishop.R;
import tevins.com.weizhishop.model.bean.BannerInfo;
import tevins.com.weizhishop.model.bean.HomeCategoryInfo;
import tevins.com.weizhishop.presenter.impl.HomeBannerPresenter;
import tevins.com.weizhishop.presenter.impl.HomeCategoryPresenter;
import tevins.com.weizhishop.ui.adapter.HomeCategoryAdapter;
import tevins.com.weizhishop.ui.view.BaseView;
import tevins.com.weizhishop.ui.widget.HomeCatedoryItemDecoration;
import tevins.com.weizhishop.utils.LogUtils;

/**
 * Created by yewyw on 2017/11/29/0029.
 */

public class HomeFrament extends Fragment implements BaseView {

    private RecyclerView mRecyclerviewHome;
    private HomeCategoryAdapter mHomeCategoryAdapter;
    private HomeBannerPresenter mHomeBannerPresenter;
    private View view;
    private SliderLayout mSlider;
    private SliderLayout mSlider1;
    private PagerIndicator mCustomIndicator;
    private HomeCategoryPresenter mHomeCategoryPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        mSlider = (SliderLayout) view.findViewById(R.id.slider);
        mCustomIndicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);
        mRecyclerviewHome = (RecyclerView) view.findViewById(R.id.recyclerview_home);

        mHomeCategoryAdapter = new HomeCategoryAdapter(getActivity());
        mRecyclerviewHome.setAdapter(mHomeCategoryAdapter);//设置adapter
        mRecyclerviewHome.setLayoutManager(new LinearLayoutManager(this.getContext()));//设置布局管理器
        mRecyclerviewHome.addItemDecoration(new HomeCatedoryItemDecoration());//添加分割线
    }

    private void initData() {
        mHomeBannerPresenter = new HomeBannerPresenter(this);
        mHomeCategoryPresenter = new HomeCategoryPresenter(this);
        mHomeBannerPresenter.getData();
        mHomeCategoryPresenter.getData();
    }


    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e("HomeFrament", "onResume: " + "我是resume");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            LogUtils.e("HomeFrament", "onHiddenChanged: " + "HomeFrament显示了");
        }
    }

    /**
     * 设置轮播图数据
     *
     * @param object
     */
    @Override
    public void setHomeBannerInfo(Object object) {
        List<BannerInfo> bannerInfoList = (List<BannerInfo>) object;
        initSlider(bannerInfoList);
    }

    /**
     * 初始化轮播图
     *
     * @param bannerInfoList
     */
    private void initSlider(List<BannerInfo> bannerInfoList) {
        TextSliderView textSliderView;
        mSlider.removeAllSliders();
        for (BannerInfo bannerinfo : bannerInfoList) {
            textSliderView = new TextSliderView(this.getActivity());
            textSliderView
                    .description(bannerinfo.getName())
                    .image(bannerinfo.getImgUrl());
            mSlider.addSlider(textSliderView);
            textSliderView = null;
        }

//        for (int i = 0; i < size; i++) {
//            textSliderView = new TextSliderView(this.getContext());
//            textSliderView
//                    .description(bannerInfoList.get(i).getName())
//                    .image(bannerInfoList.get(i).getImgUrl());
//            mSlider.addSlider(textSliderView);
//        }
       /* DefaultSliderView defaultSliderView = new DefaultSliderView(this.getContext());
        defaultSliderView
                .description("全面屏手机换屏价格大调查")
                .image("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=162990221,3197590266&fm=173&s=28C28E4E5E432F55C2B960990300C092&w=640&h=354&img.JPEG");*/

        //设置指示器
        mSlider.setCustomIndicator(mCustomIndicator);
        mSlider.setPagerTransformer(false, new RotateUpTransformer());
        mSlider.setDuration(3000);
        mSlider.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //设置默认第一个被选中
        mSlider.setCurrentPosition(0);
    }

    /**
     * 设置首页分类数据
     *
     * @param object
     */
    @Override
    public void setHomeCategoryInfo(Object object) {
        List<HomeCategoryInfo> homeCategoryInfoList = (List<HomeCategoryInfo>) object;
        initRecyclerView(homeCategoryInfoList);
    }

    private void initRecyclerView(List<HomeCategoryInfo> homeCategoryInfoList) {
        mHomeCategoryAdapter.setData(homeCategoryInfoList);
    }

    @Override
    public void onStop() {
        mSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {

    }


}
