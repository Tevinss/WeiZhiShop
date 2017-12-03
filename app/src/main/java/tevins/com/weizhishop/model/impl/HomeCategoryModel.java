package tevins.com.weizhishop.model.impl;

import android.os.Looper;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import tevins.com.weizhishop.model.BaseModel;
import tevins.com.weizhishop.model.bean.HomeCategoryInfo;
import tevins.com.weizhishop.presenter.callback.HomeCategoryCallback;
import tevins.com.weizhishop.Constant;
import tevins.com.weizhishop.utils.http.OkHttpHelper;

/**
 * Created by tevins on 2017/11/30/0030.
 */

public class HomeCategoryModel implements BaseModel {

    /**
     * 请求首页分类数据
     *
     * @param callback
     */
    public void doGetData(final HomeCategoryCallback callback) {
        OkHttpHelper.getInstance().get(Constant.API.CAMPAIN_HOME, null, new HomeCategoryCallback<List<HomeCategoryInfo>>() {
            @Override
            public void onSuccess(Response response, List<HomeCategoryInfo> homeCategoryInfoList) {
                if (homeCategoryInfoList != null) {
                    callback.onSuccess(response, homeCategoryInfoList);
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

    /**
     * 判断是否是主线程
     *
     * @return
     */
    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
