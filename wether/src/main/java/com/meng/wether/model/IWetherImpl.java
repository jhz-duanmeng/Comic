package com.meng.wether.model;

/**
 * @author Dmeng
 * @date 2018/11/20
 */
public class IWetherImpl implements IWetherModel {

    private String info;

    /**
     * @return info
     */
    @Override
    public String getInfo() {
        return info;
    }

    /**
     * @param info 天气信息
     */
    @Override
    public void setInfo(String info) {
        this.info = info;
    }
}
