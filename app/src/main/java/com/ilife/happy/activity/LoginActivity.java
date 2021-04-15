package com.ilife.happy.activity;

import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ilife.common.base.BaseSimpleActivity;
import com.ilife.common.utils.KeyboardUtils;
import com.ilife.happy.R;
import com.ilife.happy.manager.AccountManager;
import com.ilife.happy.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseSimpleActivity {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.register_account_layout)
    LinearLayout registerAccountLayout;
    @BindView(R.id.phone_num)
    EditText phoneNumEdt;
    @BindView(R.id.verify_code)
    EditText verifyCodeEdt;
    @BindView(R.id.bt_verify_code)
    Button verifyCodeBtn;
    @BindView(R.id.input_pwd)
    EditText inputPwd;
    @BindView(R.id.register_new_account)
    Button registerAccountBtn;

    @BindView(R.id.account_login)
    LinearLayout accountLogin;
    @BindView(R.id.et_name)
    EditText nameEdt;
    @BindView(R.id.et_pwd)
    EditText pwdEdt;
    @BindView(R.id.bt_account_login)
    Button accountLoginBtn;

    private String phoneNum;
    private String verifyCode;
    private String password;

    @OnClick({R.id.forget_pwd, R.id.bt_account_login, R.id.switch_register_account, R.id.bt_verify_code, R.id.register_new_account, R.id.switch_account_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget_pwd:
                //todo
                break;
            case R.id.bt_account_login:
                Log.d(TAG, "onClick: bt_account_login is clicked!");
                phoneNum = nameEdt.getText().toString().replace(" ", "");
                verifyCode = null;
                password = pwdEdt.getText().toString();
                Log.d(TAG, "onClick: register_new_account is clicked! phoneNum = " + phoneNum + ", verifyCode = " + verifyCode + ", password = " + password);
                if (!Utils.checkPhoneNumber(phoneNum)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.phone_error), Toast.LENGTH_SHORT).show();
                } else if (!Utils.checkVerifyCode(password)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.new_pwd_error), Toast.LENGTH_SHORT).show();
                } else {
                    AccountManager.getInstance().initUserDao(this);
                    if (AccountManager.getInstance().loginAccount(phoneNum, password)) {
                        Toast.makeText(LoginActivity.this, getString(R.string.login_pwd_success), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.login_pwd_failed), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.bt_verify_code:
                //todo
                break;
            case R.id.register_new_account:
                phoneNum = phoneNumEdt.getText().toString().replace(" ", "");
                verifyCode = verifyCodeEdt.getText().toString();
                password = inputPwd.getText().toString();
                Log.d(TAG, "onClick: register_new_account is clicked! phoneNum = " + phoneNum + ", verifyCode = " + verifyCode + ", newPwd = " + password);
                if (!Utils.checkPhoneNumber(phoneNum)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.phone_error), Toast.LENGTH_SHORT).show();
                } else if (!Utils.checkVerifyCode(verifyCode)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.verify_code_error), Toast.LENGTH_SHORT).show();
                } else if (!Utils.checkVerifyCode(password)) {
                    Toast.makeText(LoginActivity.this, getString(R.string.new_pwd_error), Toast.LENGTH_SHORT).show();
                } else {
                    AccountManager.getInstance().initUserDao(this);
                    if (AccountManager.getInstance().registerAccount(phoneNum, verifyCode, password)) {
                        Toast.makeText(LoginActivity.this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.register_failed), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.switch_register_account:
                accountLogin.setVisibility(View.GONE);
                registerAccountLayout.setVisibility(View.VISIBLE);
                phoneNum = null;
                verifyCode = null;
                password = null;
                break;
            case R.id.switch_account_login:
                accountLogin.setVisibility(View.VISIBLE);
                registerAccountLayout.setVisibility(View.GONE);
                phoneNum = null;
                verifyCode = null;
                password = null;
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        setListener(phoneNumEdt, verifyCodeEdt, inputPwd, registerAccountBtn, verifyCodeBtn);
        setListener(nameEdt, null, pwdEdt, accountLoginBtn, null);
    }

    @Override
    protected void initData() {

    }

    private void setListener(EditText phoneEdt, EditText verifyEdt, EditText pwdEdt, Button nextBtn, Button verifyCodeBtn) {
        phoneEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence == null || charSequence.length() == 0) return;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < charSequence.length(); i++) {
                    if (i != 3 && i != 8 && charSequence.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(charSequence.charAt(i));
                        if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(charSequence.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    phoneEdt.setText(sb.toString());
                    phoneEdt.setSelection(index);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 13) {
                    if (verifyCodeBtn != null) {
                        verifyCodeBtn.setEnabled(true);
                    }
                    if (verifyEdt != null) {
                        verifyEdt.requestFocus();
                    } else {
                        pwdEdt.requestFocus();
                    }
                }
                if (s.length() > 13) {
                    ((SpannableStringBuilder) s).delete(13, s.length());
                    if (verifyCodeBtn != null) {
                        verifyCodeBtn.setEnabled(true);
                    }
                    if (verifyEdt != null) {
                        verifyEdt.requestFocus();
                    } else {
                        pwdEdt.requestFocus();
                    }
                }

                if (!TextUtils.isEmpty(s.toString()) && !TextUtils.isEmpty(pwdEdt.getText())) {
                    if (verifyCodeBtn != null) {
                        verifyCodeBtn.setEnabled(true);
                    }
                    nextBtn.setEnabled(true);
                    nextBtn.setSelected(true);
                }
            }
        });
        if (verifyEdt != null) {
            verifyEdt.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (s.length() == 6) {
                        pwdEdt.requestFocus();
                    }

                    if (s.length() > 6) {
                        ((SpannableStringBuilder) s).delete(6, s.length());
                        pwdEdt.requestFocus();
                    }
                }
            });
        }

        pwdEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) {
                    KeyboardUtils.hideKeyboard(LoginActivity.this, pwdEdt);
                }
                if (s.length() > 6) {
                    ((SpannableStringBuilder) s).delete(6, s.length());
                    KeyboardUtils.hideKeyboard(LoginActivity.this, pwdEdt);
                }

                if (!TextUtils.isEmpty(s.toString()) && !TextUtils.isEmpty(phoneEdt.getText())) {
                    nextBtn.setEnabled(true);
                    nextBtn.setSelected(true);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //要去掉这句，否则会结束当前Activity，无法起到屏蔽的作用
        super.onBackPressed();
        //屏蔽掉返回按键
        Log.d(TAG, "onBackPressed:");
    }
}
