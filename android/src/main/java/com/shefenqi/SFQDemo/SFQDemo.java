package com.shefenqi.SFQLeka;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.facebook.react.BuildConfig;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.fenqile.core.FqlPaySDK;
import com.fenqile.core.PayCallback;
import com.fenqile.core.PayRequest;
import com.fenqile.core.PayResult;
import com.fenqile.core.UIConfig;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SFQLeka extends ReactContextBaseJavaModule implements ActivityEventListener {
    public static String APP_ID = "";
    public static String APP_KEY = "";

    Context context;
    private int mTime = 8000;
    private Promise mPromise;

    public SFQLeka(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;

        init("lekatest");
    }

    @Override
    public String getName() {
        return "SFQLeka";
    }

    public void init(String clientId) {
        //必传，上下文对象
        FqlPaySDK
                .with(getReactApplicationContext()) //必传，上下文对象
                .setClientId(clientId) //必传，应用申请注册的 clientId
                .setDebug(BuildConfig.DEBUG) //可选项，设置是否处于调试模式，默认 false
                .init(); //开始初始化
    }

    public void setCustomUI() {
        UIConfig uiConfig = new UIConfig() //设置页面标题颜色，默认为白色，入参必须符合 ColorInt 规范
                .setTitleColor(0xFFFFFFFF);
//                .overrideStartTransition(R.anim.fenqile_start_enter, R.anim.fenqile_start_exit) //重写 startActivity 动画，默认右侧滑入
//                .overrideFinishTransition(R.anim.fenqile_finish_enter, R.anim.fenqile_finish_exit); //重写 Activity finish 动画，默认向右滑出
        // 设置自定义的 UI 对象
        FqlPaySDK.setCustomUI(uiConfig);
    }

    @ReactMethod
    public void doFqlPay() {
        String redirectUrl = "https://www.baidu.com";

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("agent", "1064660");
        map.put("channel", "leka");
        map.put("phone", "13538805424");
        JSONObject attach = new JSONObject(map);

//        agent":@"1064660",
//        @"channel":@"leka",
//        @"phone":@"13538805424",
//        @"btn_color":@"3B9BFF"};

        final PayRequest payRequest = new PayRequest()
                .setRedirectUrl(redirectUrl) //设置链接，携带订单信息
                .setAttach(attach); //JSONObject 对象，携带参数信息

        FqlPaySDK.doFqlPay(payRequest, new PayCallback() {
                @Override
                public void onOpenSuccess() {
                    System.out.println("onOpenSuccess-------------");
                }
                @Override
                public void onPayResult(PayResult payResult) {
                    // TODO: do your business here
                    System.out.println("payResult-------------");
                    System.out.println(payResult);
                }
        });
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onNewIntent(Intent intent) {

    }
}
