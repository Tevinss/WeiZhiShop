package tevins.com.weizhishop.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import tevins.com.weizhishop.R;
import tevins.com.weizhishop.model.bean.ShoppingCartInfo;
import tevins.com.weizhishop.ui.adapter.ShoppingCartAdapter;
import tevins.com.weizhishop.ui.adapter.decoration.DividerItemDecoration;
import tevins.com.weizhishop.utils.CartProvider;

/**
 * Created by tevins on 2017/11/29/0029.
 */

public class CartFragment extends Fragment implements View.OnClickListener {
    private View view;
    private RecyclerView mRecyclerviewShoppingCart;
    /**
     * 全选
     */
    private CheckBox mCbShoppingCartAll;
    /**
     * 合计
     */
    private TextView mTvShoppingCartTotal;
    /**
     * 去结算
     */
    private Button mBtnShoppingCartOrder;
    /**
     * 删除
     */
    private Button mBtnShoppingCartDel;
    private ShoppingCartAdapter mShoppingCartAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        initView(view);
        getShoppingCartsData();
        return view;
    }

    /**
     * 获取购物车数据
     */
    private void getShoppingCartsData() {
        CartProvider cartProvider = new CartProvider(this.getActivity().getApplicationContext());
        List<ShoppingCartInfo> shoppingCartInfoList = cartProvider.getAll();
        if (shoppingCartInfoList != null) {
            showData(shoppingCartInfoList);
        }
    }

    /**
     * 展示在view上
     *
     * @param shoppingCartInfoList 购物车数据
     */
    private void showData(List<ShoppingCartInfo> shoppingCartInfoList) {
        Log.e("CartFragment", "showData: " + shoppingCartInfoList.size());
        mShoppingCartAdapter = new ShoppingCartAdapter(this.getActivity().getApplicationContext(), shoppingCartInfoList, R.layout.item_shopping_cart);
        mRecyclerviewShoppingCart.setAdapter(mShoppingCartAdapter);
        mRecyclerviewShoppingCart.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerviewShoppingCart.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
    }

    private void initView(View view) {
        mRecyclerviewShoppingCart = (RecyclerView) view.findViewById(R.id.recyclerview_shopping_cart);
        mCbShoppingCartAll = (CheckBox) view.findViewById(R.id.cb_shopping_cart_all);
        mCbShoppingCartAll.setOnClickListener(this);
        mTvShoppingCartTotal = (TextView) view.findViewById(R.id.tv_shopping_cart_total);
        mBtnShoppingCartOrder = (Button) view.findViewById(R.id.btn_shopping_cart_order);
        mBtnShoppingCartOrder.setOnClickListener(this);
        mBtnShoppingCartDel = (Button) view.findViewById(R.id.btn_shopping_cart_del);
        mBtnShoppingCartDel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_shopping_cart_all:
                break;
            case R.id.btn_shopping_cart_order:
                break;
            case R.id.btn_shopping_cart_del:
                break;
        }
    }
}
