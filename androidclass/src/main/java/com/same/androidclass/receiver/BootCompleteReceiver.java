package com.same.androidclass.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.same.androidclass.service.CoreService;
import com.same.androidclass.service.ExtraService;
import com.same.androidclass.util.AppUtil;

/**
 * app-Core 广播器 开机启动
 * Created by alic on 16-5-19.
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    private final static String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
//        if (ACTION_BOOT.equals(intent.getAction()))
//            Toast.makeText(context, "开机完毕~", Toast.LENGTH_LONG).show();

        //开启extra的服务
        Intent coreIntent = new Intent(context, CoreService.class);
//        coreIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(coreIntent);
    }
}
