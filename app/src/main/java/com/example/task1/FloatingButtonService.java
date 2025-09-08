package com.example.task1;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class FloatingButtonService extends Service {

    private WindowManager windowManager;
    private LinearLayout floatingButton;
    private LinearLayout menuLayout;
    private boolean menuVisible = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);


        floatingButton = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.floating_button, null);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.END;
        params.x = 0;
        params.y = 100;

        windowManager.addView(floatingButton, params);

        menuLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.floating_menu, null);

        WindowManager.LayoutParams menuParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        menuParams.gravity = Gravity.TOP | Gravity.END;
        menuParams.x = 0;
        menuParams.y = 250;

        windowManager.addView(menuLayout, menuParams);
        menuLayout.setVisibility(View.GONE);

        // Set click listener for the floating button
        ImageButton mainButton = floatingButton.findViewById(R.id.floatingButton);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenu();
            }
        });
        ImageButton phoneInfoBtn = menuLayout.findViewById(R.id.phoneInfoBtn);
        ImageButton simInfoBtn = menuLayout.findViewById(R.id.simInfoBtn);

        phoneInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FloatingButtonService.this, PhoneInfoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                hideMenu();
            }
        });

        simInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FloatingButtonService.this, SimInfoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                hideMenu();
            }
        });
    }

    private void toggleMenu() {
        if (menuVisible) {
            hideMenu();
        } else {
            showMenu();
        }
    }

    private void showMenu() {
        menuLayout.setVisibility(View.VISIBLE);
        menuVisible = true;
    }

    private void hideMenu() {
        menuLayout.setVisibility(View.GONE);
        menuVisible = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingButton != null) windowManager.removeView(floatingButton);
        if (menuLayout != null) windowManager.removeView(menuLayout);
    }
}