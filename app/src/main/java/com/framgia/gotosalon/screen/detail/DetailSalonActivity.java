package com.framgia.gotosalon.screen.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.framgia.gotosalon.R;
import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.screen.base.BaseActivity;

import java.io.Serializable;

public class DetailSalonActivity extends BaseActivity implements View.OnClickListener, DetailContract.View {
    private static final String EXTRA_SALON_KEY = "EXTRA_SALON_KEY";
    private static final String URI_PHONE = "tel:";
    private static final String URI_EMAIL = "mailto:";
    private static final String URI_MAP = "geo:0,0?q=";
    private static final String PACKAGE_MAP = "com.google.android.apps.maps";
    private TextView mTextSalonName;
    private TextView mTextSalonAddress;
    private TextView mTextSalonOpen;
    private TextView mTextSalonClose;
    private TextView mTextSalonViews;
    private TextView mTextSalonEmail;
    private TextView mTextSalonPhone;
    private TextView mTextSalonDescription;
    private ImageView mImageView;
    private Salon mSalon;
    private DetailContract.Presenter mPresenter;

    public static Intent getDetailSalonIntent(Context context, Salon salon) {
        Intent intent = new Intent(context, DetailSalonActivity.class);
        intent.putExtra(EXTRA_SALON_KEY, (Serializable) salon);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail_salon;
    }

    @Override
    protected void initComponent() {
        mImageView = findViewById(R.id.image_salon);
        mTextSalonName = findViewById(R.id.text_salon_name);
        mTextSalonAddress = findViewById(R.id.text_salon_address);
        mTextSalonOpen = findViewById(R.id.text_salon_open);
        mTextSalonClose = findViewById(R.id.text_salon_close);
        mTextSalonViews = findViewById(R.id.text_salon_views);
        mTextSalonEmail = findViewById(R.id.text_salon_email);
        mTextSalonPhone = findViewById(R.id.text_salon_phone);
        mTextSalonDescription = findViewById(R.id.text_salon_description);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.text_navigate_address).setOnClickListener(this);
        findViewById(R.id.text_navigate_email).setOnClickListener(this);
        findViewById(R.id.text_navigate_phone).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mSalon = (Salon) getIntent().getSerializableExtra(EXTRA_SALON_KEY);
        mPresenter = new DetailPresenter();
        mPresenter.setView(this);
        mPresenter.checkSalonExist(mSalon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_navigate_address:
                Uri gmmIntentUri = Uri.parse(URI_MAP + mSalon.getSalonAddress());
                Intent intentMap = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                intentMap.setPackage(PACKAGE_MAP);
                if (intentMap.resolveActivity(getPackageManager()) == null) {
                    Toast.makeText(this, getResources().getString(R.string.error_suitable_app),
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                startActivity(intentMap);
                break;
            case R.id.text_navigate_email:
                Intent intentEmail = new Intent(Intent.ACTION_SENDTO);
                intentEmail.setData(Uri.parse(URI_EMAIL));
                String[] adrress = {mSalon.getSalonEmail()};
                intentEmail.putExtra(Intent.EXTRA_EMAIL, adrress);
                if (intentEmail.resolveActivity(getPackageManager()) == null) {
                    Toast.makeText(this, getResources().getString(R.string.error_suitable_app),
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                startActivity(intentEmail);
                break;
            case R.id.text_navigate_phone:
                Intent intentPhone = new Intent(Intent.ACTION_DIAL);
                intentPhone.setData(Uri.parse(URI_PHONE + mSalon.getSalonPhone()));
                if (intentPhone.resolveActivity(getPackageManager()) == null) {
                    Toast.makeText(this, getResources().getString(R.string.error_suitable_app),
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                startActivity(intentPhone);
                break;
        }
    }

    @Override
    public void onSalonExist() {
        Glide.with(this).load(mSalon.getImageUrl()).into(mImageView);
        mTextSalonDescription.setText(mSalon.getSalonDescription());
        mTextSalonPhone.setText(mSalon.getSalonPhone());
        mTextSalonEmail.setText(mSalon.getSalonEmail());
        mTextSalonOpen.setText(mSalon.getSalonOpenTime());
        mTextSalonClose.setText(mSalon.getSalonCloseTime());
        mTextSalonName.setText(mSalon.getSalonName());
        mTextSalonViews.setText(mSalon.getSalonView() + getResources().getString(R.string.title_views));
        mTextSalonAddress.setText(mSalon.getSalonAddress());
    }

    @Override
    public void onSalonNoExist() {
        Toast.makeText(this, getResources().getString(R.string.error_when_loading_salon),
                Toast.LENGTH_SHORT).show();
    }
}
