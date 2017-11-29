package tevins.com.weizhishop.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import tevins.com.weizhishop.R;

/**
 * Created by yewyw on 2017/11/29/0029.
 */

public class MyToolBar extends Toolbar {
    private LayoutInflater mLayoutInflater;
    private View mView;

    /**
     * 请输入搜索内容
     */
    private EditText mEtToolbarSerachview;
    private TextView mTvToolbarTitle;
    private ImageButton mIbToolbarRightButton;

    public MyToolBar(Context context) {
        this(context, null);
    }

    public MyToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        if (mView == null) {
            mLayoutInflater = LayoutInflater.from(getContext());
            mView = mLayoutInflater.inflate(R.layout.my_toolbar, null);
            //找到控件
            mEtToolbarSerachview = (EditText) mView.findViewById(R.id.et_toolbar_serachview);
            mTvToolbarTitle = (TextView) mView.findViewById(R.id.tv_toolbar_title);
            mIbToolbarRightButton = (ImageButton) mView.findViewById(R.id.ib_toolbar_right_button);
            //创建布局方式，添加到this上
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            this.addView(mView, layoutParams);
        }
    }
}
