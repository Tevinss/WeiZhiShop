package tevins.com.weizhishop.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import tevins.com.weizhishop.R;

/**
 * Created by yewyw on 2017/11/29/0029.
 */

public class MineFragment extends Fragment implements View.OnClickListener {
    private View view;
    /**
     * -
     */
    private Button mBtnSub;
    private TextView mTvNum;
    /**
     * +
     */
    private Button mBtnAdd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        View view = inflater.inflate(R.layout.widet_num_add_sub, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mBtnSub = (Button) view.findViewById(R.id.btn_sub);
        mBtnSub.setOnClickListener(this);
        mTvNum = (TextView) view.findViewById(R.id.tv_num);
        mBtnAdd = (Button) view.findViewById(R.id.btn_add);
        mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sub:
                break;
            case R.id.btn_add:
                break;
        }
    }
}
