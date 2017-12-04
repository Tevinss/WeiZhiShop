package tevins.com.weizhishop.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.TintTypedArray;
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
 * Created by tevins on 2017/11/29/0029.
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

        if (attrs != null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.MyToolBar, defStyleAttr, 0);
            //获取自定义属性
            boolean isShowSearchView = a.getBoolean(R.styleable.MyToolBar_isShowSearchView, false);
            Drawable rightButtonIcon = a.getDrawable(R.styleable.MyToolBar_rightButtonIcon);

            //设置toolbar右侧的图标
            if (rightButtonIcon != null) {
                setRightButtonIcon(rightButtonIcon);
            }
            //设置是否显示searchView
            if (isShowSearchView) {
                showSearchView();
                hideTitleView();
            }

            a.recycle();
        }

    }

    /**
     * 隐藏顶部标题
     */
    private void hideTitleView() {
        if (mTvToolbarTitle != null) {
            mTvToolbarTitle.setVisibility(GONE);
        }
    }

    /**
     * 显示顶部标题
     */
    private void showTitleView() {
        if (mTvToolbarTitle != null) {
            mTvToolbarTitle.setVisibility(VISIBLE);
        }
    }

    /**
     * 显示搜索框
     */
    private void showSearchView() {
        if (mEtToolbarSerachview != null) {
            mEtToolbarSerachview.setVisibility(VISIBLE);
        }
    }

    /**
     * 隐藏搜索框
     */
    private void hideSearchView() {
        if (mEtToolbarSerachview != null) {
            mEtToolbarSerachview.setVisibility(GONE);
        }
    }

    /**
     * toolbar右侧按钮的点击事件
     *
     * @param onClickListener
     */
    public void setRightButtonClickListener(OnClickListener onClickListener) {
        if (mIbToolbarRightButton != null) {
            mIbToolbarRightButton.setOnClickListener(onClickListener);
        }
    }


    /**
     * 设置toolbar右边的图标
     *
     * @param rightButtonIcon
     */
    private void setRightButtonIcon(Drawable rightButtonIcon) {
        if (mIbToolbarRightButton != null) {
            mIbToolbarRightButton.setImageDrawable(rightButtonIcon);
            mIbToolbarRightButton.setVisibility(VISIBLE);
        }
    }

    /**
     * 初始化view
     */
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

    /**
     * 根据app:title设置标题
     *
     * @param resId
     */
    @Override
    public void setTitle(@StringRes int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initView();
        if (mTvToolbarTitle != null) {
            mTvToolbarTitle.setText(title);
            showTitleView();
        }
    }

    public String getSeachContent() {
        if (mEtToolbarSerachview != null) {
            return mEtToolbarSerachview.getText().toString().trim();
        }
        return "";
    }
}
