package tevins.com.weizhishop;

/**
 * Created by tevins on 2017/11/30/0030.
 */

public class Constant {
    public static class API {

        public static final String BASEURL = BuildConfig.BASEURL;
        /**
         * 首页分类接口
         */
        public static final String CAN_SHOW_WEBVIEW = "http://www.27305.com/frontApi/getAboutUs";
        /**
         * 首页分类接口
         */
        public static final String CAMPAIN_HOME = BASEURL + "campaign/recommend";

        /**
         * 首页banner接口
         */
        public static final String HOME_BANNER = BASEURL + "banner/query";
        /**
         * 热卖商品接口
         */
        public static final String WARES_HOT = BASEURL + "wares/hot";
        /**
         * 商品分类信息
         */
        public static final String CATEGORY_LIST = BASEURL + "category/list";
        /**
         * 分类下的商品信息
         */
        public static final String WARES_LIST = BASEURL + "wares/list";
    }
}
