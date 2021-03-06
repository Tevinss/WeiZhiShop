package tevins.com.weizhishop.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tevins.com.weizhishop.R;
import tevins.com.weizhishop.model.bean.Tab;
import tevins.com.weizhishop.ui.fragment.CartFragment;
import tevins.com.weizhishop.ui.fragment.CategoryFragment;
import tevins.com.weizhishop.ui.fragment.HomeFrament;
import tevins.com.weizhishop.ui.fragment.HotFragment;
import tevins.com.weizhishop.ui.fragment.MineFragment;
import tevins.com.weizhishop.ui.widget.FragmentTabHost;
import tevins.com.weizhishop.ui.widget.MyToolBar;
import tevins.com.weizhishop.utils.LogUtils;
import tevins.com.weizhishop.utils.WebViewHelper;
import tevins.com.weizhishop.utils.http.OkHttpHelper;

public class MainActivity extends BaseActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 101;
    private tevins.com.weizhishop.ui.widget.FragmentTabHost mFragmentTabHost;
    private ArrayList<Tab> mTabs = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private MyToolBar mMyToolbar;
    private FrameLayout mTabcontent;
    private CartFragment mCartFragment;
    private WebView mWebView;
    private OkHttpHelper mOkHttpHelper;
    private WebSettings mSettings;
    private FrameLayout mRealTabContent;
    private WebViewHelper mWebViewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOkHttpHelper = new OkHttpHelper();
        initView();
        requestPermissions();
    }


    private void hideNative() {
        mWebView.setVisibility(View.VISIBLE);
        mMyToolbar.setVisibility(View.GONE);
        mFragmentTabHost.setVisibility(View.GONE);
        mTabcontent.setVisibility(View.GONE);
        mRealTabContent.setVisibility(View.GONE);
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
//        initWebView();
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.web_view);
        mWebViewHelper = new WebViewHelper(mWebView);
        boolean shouldShowWebView = mWebViewHelper.shouldShowWebView();
        if (shouldShowWebView) {
            hideNative();
        } else {
            mWebView.setVisibility(View.GONE);
        }
    }

    private void initTabHost() {
        mLayoutInflater = LayoutInflater.from(this);
        mFragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mRealTabContent = (FrameLayout) findViewById(R.id.realtabcontent);

        mFragmentTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        Tab homeTab = new Tab(HomeFrament.class, R.string.home, R.drawable.selector_icon_home);
        Tab categoryTab = new Tab(CategoryFragment.class, R.string.catagory, R.drawable.selector_icon_category);
        Tab cartTab = new Tab(CartFragment.class, R.string.cart, R.drawable.selector_icon_cart);
        Tab hotTab = new Tab(HotFragment.class, R.string.hot, R.drawable.selector_icon_hot);
        Tab mineTab = new Tab(MineFragment.class, R.string.mine, R.drawable.selector_icon_mine);

        mTabs.add(homeTab);
        mTabs.add(hotTab);
        mTabs.add(categoryTab);
        mTabs.add(cartTab);
        mTabs.add(mineTab);

        buildTabSpec(mTabs);
        mTabcontent = (FrameLayout) findViewById(android.R.id.tabcontent);

        mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals(getString(R.string.cart))) {
                    refreshData();
                }
            }
        });
    }

    /**
     * 刷新购物车数据
     */
    private void refreshData() {
        if (mCartFragment == null) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(getString(R.string.cart));
            if (fragment != null) {
                mCartFragment = (CartFragment) fragment;
                mCartFragment.getShoppingCartsData();
            }
        } else {
            mCartFragment.getShoppingCartsData();
        }

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


     private void requestPermissions() {
             // Here, thisActivity is the current activity
             if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                 // Should we show an explanation?
                 if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                         Manifest.permission.CALL_PHONE)) {

                     // Show an expanation to the user *asynchronously* -- don't block
                     // this thread waiting for the user's response! After the user
                     // sees the explanation, try again to request the permission.
                     LogUtils.e("MainActivity", "requestPermissions: " + "展示一个请求权限的解释");

                 } else {

                     // No explanation needed, we can request the permission.

                     ActivityCompat.requestPermissions(this,
                             new String[]{Manifest.permission.CALL_PHONE},
                             MY_PERMISSIONS_REQUEST_CALL_PHONE);

                     // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                     // app-defined int constant. The callback method gets the
                     // result of the request.
                 }
             }
         }
}
