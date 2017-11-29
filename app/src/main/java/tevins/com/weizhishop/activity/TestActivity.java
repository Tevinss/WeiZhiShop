package tevins.com.weizhishop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import tevins.com.weizhishop.R;

/**
 * Created by yewyw on 2017/11/29/0029.
 */

public class TestActivity extends BaseActivity {

    /**
     * 请输入搜索内容
     */
    private EditText mEtToolbarSerachview;
    private TextView mTvToolbarTitle;
    private ImageButton mIbToolbarRightButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_toolbar);
        initView();

    }

    private void initView() {
        mEtToolbarSerachview = (EditText) findViewById(R.id.et_toolbar_serachview);
        mTvToolbarTitle = (TextView) findViewById(R.id.tv_toolbar_title);
        mIbToolbarRightButton = (ImageButton) findViewById(R.id.ib_toolbar_right_button);
    }
}
