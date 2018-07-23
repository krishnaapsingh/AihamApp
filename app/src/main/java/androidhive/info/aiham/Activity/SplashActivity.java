package androidhive.info.aiham.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import androidhive.info.aiham.Adapter.HomeAdapter;
import androidhive.info.aiham.ApiClass.ApiUtils;
import androidhive.info.aiham.Model.PreferencesUtils;
import androidhive.info.aiham.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
             if(ApiUtils.isEmptyString(PreferencesUtils.getEmail())) {
                    Intent mainIntent = new Intent(SplashActivity.this, LogIn.class);
                    startActivity(mainIntent);
                    finish();
                    SplashActivity.this.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }else {
                    Intent mainIntent = new Intent(SplashActivity.this, HomeScreen.class);
                    startActivity(mainIntent);
                    finish();
                    SplashActivity.this.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }

            }
        }, 3000);
    }
}
