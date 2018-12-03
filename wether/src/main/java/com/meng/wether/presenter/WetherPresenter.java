package com.meng.wether.presenter;

import com.meng.wether.model.IWetherImpl;
import com.meng.wether.model.IWetherModel;
import com.meng.wether.utils.ThreadUtils;
import com.meng.wether.view.IWetherView;

import java.util.concurrent.ExecutorService;

/**
 * Created by Dmeng on 2018/11/20.
 *
 * @author Dmeng
 */
public class WetherPresenter {

    IWetherView mView;

    IWetherModel mModel;

    /**
     * Presenter也要开发API供View调用。
     * 所以Presenter要有requestWetherInfo()方法
     * <p>
     * 供View层调用，用来请求天气数据
     */
    public void requestWetherInfo() {
        getNetworkInfo();
    }

    //Presenter持有View的引用，所以在这里要将View.interface注入到Presenter当中

    public WetherPresenter(IWetherView mView) {
        this.mView = mView;
        mModel = new IWetherImpl();
    }

    //presenter操作View，是通过View.interface，也就是View层定义的接口

    private void showWaitingDialog() {
        if (mView != null) {
            mView.showWaitingDialog();
        }
    }

    public void dismissWaitingDialog() {
        if (mView != null) {
            mView.dismissWaitingDialog();
        }
    }

    public void updateWetherInfo(String info) {
        if (mView != null) {
            {
                mView.onInfoUpdate(info);
            }
        }

    }


    //Presenter—->Model  presenter获取到了数据，可以交给Model处理

    private void saveInfo(String info) {
        mModel.setInfo(info);
    }

    //Model—–>Presenter  Presenter可以通过Model对象获取本地数据

    private String localInfo() {
        return mModel.getInfo();
    }

    /**
     * Presenter需要向服务器获取代码
     */
    public void getNetworkInfo() {
        ExecutorService executor = ThreadUtils.getSimpleExecutor("updatewether");
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //打开等待对话框
                    showWaitingDialog();
                    //模拟网络耗时
                    Thread.sleep(3000);

                    String info = "21度，晴转多云";
                    //保存到model层
                    saveInfo(info);
                    //从Model层获取数据，为了演示效果，实际开发中根据情况需要。
                    String localInfo = localInfo();

                    //通知View层改变视图
                    updateWetherInfo(localInfo);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    dismissWaitingDialog();
                }
            }
        });
    }
}
