package com.framgia.gotosalon.screen.main;

import android.view.View;

import com.framgia.gotosalon.R;
import com.framgia.gotosalon.screen.base.BaseActivity;
import com.framgia.gotosalon.screen.home.HomeActivity;
import com.framgia.gotosalon.screen.login.LoginActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent() {
        findViewById(R.id.button_guest).setOnClickListener(this);
        findViewById(R.id.text_login_to_ad).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_guest:
                navigateScreen(MainActivity.this, HomeActivity.class);
                break;
            case R.id.text_login_to_ad:
                navigateScreen(MainActivity.this, LoginActivity.class);
                break;
        }
    }
}
