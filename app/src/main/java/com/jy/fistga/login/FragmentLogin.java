package com.jy.fistga.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jy.fistga.base.BaseFragment;
import com.jy.fistga.R;
import com.jy.fistga.data.source.UserDataRepository;
import com.jy.fistga.data.source.local.UserLocalDataSource;
import com.jy.fistga.data.source.remote.UserRemoteDataSource;
import com.jy.fistga.login.register.FragmentRegister;
import com.jy.fistga.login.register.RegisterPresenter;
import com.jy.fistga.utils.StatusBarManager;

/*
 * created by taofu on 2018/8/30
 **/
public class FragmentLogin extends BaseFragment implements LoginContract.View {

    private LoginContract.Presenter mPresenter;

    private TextView mTvSendCode;

    private EditText mEtvPhoneNumber;
    private EditText mEtvVerificationCode;

    private CheckBox mCbLisence;


    private Button mBtnLoggin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarManager.darkStatusBar(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login ,container,false );

        mTvSendCode = view.findViewById(R.id.login_btn_send_verification_code);

        mEtvPhoneNumber = view.findViewById(R.id.login_etv_phone_number);
        mEtvVerificationCode = view.findViewById(R.id.login_etv_verification_code);

        mCbLisence = view.findViewById(R.id.login_cb_license);


        mBtnLoggin = view.findViewById(R.id.login_btn_login);

        mCbLisence.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBtnLoggin.setEnabled(isChecked);
            }
        });

        mTvSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumber = mEtvPhoneNumber.getText().toString();

                //TODO 需要做手机号校验
                mPresenter.getVerificationCode(phoneNumber);
            }
        });
        mBtnLoggin.setEnabled(true);
        mBtnLoggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = mEtvPhoneNumber.getText().toString();
                String code = mEtvVerificationCode.getText().toString();
                mPresenter.login(phoneNumber, code);
                loginSuccess();

            }
        });
        return view;

    }


    @Override
    protected boolean isNeedAddToBackStack() {
        return false;
    }

    @Override
    public void verificationCodeSuccess() {
        mTvSendCode.setText(R.string.login_verification_code_send_ok);
    }

    @Override
    public void verificationCodeFail() {
        mTvSendCode.setText(R.string.login_verification_code_send_fail);
    }

    @Override
    public void loginSuccess() {

        RegisterPresenter registerPresenter = new RegisterPresenter(UserDataRepository.getInstance(UserRemoteDataSource.getInstance(), UserLocalDataSource.getInstance(getContext())));
        mActivity.addFragment(FragmentRegister.class, registerPresenter, R.id.login_container, null, null);
    }

    @Override
    public void loginFail(String msg) {
        Toast.makeText(getContext(), "登录失败" +msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

        mPresenter = presenter;

        mPresenter.attachView(this);
    }
}
