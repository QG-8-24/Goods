package com.com.example.goods.utils;

import android.os.CountDownTimer;
import android.widget.Button;

public class MyCountDownTimer extends CountDownTimer {
    private Button timeButton;

    public MyCountDownTimer(long millisInFuture, long countDownInterval, Button timeButton) {
        super(millisInFuture, countDownInterval);
        this.timeButton = timeButton;
    }


    //计时过程
    @Override
    public void onTick(long l) {
        //防止计时过程中重复点击
        timeButton.setClickable(false);
        timeButton.setText(l / 1000 + "秒");

    }

    //计时完毕的方法
    @Override
    public void onFinish() {
        //重新给Button设置文字
        timeButton.setText("重新发送验证码");
        //设置可点击
        timeButton.setClickable(true);
    }
}