package com.framgia.gotosalon.screen.home;

import android.app.ProgressDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.framgia.gotosalon.R;
import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.data.repository.SalonRepository;
import com.framgia.gotosalon.data.source.remote.SalonRemoteDataSource;
import com.framgia.gotosalon.screen.adapter.SalonAdapter;
import com.framgia.gotosalon.screen.base.BaseFragment;
import com.framgia.gotosalon.screen.detail.DetailSalonActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HotFragment extends BaseFragment implements HomeContract.View {
    private SalonAdapter mAdapter;
    private ProgressDialog mDialog;
    private List<Salon> mSalons;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_hot;
    }

    @Override
    protected void initComponent(View view) {
        mSalons = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_salons);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SalonAdapter(getContext(), mSalons);
        recyclerView.setAdapter(mAdapter);
        mDialog = new ProgressDialog(getContext());
        mAdapter.setOnClickListener(new SalonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                startActivity(DetailSalonActivity.
                        getDetailSalonIntent(getContext(), mSalons.get(position)));
            }
        });
    }

    @Override
    protected void initData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        HomeContract.Presenter presenter = new HomePresenter(SalonRepository.getInstance(
                SalonRemoteDataSource.getInstance(databaseReference, storageReference)));
        presenter.setView(this);
        presenter.getHotSalons();
    }

    @Override
    public void onGetSalonsSucced(List<Salon> salons) {
        mAdapter.setData(salons);
        mDialog.hide();
    }

    @Override
    public void onGetSalonsFailed() {
        mDialog.hide();
    }

    @Override
    public void onGetSalonInProgress() {
        showProgressDialog(mDialog);
    }
}
