package com.coopbuy.mall.base;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.coopbuy.mall.R;
import com.coopbuy.mall.widget.LoadingBox;
import com.coopbuy.mall.widget.TitleBar;

import butterknife.ButterKnife;

/**
 * Activity抽象类
 * @author ymb
 * Create at 2017/7/13 11:19
 */
public abstract class BaseActivity<P extends BasePresenter, M extends BaseModel> extends AppCompatActivity implements TitleBar.TitleBarClickListener {
    public P mPresenter;
    public M mModel;
    public Context mContext;
    private LoadingBox box;
    private TitleBar mTitleBar;

    private View.OnClickListener mReloadListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            networkRetry();
        }
    };

    private View.OnClickListener mCustomListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            customClick();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetContentView();
        setContentView(getLayoutId());
        mContext = this;
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
        if (findViewById(R.id.box) != null) {
            box = new LoadingBox(this, findViewById(R.id.box));
            box.setClickListener(mReloadListener);
            box.setCustomClickListener(mCustomListener);
        }
        if (findViewById(R.id.title_bar) != null) {
            mTitleBar = (TitleBar) findViewById(R.id.title_bar);
            mTitleBar.setTitleBarClickListener(this);
        }
        ButterKnife.bind(this);

        this.initModel();
        this.initView();
        this.initPresenter();
    }

    /**
     * 设置layout前配置（设置无标题、竖屏或着色状态栏等）
     */
    private void doBeforeSetContentView() {
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 获取布局文件
     */
    public abstract int getLayoutId();

    /**
     * 初始化Model
     */
    public abstract void initModel();

    /**
     * 初始化Presenter
     */
    public abstract void initPresenter();

    /**
     * 初始化View
     */
    public abstract void initView();

    /**
     * 全覆盖加载框
     */
    public void showFillLoading() {
        box.showLoadingLayout();
    }

    /**
     * 显示透明加载框
     */
    public void showTransLoading() {
        box.showTransLoadingLayout();
    }

    /**
     * 显示无数据页面
     */
    public void showNoDataLayout() {
        box.showNoDataLayout();
    }

    /**
     * 显示无网络页面
     */
    public void showNetOffLayout() {
        box.showInternetOffLayout();
    }

    /**
     * 显示网络错误页面
     */
    public void showNetErrorLayout() {
        box.showExceptionLayout();
    }

    /**
     * 显示自定义页面
     */
    public void showCustomLayout(int layoutId) {
        View customView = getLayoutInflater().inflate(layoutId, null, false);
        box.addCustomView(customView, layoutId + "");
        box.showCustomView(layoutId + "");
    }

    /**
     * 隐藏所有覆盖层,显示正常页面
     */
    public void stopAll() {
        box.hideAll();
    }

    /**
     * 网络重载按钮
     */
    protected void networkRetry() {}

    /**
     * 自定义页面按钮
     */
    protected void customClick() {}


    /**
     * TitleBar返回按钮
     */
    @Override
    public void clickTitleBarBack() {
        onBackPressed();
    }

    /**
     * TitleBar右边文字或按钮
     */
    @Override
    public void clickTitleBarRight() {

    }

    /**
     * TitleBar返回按钮和手机back键执行动作一致
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 设置TitleBar标题
     * @param title
     */
    public void setTitle(String title) {
        mTitleBar.setTitleText(title);
    }

    /**
     * 设置TitleBar标题
     * @param titleResId
     */
    public void setTitle(int titleResId) {
        mTitleBar.setTitleText(titleResId);
    }

    /**
     * 设置TitleBar右边文字
     * @param text
     */
    public void setRightText(String text) {
        mTitleBar.setRightText(text);
    }

    /**
     * 设置TitleBar右边文字
     * @param textResId
     */
    public void setRightText(int textResId) {
        mTitleBar.setRightText(textResId);
    }

    /**
     * 设置TitleBar右边图片
     * @param imageResId
     */
    public void setRightImage(int imageResId) {
        mTitleBar.setRightImage(imageResId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
        ButterKnife.unbind(this);
    }
}
