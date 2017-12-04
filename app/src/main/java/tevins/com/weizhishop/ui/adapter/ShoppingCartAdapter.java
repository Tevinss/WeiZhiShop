package tevins.com.weizhishop.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.widget.CheckBox;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import tevins.com.weizhishop.R;
import tevins.com.weizhishop.model.bean.ShoppingCartInfo;
import tevins.com.weizhishop.ui.viewholder.BaseViewHolder;
import tevins.com.weizhishop.ui.widget.NumberAddSubView;

/**
 * Created by tevins on 2017/12/4/0004.
 */

public class ShoppingCartAdapter extends SimpleAdapter<ShoppingCartInfo> {

    public ShoppingCartAdapter(Context context, List dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    public void updateView(BaseViewHolder holder, ShoppingCartInfo shoppingCartInfo) {
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

    }
}
