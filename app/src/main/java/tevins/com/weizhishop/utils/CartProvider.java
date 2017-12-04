package tevins.com.weizhishop.utils;

import android.content.Context;
import android.util.SparseArray;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import tevins.com.weizhishop.model.bean.ShoppingCartInfo;
import tevins.com.weizhishop.model.bean.WaresInfo;

/**
 * Created by tevins on 2017/12/4/0004.
 * 提供购物车数据的存储，包括内存缓存和本地缓存
 */

public class CartProvider {
    private final SparseArray<ShoppingCartInfo> mShoppingCartInfoSparseArray;
    private Context mContext;
    public static final String CART_JSON = "cart_json";

    public CartProvider(Context context) {
        mContext = context;
        mShoppingCartInfoSparseArray = new SparseArray<>();
        localListToSparse();
    }

    /**
     * 从本地取出存储数据,存到内存里，取出的是个list，将list转为SparseArray
     */
    private void localListToSparse() {
        List<ShoppingCartInfo> shoppingCartsFromLocal = getShoppingCartsFromLocal();
        if (shoppingCartsFromLocal != null) {
            for (ShoppingCartInfo shoppingCartInfo : shoppingCartsFromLocal) {
                mShoppingCartInfoSparseArray.put(shoppingCartInfo.getId(), shoppingCartInfo);
            }
        }
    }

    /**
     * 增，存储ShoppingCartInfo对象
     *
     * @param shoppingCartInfo
     */
    public void put(ShoppingCartInfo shoppingCartInfo) {
        //现从内存获取这个id的商品，如果这个商品已经有了，就把商品的数量+1，没有的话，就存到内存里
        ShoppingCartInfo temp = mShoppingCartInfoSparseArray.get(shoppingCartInfo.getId());
        if (temp != null) {
            temp.setCount(temp.getCount() + 1);
        } else {
            temp = shoppingCartInfo;
        }
        mShoppingCartInfoSparseArray.put(shoppingCartInfo.getId(), temp);//内存存储
        commit();//本地存储
    }

    /**
     * 删
     *
     * @param shoppingCartInfo
     */
    public void delete(ShoppingCartInfo shoppingCartInfo) {
        mShoppingCartInfoSparseArray.remove(shoppingCartInfo.getId());
        commit();//本地存储
    }

    /**
     * 改
     *
     * @param shoppingCartInfo
     */
    public void update(ShoppingCartInfo shoppingCartInfo) {
        mShoppingCartInfoSparseArray.put(shoppingCartInfo.getId(), shoppingCartInfo);
        commit();//本地存储
    }

    public List<ShoppingCartInfo> getAll() {
        List<ShoppingCartInfo> shoppingCartsFromLocal = getShoppingCartsFromLocal();
        return shoppingCartsFromLocal;
    }

    /**
     * 从本地sp文件获取购物车数据
     *
     * @return 返回一个集合
     */
    public List<ShoppingCartInfo> getShoppingCartsFromLocal() {
        String json = PreferencesUtils.getString(mContext, CART_JSON);
        List<ShoppingCartInfo> shoppingCarts = null;
        if (json != null) {
            shoppingCarts = JSONUtil.fromJson(json, new TypeToken<List<ShoppingCartInfo>>() {
            }.getType());
        }
        return shoppingCarts;
    }

    /**
     * 将数据存储到本地json,存的是一个集合
     */
    private void commit() {
        ArrayList<ShoppingCartInfo> shoppingCartInfoArrayList = sparseToList();
        PreferencesUtils.putString(mContext, CART_JSON, JSONUtil.toJSON(shoppingCartInfoArrayList));
    }

    /**
     * 将sparse转化为list
     */
    private ArrayList<ShoppingCartInfo> sparseToList() {
        int size = mShoppingCartInfoSparseArray.size();
        ArrayList<ShoppingCartInfo> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(mShoppingCartInfoSparseArray.valueAt(i));
        }
        return list;
    }

    public void put(WaresInfo waresInfo) {
        ShoppingCartInfo shoppingCartInfo = waresInfoToShoppingCartInfo(waresInfo);//将WaresInfo转化为ShoppingCartInfo
        put(shoppingCartInfo);//存储
    }

    private ShoppingCartInfo waresInfoToShoppingCartInfo(WaresInfo waresInfo) {
        /**
         * id : 10
         * categoryId : 5
         * campaignId : 1
         * name : 金士顿（Kingston）DTM30R 16GB USB3.0 精致炫薄金属U盘
         * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/s_recommend_54b78bf0N24c00fc2.jpg
         * price : 42.9
         * sale : 8442
         */
        ShoppingCartInfo shoppingCartInfo = new ShoppingCartInfo();
        shoppingCartInfo.setId(waresInfo.getId());
        shoppingCartInfo.setName(waresInfo.getName());
        shoppingCartInfo.setImgUrl(waresInfo.getImgUrl());
        shoppingCartInfo.setPrice(waresInfo.getPrice());
        return shoppingCartInfo;
    }
}
