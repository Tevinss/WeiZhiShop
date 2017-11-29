package tevins.com.weizhishop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import tevins.com.weizhishop.R;

/**
 * Created by yewyw on 2017/11/29/0029.
 */

public class TestActivity extends BaseActivity {


    /**
     * 热门活动
     */
    private TextView mTvHomeItemTitle;
    private ImageView mIvHomeItemBig;
    private ImageView mIvHomeItemSmallTop;
    private ImageView mIvHomeItemSmallBottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_home_cardviewfir);
        initView();

    }

    private void initView() {
        mTvHomeItemTitle = (TextView) findViewById(R.id.tv_home_item_title);
        mIvHomeItemBig = (ImageView) findViewById(R.id.iv_home_item_big);
        mIvHomeItemSmallTop = (ImageView) findViewById(R.id.iv_home_item_small_top);
        mIvHomeItemSmallBottom = (ImageView) findViewById(R.id.iv_home_item_small_bottom);
    }
}
