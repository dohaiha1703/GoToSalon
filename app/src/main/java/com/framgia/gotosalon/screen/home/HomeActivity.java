package com.framgia.gotosalon.screen.home;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.framgia.gotosalon.R;
import com.framgia.gotosalon.screen.base.BaseActivity;
import com.framgia.gotosalon.screen.manager.ManagerActivity;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private static final String EXTRA_USER_KEY = "EXTRA_USER_KEY";
    private String mUserId;

    public static Intent getHomeIntent(Context context, String userId) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(EXTRA_USER_KEY, userId);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    @Override
    protected void initComponent() {
        ViewPager viewPager = findViewById(R.id.view_pager_home);
        HomePageAdapter adapter = new HomePageAdapter(getSupportFragmentManager(), getApplicationContext());
        TabLayout tabLayout = findViewById(R.id.tab_home);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        findViewById(R.id.image_menu).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mUserId = getIntent().getStringExtra(EXTRA_USER_KEY);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_menu:
                startActivity(ManagerActivity.getManagerIntent(this, mUserId));
                break;
            case R.id.image_back:
                finish();
                break;
            case R.id.image_search:
                break;
        }
    }
}
