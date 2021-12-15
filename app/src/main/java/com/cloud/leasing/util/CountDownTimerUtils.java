package com.cloud.leasing.util;

import android.graphics.Color;
import android.os.CountDownTimer;

import androidx.appcompat.widget.AppCompatTextView;

public class CountDownTimerUtils extends CountDownTimer {
    private AppCompatTextView mTextView;

    public CountDownTimerUtils(AppCompatTextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mTextView = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false); //设置不可点击
        mTextView.setText(millisUntilFinished / 1000 + "s");  //设置倒计时时间
        mTextView.setTextColor(Color.parseColor("#999999")); //设置按钮为灰色，这时是不能点击的
    }

    @Override
    public void onFinish() {
        mTextView.setText("获取验证码");
        mTextView.setClickable(true);//重新获得点击
        mTextView.setTextColor(Color.parseColor("#0E64BC"));  //还原背景色
    }
}
