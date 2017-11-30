package tevins.com.weizhishop.presenter;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tevins on 2017/11/30/0030.
 */

public interface BaseCallback<T> {


    /**
     * 状态码大于200，小于300 时调用此方法
     *
     * @param response
     * @param t
     */
    void onSuccess(Response response, T t);

    /**
     * 状态码400，404，403，500等时调用此方法
     *
     * @param response
     * @param code
     * @param e
     */
    void onError(Response response, int code, Exception e);

    /**
     * 请求失败了，网络断开等等
     *
     * @param call
     * @param e
     */
    void onFailure(Call call, Exception e);

}
