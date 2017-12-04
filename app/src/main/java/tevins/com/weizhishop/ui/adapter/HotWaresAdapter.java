package tevins.com.weizhishop.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import tevins.com.weizhishop.R;
import tevins.com.weizhishop.model.bean.WaresInfo;
import tevins.com.weizhishop.ui.viewholder.BaseViewHolder;
import tevins.com.weizhishop.utils.CartProvider;
import tevins.com.weizhishop.utils.ToastUtils;

/**
 * Created by tevins on 2017/12/3 003.
 */

public class HotWaresAdapter extends SimpleAdapter<WaresInfo> {
    private final CartProvider mCartProvider;

    public HotWaresAdapter(Context context, List dataList, int layoutId) {
        super(context, dataList, layoutId);
        mCartProvider = new CartProvider(context);
    }

    @Override
    public void updateView(BaseViewHolder holder, final WaresInfo waresInfo) {
        holder.getTextView(R.id.text_title).setText(waresInfo.getName());
        holder.getTextView(R.id.text_price).setText(waresInfo.getPrice() + "");
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.drawee_view);
        simpleDraweeView.setImageURI(Uri.parse(waresInfo.getImgUrl()));
        Button btnAddToCart = holder.getButton(R.id.btn_add_to_cart);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCartProvider.put(waresInfo);
                ToastUtils.show(mContext, "已添加到购物车");
            }
        });
    }
}
