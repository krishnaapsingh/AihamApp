package androidhive.info.aiham.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import androidhive.info.aiham.ApiClass.ApiUtils;
import androidhive.info.aiham.ApiClass.ForgotPass;
import androidhive.info.aiham.ApiClass.Login;
import androidhive.info.aiham.Interface.ApiInterface;
import androidhive.info.aiham.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgotPassword extends AppCompatActivity {

    String otp;
    AVLoadingIndicatorView avl;
    LinearLayout change_layout;
    RelativeLayout email_layout;
    EditText edtpassword,edtPassword2,edtotp;
    EditText emailId;
    Button submit;
    LinearLayout main;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);
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
        avl=(AVLoadingIndicatorView)findViewById(R.id.avi);
        avl.hide();;
        main=(LinearLayout)findViewById(R.id.main);
        emailId=(EditText)findViewById(R.id.email);
        edtpassword=(EditText)findViewById(R.id.edtpassword);
        edtPassword2=(EditText)findViewById(R.id.edtpassword2);
        edtotp=(EditText)findViewById(R.id.edtotp);
        change_layout=(LinearLayout)findViewById(R.id.change_layout);
        email_layout=(RelativeLayout)findViewById(R.id.email_layout);
        submit=(Button)findViewById(R.id.singin_btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(emailId.getText().toString().trim().equalsIgnoreCase("")){
                        emailId.setError("please enter valid account name");
                    }else if(edtotp.getText().toString().trim().equalsIgnoreCase("")){
                        edtotp.setError("Please enter Old Password");
                    }else if(edtpassword.getText().toString().equalsIgnoreCase("")){
                        edtpassword.setError("Please enter New password");
                    }else if(edtPassword2.getText().toString().equalsIgnoreCase("")){
                        edtPassword2.setError("");
                        edtpassword.setError("password and confirm password should be same");
                    }else if(!edtPassword2.getText().toString().equalsIgnoreCase(edtpassword.getText().toString())){
                        edtPassword2.setError("");
                        edtpassword.setError("password and confirm password should be same");
                    }else {
                        avl.show();
                        loginProcessWithRetrofit(emailId.getText().toString().trim(),edtotp.getText().toString().trim(),edtpassword.getText().toString().trim());
                    }

                }
        });



    }




    private void loginProcessWithRetrofit(String email,String oldpassword, String password){
        final ApiInterface mApiService = ApiUtils.getInterfaceService();
        Call<ForgotPass> mService = mApiService.updatepassword(email,oldpassword,password);
        mService.enqueue(new Callback<ForgotPass>() {
            @Override
            public void onResponse(Call<ForgotPass> call, Response<ForgotPass> response) {
                avl.hide();
                ForgotPass mLoginObject = response.body();
                if(mLoginObject.status==true) {
                    avl.hide();

                    Toast.makeText(ForgotPassword.this, mLoginObject.message, Toast.LENGTH_LONG).show();
                    finish();

                }else {
                    main.setEnabled(false);
                    avl.hide();
                    Toast.makeText(ForgotPassword.this, mLoginObject.message, Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<ForgotPass> call, Throwable t) {
                main.setEnabled(false);
                call.cancel();
                avl.hide();
                Toast.makeText(ForgotPassword.this, "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            ForgotPassword.this.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ForgotPassword.this.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }


}
