package tevins.com.weizhishop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Transformers.RotateUpTransformer;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;

import tevins.com.weizhishop.R;
import tevins.com.weizhishop.adapter.HomeCategoryAdapter;
import tevins.com.weizhishop.bean.HomeCategory;
import tevins.com.weizhishop.utils.LogUtils;
import tevins.com.weizhishop.widget.HomeCatedoryItemDecoration;

/**
 * Created by yewyw on 2017/11/29/0029.
 */

public class HomeFrament extends Fragment {

    private SliderLayout mSliderShow;
    private RecyclerView mRecyclerviewHome;
    private HomeCategoryAdapter mHomeCategoryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initSlider(view);
        initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {
        mRecyclerviewHome = (RecyclerView) view.findViewById(R.id.recyclerview_home);
        mHomeCategoryAdapter = new HomeCategoryAdapter();
        mRecyclerviewHome.setAdapter(mHomeCategoryAdapter);//设置adapter
        mRecyclerviewHome.setLayoutManager(new LinearLayoutManager(this.getContext()));//设置布局管理器
        mRecyclerviewHome.addItemDecoration(new HomeCatedoryItemDecoration());//添加分割线

        initData();

    }

    private void initData() {
        ArrayList<HomeCategory> homeCategories = new ArrayList<>();
        HomeCategory homeCategory = new HomeCategory("热门活动", R.drawable.img_big_1, R.drawable.img_1_small1, R.drawable.img_0_small2);
        homeCategories.add(homeCategory);
        homeCategory = new HomeCategory("有利可图", R.drawable.img_big_4, R.drawable.img_4_small1, R.drawable.img_4_small2);
        homeCategories.add(homeCategory);
        homeCategory = new HomeCategory("品牌街", R.drawable.img_big_2, R.drawable.img_2_small1, R.drawable.img_2_small2);
        homeCategories.add(homeCategory);
        homeCategory = new HomeCategory("金融街 包赚翻", R.drawable.img_big_1, R.drawable.img_3_small1, R.drawable.imag_3_small2);
        homeCategories.add(homeCategory);
        homeCategory = new HomeCategory("超值购", R.drawable.img_big_0, R.drawable.img_0_small1, R.drawable.img_0_small2);
        homeCategories.add(homeCategory);
        mHomeCategoryAdapter.setData(homeCategories);//更新页面数据
    }

    /**
     * 初始化轮播图
     *
     * @param view
     */
    private void initSlider(View view) {
        mSliderShow = (SliderLayout) view.findViewById(R.id.slider);
        TextSliderView textSliderViewFir = new TextSliderView(this.getContext());
        TextSliderView textSliderViewSec = new TextSliderView(this.getContext());
        TextSliderView textSliderViewThird = new TextSliderView(this.getContext());

        textSliderViewFir
                .description("金秋满减")
                .image("http://m.360buyimg.com/mobilecms/s480x180_jfs/t2278/35/409524152/232719/1d29f7a9/56078dbfNae4f16a3.jpg");
        textSliderViewSec
                .description("家电让利")
                .image("http://m.360buyimg.com/mobilecms/s480x180_jfs/t2071/116/426908452/111248/3e6d388c/5608a437N723ee2ba.jpg");
        textSliderViewThird
                .description("秒杀")
                .image("http://m.360buyimg.com/mobilecms/s480x180_jfs/t2113/230/413819408/114393/d8a62616/56078bacN9c9c6dc8.jpg");
       /* DefaultSliderView defaultSliderView = new DefaultSliderView(this.getContext());
        defaultSliderView
                .description("全面屏手机换屏价格大调查")
                .image("https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=162990221,3197590266&fm=173&s=28C28E4E5E432F55C2B960990300C092&w=640&h=354&img.JPEG");*/
        mSliderShow.addSlider(textSliderViewFir);
        mSliderShow.addSlider(textSliderViewSec);
        mSliderShow.addSlider(textSliderViewThird);
        //设置指示器
        PagerIndicator pagerIndicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);
        mSliderShow.setCustomIndicator(pagerIndicator);
        mSliderShow.setPagerTransformer(false, new RotateUpTransformer());
        mSliderShow.setDuration(3000);
        mSliderShow.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                LogUtils.e("HomeFrament", "onPageScrolled: " + "滚动了");
            }

            @Override
            public void onPageSelected(int position) {
                LogUtils.e("HomeFrament", "onPageSelected: " + "选中了");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                LogUtils.e("HomeFrament", "onPageScrollStateChanged: " + "状态改变了");
            }
        });
        textSliderViewFir.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {

            }
        });
        textSliderViewFir.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(HomeFrament.this.getContext(), "点击了", Toast.LENGTH_SHORT).show();
            }
        });

        //设置默认第一个被选中
        mSliderShow.setCurrentPosition(0);
    }


    @Override
    public void onStop() {
        mSliderShow.stopAutoCycle();
        super.onStop();
    }
}
