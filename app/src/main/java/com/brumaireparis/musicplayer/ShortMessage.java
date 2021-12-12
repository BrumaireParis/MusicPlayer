package com.brumaireparis.musicplayer;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ShortMessage extends Activity {

    public void changeStatusBarTextColor(Window window, boolean isBlack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = window.getDecorView();
            int flags;
            if (isBlack) {
                //更改文字颜色为深黑色
                flags = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            else {
                //更改文字颜色为浅色
                flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            }
            decor.setSystemUiVisibility(flags);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        changeStatusBarTextColor(this.getWindow(),true);

        askPermissions();

        Button bt_send=(Button)this.findViewById(R.id.bt_send);
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText et_phnum=(EditText)findViewById(R.id.et_phnum);
                EditText et_text=(EditText)findViewById(R.id.et_text);
                String phnum=et_phnum.getText().toString();
                String text=et_text.getText().toString();

                SmsManager smsManager=SmsManager.getDefault();
//                获取一个默认实例，根据描述是将smsManager与一个subscription id连接

                ArrayList<String> texts=smsManager.divideMessage(text);
//                将短信内容以70个字为界限分隔开

                for(String Text:texts) {
                    smsManager.sendTextMessage(phnum,null,Text,null,null);
//                   sendTextMessage的参数分别为destinationAdddress 目标号码,scAddress使用的短信服务中心（使用默认的就用null）,
//                   text短信内容，sentIntent默认为null,deliveryIntent可以设置查看对方接受的状态（是否成功接收）,
                }
//                用foreach循环将texts遍历，然后存放在数组Text中

                Toast.makeText(ShortMessage.this,"success",Toast.LENGTH_LONG).show();
//                发送是否成功的提示，context指在哪个页面显示信息，MainActivity.this指在当前页面显示
//                text是指信息的内容
//                Toast.LENGTH_LONG指显示的时间长短

            }
        });
    }

    protected void askPermissions(){
        String[] permissions = {
                "android.permission.SEND_SMS"
        };
        int requestCode = 203;
        requestPermissions(permissions,requestCode);
    }
}
