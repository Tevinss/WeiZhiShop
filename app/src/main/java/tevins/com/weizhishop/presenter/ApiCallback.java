package tevins.com.weizhishop.presenter;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by tevins on 2017/11/30/0030.
 */

public abstract class ApiCallback<T> implements BaseCallback<T> {
    public Type mType;

    public ApiCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    /**
     * 状态码大于200，小于300 时调用此方法
     *
     * @param response
     * @param t
     */
    @Override
    public abstract void onSuccess(Response response, T t);

    /**
     * 状态码400，404，403，500等时调用此方法
     *
     * @param response
     * @param code
     * @param e
     */
    @Override
    public abstract void onError(Response response, int code, Exception e);

    /**
     * 请求失败了，网络断开等等
     *
     * @param call
     * @param e
     */
    @Override
    public abstract void onFailure(Call call, Exception e);
}
