package androidhive.info.aiham.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.prefs.Preferences;

import androidhive.info.aiham.ApiClass.ApiUtils;
import androidhive.info.aiham.ApiClass.Login;
import androidhive.info.aiham.ApiClass.UserData;
import androidhive.info.aiham.Interface.ApiInterface;
import androidhive.info.aiham.Model.PreferencesUtils;
import androidhive.info.aiham.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LogIn extends AppCompatActivity {

    AVLoadingIndicatorView avl;
    EditText email,password;
    LinearLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        avl=(AVLoadingIndicatorView)findViewById(R.id.avi);
        main=(LinearLayout)findViewById(R.id.main);
        avl.hide();
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
    }

    public void forgotPassword(View view) {
        startActivity(new Intent(LogIn.this,ForgotPassword.class));
        LogIn.this.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    public void SingIn(View view) {
        if(vaildation()==1) {
            main.setEnabled(true);
            avl.show();
            loginProcessWithRetrofit(email.getText().toString().trim(),password.getText().toString().trim());
        }
    }

    public int vaildation(){
        if(email.getText().toString().trim().equalsIgnoreCase("")&&password.getText().toString().trim().equalsIgnoreCase("")){

        }else if(email.getText().toString().trim().equalsIgnoreCase("")){
            email.setError("Please enter email id");
        }else if(password.getText().toString().trim().equalsIgnoreCase("")){
            password.setError("Please enter password");
        }else {
            return 1;
        }
        return -1;
    }


    private void loginProcessWithRetrofit(String email, String password){
        final ApiInterface mApiService = ApiUtils.getInterfaceService();
        Call<Login> mService = mApiService.authenticate(email,password);
        mService.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                avl.hide();
                Login mLoginObject = response.body();
               if(mLoginObject.status==true) {
                   UserData userData=mLoginObject.data;
                   PreferencesUtils.setName(userData.first_name);
                   PreferencesUtils.setEmail(userData.email);
                   PreferencesUtils.setUserId(userData.id);
                   PreferencesUtils.setWorkMode(userData.mode);
                   PreferencesUtils.setMobile(userData.phone);
                   PreferencesUtils.setImageUrl(userData.profile_pic);
                   PreferencesUtils.setAvalibleBalance(userData.balance);
                   main.setEnabled(false);
                   avl.hide();
                   Intent in=new Intent(LogIn.this, HomeScreen.class);
                   in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(in);
                   LogIn.this.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                   finish();
               }else {
                   main.setEnabled(false);
                   avl.hide();
                   Toast.makeText(LogIn.this, "Please check your email and password", Toast.LENGTH_LONG).show();
               }

            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                call.cancel();
                main.setEnabled(false);
                avl.hide();
                Toast.makeText(LogIn.this, "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
            }
        });
    }
}
