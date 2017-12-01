package tevins.com.weizhishop.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

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

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     */
    private static class MyHandler extends Handler {
        private final WeakReference<TestActivity> mActivity;

        public MyHandler(TestActivity activity) {
            mActivity = new WeakReference<TestActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TestActivity activity = mActivity.get();
            if (activity != null) {
                // ...
            }
        }
    }

    private final MyHandler mHandler = new MyHandler(this);

    /**
     * Instances of anonymous classes do not hold an implicit
     * reference to their outer class when they are "static".
     */
    private static final Runnable sRunnable = new Runnable() {
        @Override
        public void run() { /* ... */ }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_home_cardviewfir);
        initView();

        // Post a message and delay its execution for 10 minutes.
        mHandler.postDelayed(sRunnable, 1000 * 60 * 10);

        // Go back to the previous Activity.
        finish();
    }


    private void initView() {
        mTvHomeItemTitle = (TextView) findViewById(R.id.tv_home_item_title);
        mIvHomeItemBig = (ImageView) findViewById(R.id.iv_home_item_big);
        mIvHomeItemSmallTop = (ImageView) findViewById(R.id.iv_home_item_small_top);
        mIvHomeItemSmallBottom = (ImageView) findViewById(R.id.iv_home_item_small_bottom);
    }
}
