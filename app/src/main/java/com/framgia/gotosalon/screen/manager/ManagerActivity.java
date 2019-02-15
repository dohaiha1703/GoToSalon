package com.framgia.gotosalon.screen.manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.framgia.gotosalon.R;
import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.data.repository.SalonRepository;
import com.framgia.gotosalon.data.source.remote.SalonRemoteDataSource;
import com.framgia.gotosalon.screen.adapter.SalonManageAdapter;
import com.framgia.gotosalon.screen.base.BaseActivity;
import com.framgia.gotosalon.screen.detail.DetailSalonActivity;
import com.framgia.gotosalon.screen.publish.PublishActivity;
import com.framgia.gotosalon.screen.update.UpdateActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ManagerActivity extends BaseActivity implements View.OnClickListener,
        ManagerContract.View, SalonManageAdapter.OnItemClickListener {
    private static final String EXTRA_USER_KEY = "EXTRA_USER_KEY";
    private String mUserId;
    private List<Salon> mSalons;
    private SalonManageAdapter mAdapter;
    private ManagerContract.Presenter mPresenter;
    private ProgressDialog mDialog;
    private AlertDialog.Builder mBuilder;

    public static Intent getManagerIntent(Context context, String userId) {
        Intent intent = new Intent(context, ManagerActivity.class);
        intent.putExtra(EXTRA_USER_KEY, userId);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_manager;
    }

    @Override
    protected void initComponent() {
        findViewById(R.id.float_button_add).setOnClickListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.image_back).setOnClickListener(this);
        findViewById(R.id.image_search).setOnClickListener(this);
        mDialog = new ProgressDialog(this);

        mSalons = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recycler_view_salons);
        recyclerView.setLayoutManager(new LinearLayoutManager(ManagerActivity.this));
        mAdapter = new SalonManageAdapter(ManagerActivity.this, mSalons);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickListener(this);

        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle(getResources().getString(R.string.title_alert_dialog));
        mBuilder.setMessage(getResources().getString(R.string.msg_alert_dialog));
    }

    @Override
    protected void initData() {
        mUserId = getIntent().getStringExtra(EXTRA_USER_KEY);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        mPresenter = new ManagerPresenter(SalonRepository.getInstance(SalonRemoteDataSource.
                getInstance(databaseReference, storageReference)));
        mPresenter.setView(this);
        mPresenter.getSalons(mUserId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.float_button_add:
                startActivity(PublishActivity.getPublishIntent(ManagerActivity.this, mUserId));
                break;
            case R.id.image_back:
                finish();
                break;
            case R.id.image_search:
                break;
        }
    }

    @Override
    public void onItemClick(View itemView, int position) {
        switch (itemView.getId()) {
            case R.id.image_salon:
                startActivity(DetailSalonActivity.
                        getDetailSalonIntent(ManagerActivity.this, mSalons.get(position)));
                break;
            case R.id.image_delete:
                deleteSalon(position);
                break;
            case R.id.image_edit:
                startActivity(UpdateActivity
                        .getUpdateIntent(ManagerActivity.this, mSalons.get(position)));
                break;
        }
    }

    @Override
    public void onGetSalonsSuccess(List<Salon> salons) {
        mAdapter.setData(salons);
        mDialog.hide();
    }

    @Override
    public void onGetSalonFailed() {
        Toast.makeText(this, getResources().getString(R.string.error_when_loading_salon),
                Toast.LENGTH_SHORT).show();
        mDialog.hide();
    }

    @Override
    public void onGetSalonProgress() {
        showProgressDialog(mDialog, R.string.msg_loading_salon);
    }

    @Override
    public void onDeleteSuccess() {
        Toast.makeText(this, getResources().getString(R.string.msg_deleted), Toast.LENGTH_SHORT).show();
    }

    private void deleteSalon(final int position) {
        mBuilder.setPositiveButton(getResources().getString(R.string.title_alert_dialog_pos_button),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StorageReference storageReferenceUrl = FirebaseStorage.getInstance().
                                getReferenceFromUrl(mSalons.get(position).getImageUrl());
                        mPresenter.deleteSalon(storageReferenceUrl, mSalons.get(position));
                    }
                });
        mBuilder.setNegativeButton(getResources().getString(R.string.title_alert_dialog_neg_button),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ManagerActivity.this, getResources().
                                getString(R.string.msg_choose_no), Toast.LENGTH_SHORT).show();
                    }
                });
        mBuilder.show();
    }
}
