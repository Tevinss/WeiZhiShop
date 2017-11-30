package tevins.com.weizhishop.utils.http;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tevins.com.weizhishop.presenter.ApiCallback;
import tevins.com.weizhishop.utils.LogUtils;

/**
 * Created by tevins on 2017/11/30/0030.
 */

public class OkHttpHelper {
    private static OkHttpHelper instance;
    private final Gson mGson;
    private final Handler mHandler;
    private OkHttpClient mOkHttpClient;


    public OkHttpHelper() {
        mHandler = new Handler(Looper.getMainLooper());
        mGson = new Gson();
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.newBuilder().connectTimeout(10, TimeUnit.SECONDS);//设置连接超时
        mOkHttpClient.newBuilder().readTimeout(10, TimeUnit.SECONDS);//设置读取超时
        mOkHttpClient.newBuilder().writeTimeout(30, TimeUnit.SECONDS);//设置写入超时时间
    }

    public static OkHttpHelper getInstance() {
        if (instance == null) {
            instance = new OkHttpHelper();
        }
        return instance;
    }


    /**
     * get请求
     *
     * @param url      请求地址
     * @param callback 回调
     */
    public void get(String url, Map<String, String> params, ApiCallback callback) {
        Request request = buildGetRequest(url, params);
        doRequest(request, callback);
    }

    /**
     * post请求
     *
     * @param url      请求地址
     * @param callback 回调
     * @param params   请求参数
     */
    public void post(String url, ApiCallback callback, Map<String, String> params) {
        Request request = buildPostRequest(url, params);
        doRequest(request, callback);
    }

    /**
     * 真正的请求服务器
     *
     * @param request  请求
     * @param callback 回调
     */
    private void doRequest(Request request, final ApiCallback callback) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e("OkHttpHelper", "onFailure: " + "网络有问题");
                callbackFailure(callback, call, e);
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {//请求成功

                    String resultStr = response.body().string();
                    LogUtils.e("OkHttpHelper", "onResponse: " + resultStr);
                    if (callback.mType == String.class) {
                        callbackSuccess(callback, response, resultStr);
                    } else {
                        try {
                            Object obj = mGson.fromJson(resultStr, callback.mType);
                            callbackSuccess(callback, response, obj);
                        } catch (JsonSyntaxException e) {//json解析错误
                            callbackError(callback, response, e);
                        }
                    }
                } else {//请求错误
                    callbackError(callback, response, null);
                    LogUtils.e("OkHttpHelper", "onResponse: " + "请求错误码:" + response.code());
                }
            }
        });
    }

    /**
     * 请求错误，包括请求错误和json解析失败
     *
     * @param callback
     * @param response
     * @param error
     */
    private void callbackError(final ApiCallback callback, final Response response, final Exception error) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response, response.code(), error);
            }
        });
    }

    /**
     * 请求成功，回调到主线程
     *
     * @param callback
     * @param response
     * @param obj
     */
    private void callbackSuccess(final ApiCallback callback, final Response response, final Object obj) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, obj);
            }
        });
    }

    /**
     * 请求失败，网络断开，回调到主线程
     *
     * @param callback
     * @param call
     * @param e
     */
    private void callbackFailure(final ApiCallback callback, final Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(call, e);
            }
        });
    }


    /**
     * 构建get方式的请求
     *
     * @param url
     * @param params
     * @return
     */
    private Request buildGetRequest(String url, Map<String, String> params) {
        Request request = buildRequest(url, HttpMethodType.GET, params);
        return request;
    }

    /**
     * 构建post方式的请求
     *
     * @param url
     * @return
     */
    private Request buildPostRequest(String url, Map<String, String> params) {
        Request request = buildRequest(url, HttpMethodType.POST, params);
        return request;
    }

    /**
     * 构建请求
     *
     * @param url        请求地址
     * @param methodType 请求方法类型
     * @param params     请求参数
     * @return 返回request
     */
    private Request buildRequest(String url, HttpMethodType methodType, Map<String, String> params) {
        Request.Builder builder = new Request.Builder();
        if (methodType == HttpMethodType.GET) {
            String paramsStr = "";
            String realUrl = url;
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    paramsStr += entry.getKey() + "=" + entry.getValue();
                }
                realUrl = url + "?" + paramsStr;
                LogUtils.e("OkHttpHelper", "buildRequest: " + realUrl);
            }
            builder.url(realUrl).get();
        } else if (methodType == HttpMethodType.POST) {
            RequestBody requestBody = buildFormData(params);
            builder.url(url).post(requestBody);
        }
        return builder.build();
    }

    /**
     * 构建post方式请求的请求body
     *
     * @param params
     * @return
     */
    private RequestBody buildFormData(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();//创建表单体建造者
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }


    enum HttpMethodType {
        GET, POST,
    }
}
