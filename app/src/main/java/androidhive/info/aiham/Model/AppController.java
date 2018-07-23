package androidhive.info.aiham.Model;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.LogInterceptor;

import java.util.List;

import androidhive.info.aiham.Activity.SplashActivity;

/**
 * Created by Trieffects on 04-Sep-17.
 */

public class AppController extends Application {
    private static AppController mInstance;
    List<ApplicationInfo> packages;
    PackageManager pm;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        timeHawkInit();
        pm=getPackageManager();
        packages=pm.getInstalledApplications(0);
        ActivityManager mActivityManager=(ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
      for(ApplicationInfo packageInfo:packages){
          if((packageInfo.flags&ApplicationInfo.FLAG_SYSTEM)==0) {
              PreferencesUtils.setEmail("");
              Intent intent=new Intent(this, SplashActivity.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              startActivity(intent);
              continue;
          }
          if((packageInfo.packageName.equalsIgnoreCase("androidhive.info.aiham"))) {
              continue;
              //PreferencesUtils.setEmail("");
          }else {
              Log.d("","another app");
              //PreferencesUtils.setEmail("");
          }
           mActivityManager.killBackgroundProcesses(packageInfo.packageName);
      }
    }


    private void timeHawkInit() {
        long startTime = System.currentTimeMillis();

        Hawk.init(this).setLogInterceptor(new LogInterceptor() {
            @Override
            public void onLog(String message) {
                Log.d("HAWK", message);
            }
        }).build();

        long endTime = System.currentTimeMillis();
        System.out.println("Hawk.init: " + (endTime - startTime) + "ms");
    }

}
