package com.meng.wether.model;

/**
 * @author Dmeng
 * @date 2018/11/20
 */

public interface IWetherModel {

    /**
     * 提供数据
     */
    public String getInfo();

    /**
     * 存储数据
     */
    public void setInfo(String info);
}
