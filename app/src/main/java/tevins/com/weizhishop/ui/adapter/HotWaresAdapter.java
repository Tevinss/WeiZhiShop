package tevins.com.weizhishop.ui.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import tevins.com.weizhishop.R;
import tevins.com.weizhishop.model.bean.HotWaresInfo;
import tevins.com.weizhishop.ui.viewholder.BaseViewHolder;

/**
 * Created by tevins on 2017/12/3 003.
 */

public class HotWaresAdapter extends SimpleAdapter<HotWaresInfo> {
    public HotWaresAdapter(Context context, List dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    public void updateView(BaseViewHolder holder, HotWaresInfo hotWaresInfo) {
        holder.getTextView(R.id.text_title).setText(hotWaresInfo.getName());
        holder.getTextView(R.id.text_price).setText(hotWaresInfo.getPrice() + "");
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.drawee_view);
        simpleDraweeView.setImageURI(Uri.parse(hotWaresInfo.getImgUrl()));
    }
}
