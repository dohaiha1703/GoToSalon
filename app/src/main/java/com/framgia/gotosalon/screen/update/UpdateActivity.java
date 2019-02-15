package com.framgia.gotosalon.screen.update;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.framgia.gotosalon.R;
import com.framgia.gotosalon.data.model.Salon;
import com.framgia.gotosalon.data.repository.SalonRepository;
import com.framgia.gotosalon.data.source.remote.SalonRemoteDataSource;
import com.framgia.gotosalon.screen.base.BaseActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UpdateActivity extends BaseActivity implements View.OnClickListener, UpdateContract.View {
    private static final String EXTRA_USER_KEY = "EXTRA_USER_KEY";
    private static final int ACTION_PICK_REQUEST = 1996;
    private static final String IMAGE_MIME_TYPE = "image/*";
    private EditText mEditTextSalonName;
    private EditText mEditTextSalonAddress;
    private EditText mEditTextSalonPhone;
    private EditText mEditTextSalonEmail;
    private EditText mEditTextSalonDescription;
    private Spinner mSpinnerSalonOpen;
    private Spinner mSpinnerSalonClose;
    private ImageView mImageChoose;
    private Salon mSalon;
    private ProgressDialog mDialog;
    private Uri mUri;
    private UpdateContract.Presenter mPresenter;

    public static Intent getUpdateIntent(Context context, Salon salon) {
        Intent intent = new Intent(context, UpdateActivity.class);
        intent.putExtra(EXTRA_USER_KEY, salon);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_update;
    }

    @Override
    protected void initComponent() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEditTextSalonAddress = findViewById(R.id.edit_salon_address);
        mEditTextSalonName = findViewById(R.id.edit_salon_name);
        mEditTextSalonDescription = findViewById(R.id.edit_salon_description);
        mEditTextSalonEmail = findViewById(R.id.edit_salon_email);
        mEditTextSalonPhone = findViewById(R.id.edit_salon_phone);
        mImageChoose = findViewById(R.id.image_choose);

        mSpinnerSalonOpen = findViewById(R.id.spinner_open);
        mSpinnerSalonOpen.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.spiner_time)));

        mSpinnerSalonClose = findViewById(R.id.spinner_close);
        mSpinnerSalonClose.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.spiner_time)));

        mDialog = new ProgressDialog(this);

        findViewById(R.id.button_update).setOnClickListener(this);
        findViewById(R.id.image_update).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mSalon = (Salon) getIntent().getSerializableExtra(EXTRA_USER_KEY);
        mEditTextSalonPhone.setText(mSalon.getSalonPhone());
        mEditTextSalonEmail.setText(mSalon.getSalonEmail());
        mEditTextSalonDescription.setText(mSalon.getSalonDescription());
        mEditTextSalonName.setText(mSalon.getSalonName());
        mEditTextSalonAddress.setText(mSalon.getSalonAddress());
        Glide.with(this).load(mSalon.getImageUrl()).into(mImageChoose);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        mPresenter = new UpdatePresenter(SalonRepository.getInstance(SalonRemoteDataSource.
                getInstance(databaseReference, storageReference)));
        mPresenter.setView(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_edit:
                Intent intent = new Intent();
                intent.setType(IMAGE_MIME_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, ACTION_PICK_REQUEST);
                break;

            case R.id.button_update:
                mSalon.setSalonName(mEditTextSalonName.getText().toString().trim());
                mSalon.setSalonAddress(mEditTextSalonAddress.getText().toString().trim());
                mSalon.setSalonPhone(mEditTextSalonPhone.getText().toString().trim());
                mSalon.setSalonEmail(mEditTextSalonEmail.getText().toString().trim());
                mSalon.setSalonDescription(mEditTextSalonDescription.getText().toString().trim());
                mSalon.setSalonOpenTime(mSpinnerSalonOpen.getSelectedItem().toString().trim());
                mSalon.setSalonCloseTime(mSpinnerSalonClose.getSelectedItem().toString().trim());
                mPresenter.validateUpdateSalon(mSalon);
                mPresenter.updateSalon(mUri, mSalon.getSalonId(), mSalon);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_PICK_REQUEST &&
                resultCode == RESULT_OK && data != null && data.getData() != null) {
            mUri = data.getData();
            mImageChoose.setImageURI(mUri);
        } else {
            Toast.makeText(this, getResources().getString(R.string.error_pick_image),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpdateProgress() {
        showProgressDialog(mDialog, R.string.msg_please_wait);
    }

    @Override
    public void onUpdateImageSuccess() {
        mDialog.hide();
        Toast.makeText(this, getResources().getString(R.string.msg_update_image_sussces),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateImageFailed() {
        mDialog.hide();
        Toast.makeText(this, getResources().getString(R.string.msg_update_image_sussces),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateSalonSuccess() {
        mDialog.hide();
        Toast.makeText(this, getResources().getString(R.string.msg_update_image_sussces),
                Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onUpdateSalonFailed() {
        mDialog.hide();
        Toast.makeText(this, getResources().getString(R.string.msg_update_image_sussces),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidateSalonName() {
        Toast.makeText(this, getResources().getString(R.string.error_salon_name),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidateSalonAddress() {
        Toast.makeText(this, getResources().getString(R.string.error_salon_address),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidateSalonEmail() {
        Toast.makeText(this, getResources().getString(R.string.error_salon_email),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInvalidEmailForm() {
        Toast.makeText(this, getResources().getString(R.string.error_salon_email_form),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidateSalonPhone() {
        Toast.makeText(this, getResources().getString(R.string.error_salon_phone),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidateSalonTime() {
        Toast.makeText(this, getResources().getString(R.string.error_salon_time),
                Toast.LENGTH_SHORT).show();
    }
}
