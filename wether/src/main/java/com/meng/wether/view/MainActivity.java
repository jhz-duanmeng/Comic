package com.meng.wether.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.meng.wether.R;
import com.meng.wether.presenter.WetherPresenter;

/**
 * @author Dmeng
 */
public class MainActivity extends AppCompatActivity implements IWetherView, View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * 从视图界面出发，用户要请求数据，而Presenter是具体实现者，
     * 所以Presenter要提供方法代View的实现者调用，并且View的实现中必须要有Presenter的引用。
     */
    WetherPresenter mPresenter;
    private TextView mTvInfo;
    private Button mButton;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new WetherPresenter(this);
        mTvInfo = (TextView) findViewById(R.id.tv_wether);
        mButton = (Button) findViewById(R.id.bt_getWether);
        mButton.setOnClickListener(this);

    }

    @Override
    public void onInfoUpdate(final String info) {
        Log.d(TAG, "onInfoUpdate: " + info);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvInfo.setText(info);
            }
        });

    }

    @Override
    public void showWaitingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }

                mDialog = ProgressDialog.show(MainActivity.this, "", "正在获取中...");
            }
        });

    }

    @Override
    public void dismissWaitingDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        mPresenter.requestWetherInfo();
    }
}
