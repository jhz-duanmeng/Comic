package com.meng.callnumber;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.meng.callnumber.utils.PreferencesUtils;
import com.meng.wether.ITelephony;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_CALL_PERMISSION = 10111; //拨号请求码
    private Button bt_call;
    private EditText et_number;
    private String number;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        bt_call = (Button) findViewById(R.id.bt_call);
        et_number = (EditText) findViewById(R.id.et_number);
        bt_call.setOnClickListener(this);
        String shareNumber = PreferencesUtils.getString(this,"number");
        et_number.setText(shareNumber);
        PhoneStateReceiver myBroadcast = new PhoneStateReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
        filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(myBroadcast,filter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_call:
                number = et_number.getText().toString();
                PreferencesUtils.putString(this,"number",number);
                callPhone(number);
                break;

            default:
                break;

        }
    }

    /**
     * 判断是否有某项权限
     * @param stringPermission 权限
     * @param requestCode 请求码
     * @return
     */
    public boolean checkReadPermission(String stringPermission,int requestCode) {
        Log.d(TAG, "checkReadPermission: ");
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(this, stringPermission) == PackageManager.PERMISSION_GRANTED) {
            //已有权限
            flag = true;
        } else {
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{stringPermission}, requestCode);
        }
        return flag;
    }

    private void callPhone(String phoneNum) {


//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//
//            return;
//        }

        if(checkReadPermission(Manifest.permission.CALL_PHONE,REQUEST_CALL_PERMISSION)) {
            Log.d(TAG, "callPhone: ");
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + phoneNum);
            intent.setData(data);
            startActivity(intent);
        }

    }

    public static void endCall(Context cx) { //挂断电话
        TelephonyManager telMag = (TelephonyManager) cx
                .getSystemService(Context.TELEPHONY_SERVICE);
        Class<TelephonyManager> c = TelephonyManager.class;
        Method mthEndCall = null;
        try {
            mthEndCall = c.getDeclaredMethod("getITelephony", (Class[]) null);
            mthEndCall.setAccessible(true);
            ITelephony iTel = (ITelephony) mthEndCall.invoke(telMag,
                    (Object[]) null);
            iTel.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查权限后的回调
     * @param requestCode 请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: ");
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION:
                if (permissions.length != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"请允许拨号权限后再试",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }



    public class PhoneStateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            Log.d(TAG, "onReceive: ACTION_NEW_OUTGOING_CALL");

        } else if (action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            switch (tm.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:
                    Log.d(TAG, "onReceive: CALL_STATE_RINGING");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    endCall(context);
                    Log.d(TAG, "onReceive: CALL_STATE_OFFHOOK");
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    callPhone(number);
                    Log.d(TAG, "onReceive: CALL_STATE_IDLE");
                    break;

                default:
                    break;

            }
        }
        }
    }

}
