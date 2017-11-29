package tevins.com.weizhishop.bean;

/**
 * Created by yewyw on 2017/11/29/0029.
 */

public class Category extends BaseBean {
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, long id) {
        this.name = name;
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
