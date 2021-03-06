package com.coopbuy.mall.ui.mainpage.adapter;


import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.alibaba.android.vlayout.LayoutHelper;
import com.coopbuy.mall.R;
import com.coopbuy.mall.api.Constant;
import com.coopbuy.mall.api.login.HomePageDataResponse;
import com.coopbuy.mall.base.BaseDelegateAdapter;
import com.coopbuy.mall.base.BaseRecyclerHolder;
import com.coopbuy.mall.utils.StringUtils;
import com.coopbuy.mall.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class HomeLayoutAdapter_6 extends BaseDelegateAdapter<HomePageDataResponse.FloorsBean.FloorItemsBean> {

    private List<HomePageDataResponse.FloorsBean.FloorItemsBean> floorItems;

    public HomeLayoutAdapter_6(Context ctx, List<HomePageDataResponse.FloorsBean.FloorItemsBean> list, LayoutHelper mLayoutHelper) {
        super(ctx, list, mLayoutHelper);
        this.floorItems = list;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_6_home;
    }

    @Override
    protected void bindData(BaseRecyclerHolder holder, int position, final HomePageDataResponse.FloorsBean.FloorItemsBean item) {
        ((SimpleDraweeView) holder.getView(R.id.sdv_image)).setImageURI(Uri.parse(Constant.IMAGE_SERVER_URL + item.getObjectData().getImageUrl()));
        holder.getTextView(R.id.tv_name).setText(item.getObjectData().getGoodsName());
        holder.getTextView(R.id.tv_desc).setText(item.getObjectData().getPromotionDesc());
        holder.getTextView(R.id.tv_price).setText("¥" + StringUtils.keepTwoDecimalPoint(item.getObjectData().getSellingPrice()));

        holder.getView(R.id.ll_item_6_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.toastShort("ObjectId " + item.getObjectId());
            }
        });
    }
}
