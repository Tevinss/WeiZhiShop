package tevins.com.weizhishop.model.impl;

import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import tevins.com.weizhishop.model.BaseModel;
import tevins.com.weizhishop.model.bean.BannerInfo;
import tevins.com.weizhishop.presenter.callback.HomeBannerCallback;
import tevins.com.weizhishop.utils.Constant;
import tevins.com.weizhishop.utils.http.OkHttpHelper;

/**
 * Created by tevins on 2017/11/30/0030.
 */

public class HomeBannerModel implements BaseModel {

    /**
     * 请求首页banner数据
     *
     * @param callback
     */
    public void doGetData(final HomeBannerCallback callback) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "1");
        OkHttpHelper.getInstance().get(Constant.API.HOME_BANNER, hashMap, new HomeBannerCallback<List<BannerInfo>>() {
            @Override
            public void onSuccess(Response response, List<BannerInfo> bannerInfoList) {
                if (bannerInfoList != null) {
                    callback.onSuccess(response, bannerInfoList);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                callback.onError(response, response.code(), e);
            }

            @Override
            public void onFailure(Call call, Exception e) {
                callback.onFailure(call, e);
            }
        });

    }
}
