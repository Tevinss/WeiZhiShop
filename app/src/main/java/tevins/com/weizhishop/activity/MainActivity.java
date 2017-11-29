package tevins.com.weizhishop.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tevins.com.weizhishop.R;
import tevins.com.weizhishop.bean.Tab;
import tevins.com.weizhishop.fragment.CartFragment;
import tevins.com.weizhishop.fragment.CategoryFragment;
import tevins.com.weizhishop.fragment.HomeFrament;
import tevins.com.weizhishop.fragment.HotFragment;
import tevins.com.weizhishop.fragment.MineFragment;
import tevins.com.weizhishop.widget.MyToolBar;

public class MainActivity extends BaseActivity {

    private FragmentTabHost mFragmentTabHost;
    private ArrayList<Tab> mTabs = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private MyToolBar mMyToolbar;
    private FrameLayout mRealtabcontent;
    private FrameLayout mTabcontent;
    private FragmentTabHost mTabhost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        initTabHost();
        mMyToolbar = (MyToolBar) findViewById(R.id.my_toolbar);
        mMyToolbar.setRightButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, mMyToolbar.getSeachContent(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initTabHost() {
        mLayoutInflater = LayoutInflater.from(this);
        mFragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mFragmentTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        Tab homeTab = new Tab(HomeFrament.class, R.string.home, R.drawable.selector_icon_home);
        Tab categoryTab = new Tab(CategoryFragment.class, R.string.catagory, R.drawable.selector_icon_category);
        Tab cartTab = new Tab(CartFragment.class, R.string.cart, R.drawable.selector_icon_cart);
        Tab hotTab = new Tab(HotFragment.class, R.string.hot, R.drawable.selector_icon_hot);
        Tab mineTab = new Tab(MineFragment.class, R.string.mine, R.drawable.selector_icon_mine);

        mTabs.add(homeTab);
        mTabs.add(categoryTab);
        mTabs.add(cartTab);
        mTabs.add(hotTab);
        mTabs.add(mineTab);

        buildTabSpec(mTabs);
        mRealtabcontent = (FrameLayout) findViewById(R.id.realtabcontent);
        mTabcontent = (FrameLayout) findViewById(android.R.id.tabcontent);
        mTabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
    }

    /**
     * 创建TabSpec，并添加到TabHost
     *
     * @param tabs
     */
    private void buildTabSpec(ArrayList<Tab> tabs) {
        for (Tab tab : tabs) {
            //给tabspec打标记
            TabHost.TabSpec tabSpec = mFragmentTabHost.newTabSpec(getString(tab.getTitle()));
            Log.e("MainActivity", "buildTabSpec: " + getString(tab.getTitle()));
            //给tabspec设置indicator
            View tabView = mLayoutInflater.inflate(R.layout.tab_indicator, null);
            ImageView iconTab = (ImageView) tabView.findViewById(R.id.icon_tab);
            TextView txtIndicator = (TextView) tabView.findViewById(R.id.txt_indicator);
            iconTab.setImageResource(tab.getIcon());
            txtIndicator.setText(tab.getTitle());
            tabSpec.setIndicator(tabView);
            //添加到TabHost
            mFragmentTabHost.addTab(tabSpec, tab.getFragment(), null);
        }
        //设置当前是哪一个tab
        mFragmentTabHost.setCurrentTab(0);
    }

}
