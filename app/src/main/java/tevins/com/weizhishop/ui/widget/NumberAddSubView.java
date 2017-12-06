package tevins.com.weizhishop.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import tevins.com.weizhishop.R;
import tevins.com.weizhishop.utils.ToastUtils;

/**
 * Created by tevins on 2017/12/4/0004.
 */

public class NumberAddSubView extends LinearLayout implements View.OnClickListener {
    private LayoutInflater mLayoutInflater;
    /**
     * -
     */
    private Button mBtnSub;
    private TextView mTvNum;
    /**
     * +
     */
    private Button mBtnAdd;
    /**
     * 当前值
     */
    private int mValue;
    /**
     * 最小值
     */
    private int mMinValue;
    /**
     * 最大值
     */
    private int mMaxValue;
    private OnButtonClickListener mOnButtonClickListener;

    public NumberAddSubView(Context context) {
        this(context, null);
    }

    public NumberAddSubView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberAddSubView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLayoutInflater = LayoutInflater.from(context);
        initView();
        if (attrs != null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.NumberAddSubView, defStyleAttr, 0);
            int value = a.getInt(R.styleable.NumberAddSubView_value, 0);
            setValue(value);
            int minValue = a.getInt(R.styleable.NumberAddSubView_minValue, 0);
            setMinValue(minValue);
            int maxValue = a.getInt(R.styleable.NumberAddSubView_maxValue, 0);
            setMaxValue(maxValue);
            Drawable textBackground = a.getDrawable(R.styleable.NumberAddSubView_textBackground);
            if (textBackground != null) {
                setTextBackground(textBackground);
            }
            Drawable buttonAddBackground = a.getDrawable(R.styleable.NumberAddSubView_buttonAddBackground);
            if (buttonAddBackground != null)
                setButtonAddBackgroud(buttonAddBackground);

            Drawable buttonSubBackground = a.getDrawable(R.styleable.NumberAddSubView_buttonSubBackground);
            if (buttonSubBackground != null)
                setButtonSubBackgroud(buttonSubBackground);

            a.recycle();
        }
    }

    private void initView() {
        View view = mLayoutInflater.inflate(R.layout.widet_num_add_sub, this, false);
        mBtnSub = (Button) view.findViewById(R.id.btn_sub);
        mBtnSub.setOnClickListener(this);
        mTvNum = (TextView) view.findViewById(R.id.tv_num);
        mBtnAdd = (Button) view.findViewById(R.id.btn_add);
        mBtnAdd.setOnClickListener(this);

        this.addView(view);
    }

    /**
     * 设置减号的背景图
     *
     * @param buttonSubBackground
     */
    public void setButtonSubBackgroud(Drawable buttonSubBackground) {
        mBtnSub.setBackgroundDrawable(buttonSubBackground);
    }

    /**
     * 设置加号的背景图
     *
     * @param buttonAddBackground
     */
    public void setButtonAddBackgroud(Drawable buttonAddBackground) {
        mBtnAdd.setBackgroundDrawable(buttonAddBackground);
    }


    /**
     * 设置textview背景图
     *
     * @param textBackground
     */
    public void setTextBackground(Drawable textBackground) {
        mTvNum.setBackgroundDrawable(textBackground);
    }

    /**
     * 设置最大值
     *
     * @param maxValue
     */
    public void setMaxValue(int maxValue) {
        mMaxValue = maxValue;
    }

    /**
     * 设置最小值
     *
     * @param minValue
     */
    public void setMinValue(int minValue) {
        mMinValue = minValue;
    }

    /**
     * 设置数字值
     *
     * @param value
     */
    public void setValue(int value) {
        mTvNum.setText(value + "");
        mValue = value;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sub://点击了减号
                numSub();
                if (mOnButtonClickListener != null) {
                    mOnButtonClickListener.onButtonSubClick(v, this.mValue);
                }
                break;
            case R.id.btn_add://点击了加号
                numAdd();
                if (mOnButtonClickListener != null) {
                    mOnButtonClickListener.onButtonAddClick(v, this.mValue);
                }
                break;
        }
    }

    /**
     * 点击-1
     */
    private void numSub() {
        int currentValue = getCurrentValue();
        if (currentValue > mMinValue) {
            this.mValue--;
        } else {
            ToastUtils.show(this.getContext(), R.id.no_smaller);
        }
        setValue(this.mValue);
    }

    /**
     * 点击+1
     */
    private void numAdd() {
        int currentValue = getCurrentValue();
        if (currentValue < mMaxValue) {
            this.mValue++;
        } else {
            ToastUtils.show(this.getContext(), R.id.no_bigger);
        }
        setValue(this.mValue);
    }

    /**
     * 获取当前的value值
     */
    private int getCurrentValue() {
        String value = mTvNum.getText().toString();
        if (value != null && "".equals(value)) {
            this.mValue = Integer.parseInt(value);
        }
        return this.mValue;
    }

    public interface OnButtonClickListener {
        /**
         * 点击了加号
         *
         * @param view
         * @param value
         */
        void onButtonAddClick(View view, int value);

        /**
         * 点击了减号
         *
         * @param view
         * @param value
         */
        void onButtonSubClick(View view, int value);
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {

        mOnButtonClickListener = onButtonClickListener;
    }
}
