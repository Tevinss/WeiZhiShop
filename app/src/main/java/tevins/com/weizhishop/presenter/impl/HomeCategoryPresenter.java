package tevins.com.weizhishop.presenter.impl;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import tevins.com.weizhishop.model.bean.HomeCategoryInfo;
import tevins.com.weizhishop.model.impl.HomeCategoryModel;
import tevins.com.weizhishop.presenter.BasePresenter;
import tevins.com.weizhishop.presenter.callback.HomeCategoryCallback;
import tevins.com.weizhishop.ui.view.BaseView;

/**
 * Created by tevins on 2017/11/30/0030.
 */

public class HomeCategoryPresenter extends HomeCategoryCallback<List<HomeCategoryInfo>> implements BasePresenter {
    /*Presenter作为中间层，持有View和Model的引用*/
    private HomeCategoryModel mHomeCategoryModel;
    private BaseView mHomeFrament;

    public HomeCategoryPresenter(BaseView homeFrament) {
        mHomeFrament = homeFrament;
        mHomeCategoryModel = new HomeCategoryModel();
    }

    @Override
    public void getData() {
        mHomeCategoryModel.doGetData(this);
    }


    @Override
    public void onSuccess(Response response, List<HomeCategoryInfo> homeCategoryInfoList) {
        mHomeFrament.setHomeCategoryInfo(homeCategoryInfoList);
    }

    @Override
    public void onError(Response response, int code, Exception e) {
        mHomeFrament.showError();
    }

    @Override
    public void onFailure(Call call, Exception e) {

    }
}
