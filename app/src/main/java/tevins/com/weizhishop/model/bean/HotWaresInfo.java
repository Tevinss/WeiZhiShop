package tevins.com.weizhishop.model.bean;

/**
 * Created by tevins on 2017/12/3 003.
 */

public class HotWaresInfo {
    /**
     * id : 10
     * categoryId : 5
     * campaignId : 1
     * name : 金士顿（Kingston）DTM30R 16GB USB3.0 精致炫薄金属U盘
     * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_54b78bf0N24c00fc2.jpg
     * price : 42.9
     * sale : 8442
     */

    private int id;
    private int categoryId;
    private int campaignId;
    private String name;
    private String imgUrl;
    private double price;
    private int sale;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }
}
