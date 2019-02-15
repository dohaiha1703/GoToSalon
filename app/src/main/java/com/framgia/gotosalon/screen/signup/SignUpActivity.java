package com.framgia.gotosalon.screen.signup;

import android.app.ProgressDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.framgia.gotosalon.R;
import com.framgia.gotosalon.data.model.Account;
import com.framgia.gotosalon.data.repository.AuthenicationRepository;
import com.framgia.gotosalon.data.source.remote.AuthenicationRemoteDataSoucre;
import com.framgia.gotosalon.screen.base.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends BaseActivity implements SignUpContract.View, View.OnClickListener {
    private static final String MSG_PLEASE_WAIT = "please wait few second";
    private SignUpContract.Presenter mPresenter;
    private ProgressDialog mDialog;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private EditText mEditTextRePassword;
    private EditText mEditTextUsername;
    private Account mAccount;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void initComponent() {
        findViewById(R.id.button_sign_up).setOnClickListener(this);
        mEditTextUsername = findViewById(R.id.edit_user_name);
        mEditTextRePassword = findViewById(R.id.edit_repassword);
        mEditTextEmail = findViewById(R.id.edit_email);
        mEditTextPassword = findViewById(R.id.edit_password);
        mDialog = new ProgressDialog(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = new SignUpPresenter(AuthenicationRepository
                .getInstance(AuthenicationRemoteDataSoucre.getInstance(FirebaseAuth.getInstance())
                        , null));
        mPresenter.setView(this);
        mAccount = new Account();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, R.string.msg_sign_up_success, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onEmptyFieldEmail() {
        Toast.makeText(this, R.string.error_empty_field_email, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyFieldPassword() {
        Toast.makeText(this, R.string.error_empty_field_password, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyFieldRePassword() {
        Toast.makeText(this, R.string.error_empty_field_re_password, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyFieldUsername() {
        Toast.makeText(this, R.string.error_empty_field_user_name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmailExsist() {
        Toast.makeText(this, R.string.error_email_exsist, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShortPassword() {
        Toast.makeText(this, R.string.error_short_password, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDoNotMatchPassword() {
        Toast.makeText(this, R.string.error_not_match_password, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInvalidEmailForm() {
        Toast.makeText(this, R.string.error_email_form, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorWhenSignUp() {
        Toast.makeText(this, R.string.error_when_sign_up, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        showProgressDialog(mDialog, R.string.title_sign_up);
    }

    @Override
    public void hideProgressDialog() {
        mDialog.hide();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_up:
                mAccount.setEmail(mEditTextEmail.getText().toString());
                mAccount.setPassword(mEditTextPassword.getText().toString());
                mAccount.setRePassword(mEditTextRePassword.getText().toString());
                mAccount.setUsername(mEditTextUsername.getText().toString());
                mPresenter.signUp(mAccount);
                break;
        }
    }
}
