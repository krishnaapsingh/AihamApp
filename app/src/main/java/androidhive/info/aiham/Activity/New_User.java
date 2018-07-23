package androidhive.info.aiham.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wang.avi.AVLoadingIndicatorView;

import androidhive.info.aiham.Adapter.HomeAdapter;
import androidhive.info.aiham.ApiClass.ApiUtils;
import androidhive.info.aiham.ApiClass.SignUp;
import androidhive.info.aiham.ApiClass.UserData;
import androidhive.info.aiham.Interface.ApiInterface;
import androidhive.info.aiham.Model.BalanceModel;
import androidhive.info.aiham.Model.PreferencesUtils;
import androidhive.info.aiham.R;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class New_User extends AppCompatActivity{
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    EditText userName,edtEmail,edtpassword,edtPassword2;
    RadioButton onlineRadio,offlineRadio;
    LinearLayout main;
    String workType="2";
   TextView available_bal;
    AVLoadingIndicatorView avi;
    TelephonyManager mTelephonyManager;
    LinearLayout rel1,rel2;
    CircleImageView usrimg;
    LinearLayout balancell;
    ActionBar actionBar;
    String check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Change Password");
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Drawable drawable=toolbar.getNavigationIcon();
        drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.DST_IN);
        avi=(AVLoadingIndicatorView)findViewById(R.id.avi);
        avi.hide();
        available_bal=(TextView) findViewById(R.id.available_bal);
        balancell=findViewById(R.id.balancell);
        rel1=(LinearLayout)findViewById(R.id.rel1);
        rel2=(LinearLayout)findViewById(R.id.rel2);
        main=(LinearLayout)findViewById(R.id.main);
        usrimg=findViewById(R.id.usrimg);
        userName=(EditText)findViewById(R.id.username);

        edtEmail=(EditText)findViewById(R.id.edtemail);
        edtpassword=(EditText)findViewById(R.id.password);
        edtPassword2=(EditText)findViewById(R.id.conf_password);
        onlineRadio=(RadioButton)findViewById(R.id.onlinebtn);
        offlineRadio=(RadioButton)findViewById(R.id.offlinebtn);

        if(!ApiUtils.isEmptyString(getIntent().getStringExtra("data"))){
            check=getIntent().getStringExtra("data");
            userName.setText(PreferencesUtils.getName());
            edtEmail.setText(PreferencesUtils.getEmail());
            available_bal.setText(PreferencesUtils.getAvalibleBalance());
            edtEmail.setEnabled(false);
            rel1.setVisibility(View.GONE);
            rel2.setVisibility(View.GONE);
            if(!ApiUtils.isEmptyString(PreferencesUtils.getImageUrl())){
                Glide.with(this).load(ApiUtils.ImageBaseUrl+PreferencesUtils.getImageUrl()).error(R.drawable.logon).into(usrimg);
            }

            if(PreferencesUtils.getWorkMode().equalsIgnoreCase("1")){
                onlineRadio.setChecked(true);
                offlineRadio.setChecked(false);
                avi.show();
                getgurrentBalance(PreferencesUtils.getAvalibleBalance());
            }else {
                onlineRadio.setChecked(false);
                offlineRadio.setChecked(true);
                balancell.setVisibility(View.GONE);
            }

        }
        onlineRadio.setClickable(false);
        onlineRadio.setClickable(false);
    }


//    public int validated(){
//        if(userName.getText().toString().equalsIgnoreCase("")&&edtEmail.getText().toString().equalsIgnoreCase("")&&edtPassword2.getText().toString().equalsIgnoreCase("")&&edtpassword.getText().toString().equalsIgnoreCase("")){
//            Toast.makeText(New_User.this,"All Field are mandatory",Toast.LENGTH_SHORT).show();
//        }else if(userName.getText().toString().equalsIgnoreCase("")){
//            userName.setError("Please enter your name");
//        }else if(edtEmail.getText().toString().equalsIgnoreCase("")||!ApiUtils.isEmailValid(edtEmail.getText().toString().trim())){
//            edtEmail.setError("Please enter your email id");
//        }else if(edtpassword.getText().toString().equalsIgnoreCase("")&&ApiUtils.isEmptyString(check)){
//            edtpassword.setError("Please enter password");
//        }else if(edtPassword2.getText().toString().equalsIgnoreCase("")&&ApiUtils.isEmptyString(check)){
//            edtPassword2.setError("");
//            edtpassword.setError("password and confirm password should be same");
//        }else if(!edtPassword2.getText().toString().equalsIgnoreCase(edtpassword.getText().toString())&&ApiUtils.isEmptyString(check)){
//            edtPassword2.setError("");
//            edtpassword.setError("password and confirm password should be same");
//        }else if(workType.equalsIgnoreCase("2")){
//            Toast.makeText(New_User.this,"Please select work type",Toast.LENGTH_SHORT).show();
//        }else {
//            return 1;
//        }
//        return -1;
//    }
//    private void registrationProcessWithRetrofit(String name,String imei,final String email, String password,final String type){
//        ApiInterface mApiService = ApiUtils.getInterfaceService();
//        Call<SignUp> mService = mApiService.registration(name,imei,email, password,type);
//        mService.enqueue(new Callback<SignUp>() {
//            @Override
//            public void onResponse(Call<SignUp> call, Response<SignUp> response) {
//                avi.hide();
//                SignUp mLoginObject = response.body();
//                if(mLoginObject.status==true){
//                    UserData userData=mLoginObject.data;
//                    PreferencesUtils.setName(userData.first_name);
//                    PreferencesUtils.setEmail(userData.email);
//                    PreferencesUtils.setUserId(userData.id);
//              //      PreferencesUtils.setimei(userData.imei);
//                 //   PreferencesUtils.setWorkMode(type);
//
//                    Intent in=new Intent(New_User.this, HomeScreen.class);
//                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(in);
//                    New_User.this.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//                    finish();
//                }else {
//                    main.setEnabled(true);
//                    Toast.makeText(New_User.this,"User Already Exits ",Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<SignUp> call, Throwable t) {
//                avi.hide();
//                main.setEnabled(true);
//                call.cancel();
//            }
//        });
//    }

    public void getgurrentBalance(String usrid){
        final ApiInterface mApiServices=ApiUtils.getInterfaceService();
        Call<BalanceModel> mService=mApiServices.getBalance(usrid);
        mService.enqueue(new Callback<BalanceModel>() {
            @Override
            public void onResponse(Call<BalanceModel> call, Response<BalanceModel> response) {
                BalanceModel object=response.body();
                if(object.status){
                    PreferencesUtils.setAvalibleBalance(object.data.get(0).balance);
                    available_bal.setText(object.data.get(0).balance);
                }
                avi.hide();
            }

            @Override
            public void onFailure(Call<BalanceModel> call, Throwable t) {
                avi.hide();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            New_User.this.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        New_User.this.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}
