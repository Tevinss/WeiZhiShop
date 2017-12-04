package tevins.com.weizhishop.model.bean;

/**
 * Created by tevins on 2017/11/29/0029.
 */

public class Tab {
    private Class fragment;
    private int title;
    private int icon;

    public Tab(Class fragment, int title, int icon) {
        this.fragment = fragment;
        this.title = title;
        this.icon = icon;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
