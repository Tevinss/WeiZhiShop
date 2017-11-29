package tevins.com.weizhishop.bean;

import java.io.Serializable;

/**
 * Created by yewyw on 2017/11/29/0029.
 */

public class BaseBean implements Serializable {
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
