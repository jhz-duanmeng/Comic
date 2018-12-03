package com.meng.wether.view;

/**
 *
 * @author Dmeng
 * @date 2018/11/20
 */

public interface IWetherView {

    /**
     * 显示天气信息
     *
     * @param info 天气信息
     */
    public void onInfoUpdate(String info);

    /**
     * 显示获取信息等待对话框
     */
    public void showWaitingDialog();

    /**
     * 取消显示对话框
     */
    public void dismissWaitingDialog();
}
