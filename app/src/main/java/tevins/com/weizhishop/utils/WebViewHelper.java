package tevins.com.weizhishop.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;
import tevins.com.weizhishop.Constant;
import tevins.com.weizhishop.model.bean.AboutWebViewInfo;
import tevins.com.weizhishop.presenter.ApiCallback;
import tevins.com.weizhishop.utils.http.OkHttpHelper;

/**
 * Created by tevins on 2017/12/5/0005.
 */

public class WebViewHelper {
    private OkHttpHelper mOkHttpHelper;
    private WebView mWebView;
    private WebSettings mSettings;

    public WebViewHelper(WebView webView) {
        mOkHttpHelper = new OkHttpHelper();
        mWebView = webView;
        initWebSetting();
        initWebViewClient();
    }

    public boolean shouldShowWebView() {
        final boolean[] shouldShowWebView = {false};
        HashMap<String, String> params = new HashMap<>();
        params.put("appid", "12021632");
        mOkHttpHelper.post(Constant.API.CAN_SHOW_WEBVIEW, params, new ApiCallback<AboutWebViewInfo>() {
            @Override
            public void onSuccess(Response response, AboutWebViewInfo aboutWebViewInfo) {
                if (aboutWebViewInfo != null) {
                    String isshowwap = aboutWebViewInfo.getIsshowwap();
                    String wapurl = aboutWebViewInfo.getWapurl();
                    if (!TextUtils.isEmpty(isshowwap)) {
                        if ("2".equals(isshowwap)) {
                            shouldShowWebView[0] = false;
                        } else if ("1".equals(isshowwap)) {
                            loadWebViewUrl(wapurl);
//                                loadWebViewUrl("http://www.baidu.com");
                            shouldShowWebView[0] = true;
                        }
                    }
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }

            @Override
            public void onFailure(Call call, Exception e) {

            }
        });
        return shouldShowWebView[0];
    }

    private void loadWebViewUrl(String wapurl) {

//        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
//        mProgressBarHorizontal = (ProgressBar) findViewById(R.id.progress_bar_horizontal);
        // 若要显示本地文件和本地html文件应该放在哪
        // android工程目录下单assets文件
        mWebView.loadUrl(wapurl);
//        mUrl = "http://www.iplaysoft.com/";

    }


    /**
     * 初始化webview的设置
     */
    private void initWebSetting() {
        mSettings = mWebView.getSettings();
        //支持获取手势焦点
        mWebView.requestFocusFromTouch();
        //支持JS
        mSettings.setJavaScriptEnabled(true);
        //支持插件
        mSettings.setPluginState(WebSettings.PluginState.ON);
        //设置适应屏幕
        mSettings.setUseWideViewPort(true);
        mSettings.setLoadWithOverviewMode(true);
        //设置拦截不加载图片
//        mSettings.setBlockNetworkImage(true);
        //支持缩放
        mSettings.setSupportZoom(true);
        mSettings.setBuiltInZoomControls(true);//必须要加，才能缩放
        //隐藏原生的缩放控件
        mSettings.setDisplayZoomControls(false);
        //支持内容重新布局
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mSettings.supportMultipleWindows();
        mSettings.setSupportMultipleWindows(true);
        //设置缓存模式
        mSettings.setDomStorageEnabled(true);
        mSettings.setDatabaseEnabled(true);
        mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mSettings.setAppCacheEnabled(true);
        mSettings.setAppCachePath(mWebView.getContext().getCacheDir().getAbsolutePath());
        //设置可访问文件
        mSettings.setAllowFileAccess(true);
        //当webview调用requestFocus时为webview设置节点
        mSettings.setNeedInitialFocus(true);
        //支持自动加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            mSettings.setLoadsImagesAutomatically(true);
        } else {
            mSettings.setLoadsImagesAutomatically(false);
        }
        //设置编码格式
        mSettings.setDefaultTextEncodingName("UTF-8");
    }

    /**
     * 初始化WebChromeClient
     */
    private void initWebViewClient() {
        mWebView.setWebViewClient(new WebViewClient() {
            //页面开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            //页面加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            //是否在WebView内加载新页面
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.toString());
                return true;
            }

            //网络错误时回调的方法
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //在这里写网络错误时的逻辑，比如显示一个错误页面
                Log.e("WebViewActivity", "onReceivedError: " + "网络错误，请稍后重试");
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }
        });
    }
}
