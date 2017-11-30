package tevins.com.weizhishop.presenter.callback;

import okhttp3.Call;
import okhttp3.Response;
import tevins.com.weizhishop.presenter.ApiCallback;

/**
 * Created by tevins on 2017/11/30/0030.
 */

public abstract class HomeBannerCallback<T> extends ApiCallback<T> {

    @Override
    public abstract void onSuccess(Response response, T t);

    @Override
    public abstract void onError(Response response, int code, Exception e);

    @Override
    public abstract void onFailure(Call call, Exception e);
}
