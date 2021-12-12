package com.brumaireparis.musicplayer.functions;

import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import androidx.core.content.ContextCompat;

import com.brumaireparis.musicplayer.R;

public class Flashlight extends Activity {
    /**
     * 手势滑动测试demo
     * 步骤，
     * 1、实例化GestureDetector对象
     * 2、实例化 GestureDetector.OnGestureListener 手势监听对象
     * 3、覆写onTouchEvent方法，在onTouchEvent方法中将event对象传给gestureDetector.onTouchEvent(event);处理。
     */
    final int RIGHT = 0;
    final int LEFT = 1;
    private boolean isopen=false;

    private CameraManager manager;

    private GestureDetector gestureDetector;//要想使用手势滑动，就必须要这个GestureDetector对象
    /**
     * 要实现手指在屏幕上左右滑动的事件需要实例化对象GestureDetector，new GestureDetector(MainActivity.this,onGestureListener);
     * 首先实现监听对象GestureDetector.OnGestureListener，根据x或y轴前后变化坐标来判断是左滑动还是右滑动
     * 并根据不同手势滑动做出事件处理doResult（int action），
     然后覆写onTouchEvent方法，在onTouchEvent方法中将event对象传给gestureDetector.onTouchEvent(event);处理。
     */

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);

        changeStatusBarTextColor(this.getWindow(),true);

        gestureDetector = new GestureDetector(Flashlight.this, onGestureListener);

        manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        ImageButton imageButton = findViewById(R.id.flashButton);
        imageButton.setOnClickListener(v -> {
            if(!isopen)
            {
                imageButton.setBackground(ContextCompat.getDrawable(this,R.drawable.ic_power1));
                try {
                    manager.setTorchMode("0", true);
                }
                catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                isopen = true;
            }

            else {
                imageButton.setBackground(ContextCompat.getDrawable(this,R.drawable.ic_power0));
                try {
                    manager.setTorchMode("0", false);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
                isopen = false;
            }
        });

    }

    private GestureDetector.OnGestureListener onGestureListener =
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                       float velocityY) {
                    //e1就是初始状态的MotionEvent对象，e2就是滑动了过后的MotionEvent对象
                    //velocityX和velocityY就是滑动的速率
                    float x = e2.getX() - e1.getX();//滑动后的x值减去滑动前的x值 就是滑动的横向水平距离(x)
                    float y = e2.getY() - e1.getY();//滑动后的y值减去滑动前的y值 就是滑动的纵向垂直距离(y)
                    Log.w("tag", "x>" + x);
                    Log.w("tag", "y>" + y);
                    Log.w("tag", "velocityX>" + velocityX);
                    Log.w("tag", "velocityY>" + velocityY);
                    //如果滑动的横向距离大于100，表明是右滑了，那么就执行下面的方法，可以是关闭当前的activity
                    if (x > 100) {
                        doResult(RIGHT);
                        Log.w("tag", "RIGHT>" + x);
                    }
                    //如果滑动的横向距离大于100，表明是左滑了(因为左滑为负数，所以距离大于100就是x值小于-100)
                    if (x < -100) {
                        Log.w("tag", "LEFT>" + x);
                        doResult(LEFT);
                    }
                    return true;
                }
            };

    /**
     * 将MotionEvent事件处理交给gestureDetector对象
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println(" ACTION_DOWN");//手指在屏幕上按下
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println(" ACTION_MOVE");//手指正在屏幕上滑动
                break;
            case MotionEvent.ACTION_UP:
                System.out.println(" ACTION_UP");//手指从屏幕抬起了
                break;
            default:
                break;
        }
        return gestureDetector.onTouchEvent(event);
    }

    public void doResult(int action) {

        switch (action) {
            case RIGHT:
                System.out.println("go right");
                finish();
                break;
            case LEFT:
                System.out.println("go left");
                break;
        }
    }
}

/* 此处需要gestureDetector对象实例化*/
/*
 * //需要传入一个Context和一个手势监听OnGestureListener
 * //下面是源码
 public GestureDetector(Context context, OnGestureListener listener) {
 this(context, listener, null);
 }
 */