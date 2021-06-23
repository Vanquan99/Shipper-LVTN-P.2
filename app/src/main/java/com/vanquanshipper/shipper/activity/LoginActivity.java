package com.vanquanshipper.shipper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vanquanshipper.shipper.R;
import com.vanquanshipper.shipper.model.LoginUser;
import com.vanquanshipper.shipper.retrofit.APIClient;
import com.vanquanshipper.shipper.retrofit.GetResult;
import com.vanquanshipper.shipper.utils.CustPrograssbar;
import com.vanquanshipper.shipper.utils.SessionManager;
import com.vanquanshipper.shipper.utils.Utiles;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static com.vanquanshipper.shipper.utils.SessionManager.currncy;

public class LoginActivity extends AppCompatActivity implements GetResult.MyListener {

    @BindView(R.id.ed_username)
    TextInputEditText edUsername;
    @BindView(R.id.ed_password)
    TextInputEditText edPassword;
    @BindView(R.id.chk_remember)
    CheckBox chkRemember;
    @BindView(R.id.txt_login)
    TextView txtLogin;
    CustPrograssbar custPrograssbar;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        custPrograssbar = new CustPrograssbar();
        sessionManager = new SessionManager(this);
    }

    @OnClick(R.id.txt_login)
    public void onClick() {
        if (validation()) {
            loginUser();
        }
    }


    private void loginUser() {
        custPrograssbar.PrograssCreate(LoginActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", edUsername.getText().toString());
            jsonObject.put("password", edPassword.getText().toString());
            jsonObject.put("imei", Utiles.getIMEI(LoginActivity.this));
            JsonParser jsonParser = new JsonParser();

            Call<JsonObject> call = APIClient.getInterface().getLogin((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        try {
            custPrograssbar.ClosePrograssBar();
            Gson gson = new Gson();
            LoginUser response = gson.fromJson(result.toString(), LoginUser.class);
            Toast.makeText(LoginActivity.this, "" + response.getResponseMsg(), Toast.LENGTH_LONG).show();
            if (response.getResult().equals("true")) {
                OneSignal.sendTag("rider_id", response.getUser().getId());
                sessionManager.setUserDetails("", response.getUser());
                sessionManager.setStringData(currncy, response.getCurrency());
                if (response.getUser().getStatus().equalsIgnoreCase("1")) {
                    sessionManager.setBooleanData("status", true);

                } else {
                    sessionManager.setBooleanData("status", false);

                }
                if (chkRemember.isChecked()) {
                    sessionManager.setBooleanData("rlogin", true);
                }
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }
        } catch (Exception e) {
            Log.e("error", " --> " + e.toString());
        }
    }

    public boolean validation() {
        if (edUsername.getText().toString().isEmpty()) {
            edUsername.setError("Enter Mobile No");
            return false;
        }
        if (edPassword.getText().toString().isEmpty()) {
            edPassword.setError("Enter Password");
            return false;
        }
        return true;
    }
}
