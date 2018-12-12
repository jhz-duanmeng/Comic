package com.meng.callnumber;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Dmeng on 2018/12/12.
 */

public class PhoneStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

//        String action = intent.getAction();
//        if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
//            if(null != BaseAppliction.phone_num) {
//                BaseAppliction app = (BaseAppliction) context.getApplicationContext();
//                app.showCtrlWin();
//            }
//        } else if (action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
//            TelephonyManager tm = (TelephonyManager) context
//                    .getSystemService(Context.TELEPHONY_SERVICE);
//            switch (tm.getCallState()) {
//                case TelephonyManager.CALL_STATE_RINGING:
//                    break;
//                case TelephonyManager.CALL_STATE_OFFHOOK:
//                    break;
//                case TelephonyManager.CALL_STATE_IDLE:
//                    if(null != BaseAppliction.phone_num) {
//                        Intent it = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + BaseAppliction.phone_num));
//                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(it);
//                        BaseAppliction.mCallCount ++;
//                        BaseAppliction app = (BaseAppliction) context.getApplicationContext();
//                        app.updateState();
//                    }
//                    break;
//            }
//        }
    }
}
