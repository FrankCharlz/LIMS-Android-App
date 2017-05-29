package com.mj.lims;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements Callback {

    private EditText edUsername, edPassword;
    private ProgressDialog pdiag;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        edUsername = (EditText) findViewById(R.id.username);
        edPassword = (EditText) findViewById(R.id.password);
    }


    public void login(View view) {
        String username, password;

        username = edUsername.getText().toString();
        password = edPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Snackbar.make(
                    view.getRootView(),
                    "You must fill both fields",
                    Snackbar.LENGTH_LONG
            ).show();
            return;
        }

        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(Constants.URL_LOGIN)
                .post(formBody)
                .build();

        MyApp.getClient().newCall(request).enqueue(this);
        pdiag = new ProgressDialog(this);
        pdiag.setMessage("Logging in");
        pdiag.show();

    }

    @Override
    public void onFailure(Call call, IOException e) {
        loginFailed("Login failed:\n"+e.getLocalizedMessage());
        hideDialog();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        hideDialog();
        String body = response.body().string().trim();
        MyApp.log("response body: "+body);
        if (response.isSuccessful()) {
            //// TODO: 29-May-17 kuna shida hapa
            if (body.length() > 10) {
                body = body.substring(1);
                body = body.substring(0, (body.length() -1));
                MyApp.log("user: "+body);
                User user = MyApp.getGson().fromJson(body, User.class);
                saveUser(user);
                Remember.putBoolean(Constants.SIGNED_IN, true);
                loginSuccess();
            } else {
                MyApp.log("Failed to login, credentials");
                loginFailed("Failed to login:\nMake sure you entered correct credentials");
            }
        } else {
            MyApp.log("Login request not successful");
            loginFailed("Failed to login:\nNetwork error");
        }

    }

    private void loginFailed(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(
                        findViewById(android.R.id.content),
                        message,
                        Snackbar.LENGTH_LONG
                ).show();
            }
        });
    }

    private void loginSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context, MainActivity.class));
                finish();
            }
        });
    }


    private void saveUser(User user) {
        Remember.putInt(Constants.USER_ID, user.id);
        Remember.putInt("role_id", user.role_id);
        Remember.putLong("last_login", System.currentTimeMillis());
        Remember.putString(Constants.USER_FULL_NAME, user.firstname+" "+user.othernames);
        Remember.putString(Constants.USER_PHONE_NUMBER, user.phone);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideDialog();
    }

    private void hideDialog() {
        if (pdiag != null) {
            pdiag.dismiss();
        }
    }
}
