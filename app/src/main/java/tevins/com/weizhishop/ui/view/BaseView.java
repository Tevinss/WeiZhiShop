package tevins.com.weizhishop.ui.view;

/**
 * Created by tevins on 2017/11/30/0030.
 */

public interface BaseView<T> {
    void showLoading();

    void hideLoading();

    void showError();


    void setHomeBannerInfo(Object object);

    void setHomeCategoryInfo(Object object);
}
