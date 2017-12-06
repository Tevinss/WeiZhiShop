package tevins.com.weizhishop.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import tevins.com.weizhishop.R;
import tevins.com.weizhishop.model.bean.ShoppingCartInfo;
import tevins.com.weizhishop.ui.viewholder.BaseViewHolder;
import tevins.com.weizhishop.ui.widget.NumberAddSubView;
import tevins.com.weizhishop.utils.CartProvider;

/**
 * Created by tevins on 2017/12/4/0004.
 */

public class ShoppingCartAdapter extends SimpleAdapter<ShoppingCartInfo> {

    private final CartProvider mCartProvider;
    private TextView mTvShoppingCartTotal;
    private CheckBox mCbShoppingCartAll;

    public ShoppingCartAdapter(Context context, List dataList, int layoutId, TextView tvShoppingCartTotal, CheckBox cbShoppingCartAll) {
        super(context, dataList, layoutId);
        mCartProvider = new CartProvider(context);
        mTvShoppingCartTotal = tvShoppingCartTotal;
        mCbShoppingCartAll = cbShoppingCartAll;
        showTotalPrice();
    }

    @Override
    public void updateView(BaseViewHolder holder, final ShoppingCartInfo shoppingCartInfo) {
        holder.getTextView(R.id.tv_shopping_cart_item_title).setText(shoppingCartInfo.getName());
        StringBuffer sb = new StringBuffer();
        holder.getTextView(R.id.tv_shopping_cart_item_price).setText(sb.append(shoppingCartInfo.getPrice()).toString());
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.drawee_view_shopping_cart_item);
        simpleDraweeView.setImageURI(Uri.parse(shoppingCartInfo.getImgUrl()));
        CheckBox checkBox = (CheckBox) holder.getView(R.id.cb_shopping_cart_item_select);
        checkBox.setChecked(shoppingCartInfo.isChecked());
        //设置数量值
        NumberAddSubView numberAddSubView = (NumberAddSubView) holder.getView(R.id.num_shopping_cart_item_control);
        numberAddSubView.setValue(shoppingCartInfo.getCount());
        numberAddSubView.setOnButtonClickListener(new NumberAddSubView.OnButtonClickListener() {
            @Override
            public void onButtonAddClick(View view, int value) {
                shoppingCartInfo.setCount(value);//更新数据
                mCartProvider.update(shoppingCartInfo);//存储到内存和本地
                showTotalPrice();
            }

            @Override
            public void onButtonSubClick(View view, int value) {
                shoppingCartInfo.setCount(value);//更新数据
                mCartProvider.update(shoppingCartInfo);//存储到内存和本地
                showTotalPrice();
            }
        });
    }

    /**
     * 展示商品的总价格
     */
    public void showTotalPrice() {
        float totalPrice = getTotalPrice();
        mTvShoppingCartTotal.setText(Html.fromHtml("合计￥<span style='color:#eb4f38'>" + totalPrice + "</span>"), TextView.BufferType.SPANNABLE);
    }

    /**
     * 获取商品的总价格
     */
    private float getTotalPrice() {
        float sumPrice = 0;
        if (isNotNull()) {
            for (ShoppingCartInfo shoppingCartInfo : mDataList) {
                if (shoppingCartInfo.isChecked()) {
                    sumPrice += shoppingCartInfo.getCount() * shoppingCartInfo.getPrice();
                }
            }
        }
        return sumPrice;
    }

    /**
     * 判断集合是否是空
     *
     * @return
     */
    private boolean isNotNull() {
        return (mDataList != null && mDataList.size() > 0);
    }

}
