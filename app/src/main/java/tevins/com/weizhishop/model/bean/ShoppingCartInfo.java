package tevins.com.weizhishop.model.bean;

/**
 * Created by tevins on 2017/12/4/0004.
 */

public class ShoppingCartInfo extends WaresInfo {
    private int count = 1;
    private boolean isChecked = true;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
