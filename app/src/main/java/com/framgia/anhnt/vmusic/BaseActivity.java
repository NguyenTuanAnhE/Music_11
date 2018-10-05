package com.framgia.anhnt.vmusic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.framgia.anhnt.vmusic.utils.ColorUtils;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getFullScreen()) {
            ColorUtils.setFullScreen(this);
        }
        setContentView(getContentLayout());
        ColorUtils.changeStatusBarColor(this, getStatusBarColor());
        initComponents();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeEnable());
            getSupportActionBar().setDisplayShowHomeEnabled(showHomeEnable());
        }
    }

 protected void requestPermission(String[] permissions) {
        if (!hasPermissions(this, permissions)) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_ALL);
        }
    }

    protected boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    protected abstract boolean getFullScreen();

    protected abstract boolean showHomeEnable();

    public abstract int getContentLayout();

    public abstract int getStatusBarColor();

    public abstract void initComponents();

}
