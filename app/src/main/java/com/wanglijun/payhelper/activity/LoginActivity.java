package com.wanglijun.payhelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wanglijun.payhelper.HelperApplication;
import com.wanglijun.payhelper.R;
import com.wanglijun.payhelper.common.PreferenceUtil;
import com.wanglijun.payhelper.entity.Result;
import com.wanglijun.payhelper.entity.User;
import com.wanglijun.payhelper.net.RetrofitUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText userNameEditText;
    private EditText userPwdEditText;
    private Button loginButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("登录");
        setContentView(R.layout.activity_login);
        userNameEditText = findViewById(R.id.et_user_name);
        userPwdEditText = findViewById(R.id.et_user_pwd);
        loginButton = findViewById(R.id.btn_login);
        loginButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            //todo login in server

            String user_name = userNameEditText.getText().toString().trim();
            String user_pass = userPwdEditText.getText().toString().trim();
            if (TextUtils.isEmpty(user_name)) {
                Toast.makeText(HelperApplication.getContext(), "请输入账号", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(user_pass)) {
                Toast.makeText(HelperApplication.getContext(), "请输入密码", Toast.LENGTH_LONG).show();
                return;
            }

            RetrofitUtil.getInstance().userService()
                    .login(user_name, user_pass)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<Result<User>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Result<User> userResult) {
                            if (userResult.getStatus() == 1) {
                                PreferenceUtil.getInstance().userId(userResult.getParams().getId());
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, userResult.getMsg(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
