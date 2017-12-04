package tevins.com.weizhishop.ui.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import tevins.com.weizhishop.R;
import tevins.com.weizhishop.model.bean.WaresInfo;
import tevins.com.weizhishop.ui.viewholder.BaseViewHolder;

/**
 * Created by tevins on 2017/12/4/0004.
 */

public class CategoryWaresAdapter extends SimpleAdapter<WaresInfo> {

    public CategoryWaresAdapter(Context context, List dataList, int layoutId) {
        super(context, dataList, layoutId);
    }


    @Override
    public void updateView(BaseViewHolder holder, WaresInfo hotWaresInfo) {
        holder.getTextView(R.id.tv_category_wares_title).setText(hotWaresInfo.getName());
        StringBuffer sb = new StringBuffer();
        holder.getTextView(R.id.tv_category_wares__price).setText(sb.append(hotWaresInfo.getPrice()).toString());
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.drawee_view_category_wares);
        simpleDraweeView.setImageURI(Uri.parse(hotWaresInfo.getImgUrl()));
    }
}
