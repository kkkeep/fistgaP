package com.jy.fistga.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/*
 * created by taofu on 2018/9/4
 **/
public class StatusBarManager {


    /**
     * 仅仅让状态栏透明，布局类容位于状态下方
     */
    public static void translucentStatusBar(Activity activity) {

        if (!SystemFacade.hasKitKat()) {
            return;
        }
        //如果系统版本大于API 19 小于 API 小于 API 21
        if (SystemFacade.hasKitKat() && !SystemFacade.hasLollipop()) {

            Window window = activity.getWindow();
            //在 4.4 api 19 和 API 20 添加FLAG_TRANSLUCENT_STATUS 是让系统状态栏为透明，同时系统会自动设置View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | NewsView.SYSTEM_UI_FLAG_LAYOUT_STABLE
            //所以布局延伸到了状态下面
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else if (SystemFacade.hasLollipop()) {
            Window window = activity.getWindow();
            //添加Flag把状态栏设为可绘制模式
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //设置状态栏为透明
            window.setStatusBarColor(Color.TRANSPARENT);

        }

    }


    /**
     * 改变状态栏为透明并让布局延伸到状态底部
     */
    public static void immersive(Activity activity) {

        if (!SystemFacade.hasKitKat()) {
            return;
        }
        //如果系统版本大于API 19 小于 API 小于 API 21
        if (SystemFacade.hasKitKat() && !SystemFacade.hasLollipop()) {

            Window window = activity.getWindow();
            //在 4.4 api 19 和 API 20 添加FLAG_TRANSLUCENT_STATUS 是让系统状态栏为透明，同时系统会制动设置View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | NewsView.SYSTEM_UI_FLAG_LAYOUT_STABLE
            //所以布局延伸到了状态下面
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else if (SystemFacade.hasLollipop()) {
            Window window = activity.getWindow();
            //添加Flag把状态栏设为可绘制模式
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //设置状态栏为透明
            window.setStatusBarColor(Color.TRANSPARENT);
            //设置让布局延伸到状态栏下面
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        }

    }

    /**
     * 改变状态栏为透明并让布局延伸到状态底部
     */
    public static void translucentStatusBarAndImmersive(Activity activity) {

        if (!SystemFacade.hasKitKat()) {
            return;
        }
        //如果系统版本大于API 19 小于 API 小于 API 21
        if (SystemFacade.hasKitKat() && !SystemFacade.hasLollipop()) {

            Window window = activity.getWindow();
            //在 4.4 api 19 和 API 20 添加FLAG_TRANSLUCENT_STATUS 是让系统状态栏为透明，同时系统会制动设置View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | NewsView.SYSTEM_UI_FLAG_LAYOUT_STABLE
            //所以布局延伸到了状态下面
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else if (SystemFacade.hasLollipop()) {
            Window window = activity.getWindow();
            //添加Flag把状态栏设为可绘制模式
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //设置状态栏为透明
            window.setStatusBarColor(Color.TRANSPARENT);
            //设置让布局延伸到状态栏底部
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        }

    }


    /**
     * 设置状态栏字体为灰色
     */
    public static void lightStatusBar(Activity activity) {
        if (SystemFacade.hasM()) {
            int flag = activity.getWindow().getDecorView().getSystemUiVisibility();
            if ((flag | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) != flag) { // 如果状态栏字体不是灰色
                activity.getWindow().getDecorView().setSystemUiVisibility(flag ^ View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    /**
     * 设置状态栏字体为白色
     */
    public static void darkStatusBar(Activity activity) {
        if (SystemFacade.hasM()) {
            int flag = activity.getWindow().getDecorView().getSystemUiVisibility();
            if ((flag | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) == flag) { // 如果状态栏字体不是白色
                //flag &= ~NewsView.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                //activity.getWindow().getDecorView().setSystemUiVisibility(flag);
                activity.getWindow().getDecorView().setSystemUiVisibility(flag ^ View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    /**
     * 设置状态栏颜色
     */
    public static void setStatusBarColor(Activity activity, int color) {
        if (!SystemFacade.hasKitKat()) {
            return;
        }
        //如果系统版本大于API 19 小于 API 小于 API 21
        if (SystemFacade.hasKitKat() && !SystemFacade.hasLollipop()) {

            Window window = activity.getWindow();
            //在 4.4 api 19 和 API 20 添加FLAG_TRANSLUCENT_STATUS 是让系统状态栏为透明，同时系统会制动设置View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | NewsView.SYSTEM_UI_FLAG_LAYOUT_STABLE
            //所以布局延伸到了状态下面
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            createOrChangeMockStatusBarColor(activity, color);
            ViewGroup contentView = activity.getWindow().findViewById(android.R.id.content);
            View rootView = contentView.getChildAt(0);
            if (rootView != null) {
                rootView.setFitsSystemWindows(true);
            }

        } else if (SystemFacade.hasLollipop()) {
            Window window = activity.getWindow();

            //添加Flag把状态栏设为可绘制模式
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(color);

        }

    }

    /**
     * 因此底部导航栏，
     */
    public static void hideNavigationBar(Activity activity) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT < 19) { // lower api
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            //for new api versions.
            View decorView = activity.getWindow().getDecorView();

            // 如果不设置SYSTEM_UI_FLAG_IMMERSIVE_STICKY ，那么在点击屏幕时候底部导航栏会弹出,相当于View.SYSTEM_UI_FLAG_HIDE_NAVIGATION 不起作用，被清除
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private static final String MOCK_STATUS_BAR_VIEW = "mock_status_bar";

    private static void createOrChangeMockStatusBarColor(Activity activity, int color) {

        Window window = activity.getWindow();
        ViewGroup mDecorView = (ViewGroup) window.getDecorView();

        View fakeView = mDecorView.findViewWithTag(MOCK_STATUS_BAR_VIEW);
        if (fakeView != null) {
            fakeView.setBackgroundColor(color);
        } else {
            View mockView = new View(activity);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SystemFacade.getStatusBarHeight(activity));
            layoutParams.gravity = Gravity.TOP;
            mockView.setLayoutParams(layoutParams);
            mockView.setBackgroundColor(color);
            mockView.setTag(MOCK_STATUS_BAR_VIEW);

            mDecorView.addView(mockView);

        }
    }


    private boolean isXiaomi(String brand) {

        return brand.contains("xiaomi");
    }

    private boolean isMeizu(String brand) {
        return brand.contains("meizu");
    }

}

