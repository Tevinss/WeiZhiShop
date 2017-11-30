package tevins.com.weizhishop.presenter.impl;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import tevins.com.weizhishop.model.bean.BannerInfo;
import tevins.com.weizhishop.model.impl.HomeBannerModel;
import tevins.com.weizhishop.presenter.BasePresenter;
import tevins.com.weizhishop.presenter.callback.HomeBannerCallback;
import tevins.com.weizhishop.ui.view.BaseView;

/**
 * Created by tevins on 2017/11/30/0030.
 */

public class HomeBannerPresenter extends HomeBannerCallback<List<BannerInfo>> implements BasePresenter {
    /*Presenter作为中间层，持有View和Model的引用*/
    private HomeBannerModel mHomeBannerModel;
    private BaseView mHomeFrament;

    public HomeBannerPresenter(BaseView homeFrament) {
        mHomeFrament = homeFrament;
        mHomeBannerModel = new HomeBannerModel();
    }

    @Override
    public void getData() {
        mHomeBannerModel.doGetData(this);
    }


    @Override
    public void onSuccess(Response response, List<BannerInfo> bannerInfos) {
        mHomeFrament.setHomeBannerInfo(bannerInfos);
    }

    @Override
    public void onError(Response response, int code, Exception e) {
        mHomeFrament.showError();
    }

    @Override
    public void onFailure(Call call, Exception e) {

    }
}
