package androidhive.info.aiham.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import androidhive.info.aiham.Adapter.CategoryAdapter;
import androidhive.info.aiham.Adapter.HomeAdapter;
import androidhive.info.aiham.ApiClass.ApiUtils;
import androidhive.info.aiham.ApiClass.CardData;
import androidhive.info.aiham.ApiClass.CategoryGet;
import androidhive.info.aiham.ApiClass.UserData;
import androidhive.info.aiham.Interface.ApiInterface;
import androidhive.info.aiham.Model.AvailableCardModel;
import androidhive.info.aiham.Model.BalanceModel;
import androidhive.info.aiham.Model.CategoryListModel;
import androidhive.info.aiham.Model.PreferencesUtils;
import androidhive.info.aiham.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreen extends AppCompatActivity {

    AVLoadingIndicatorView avl;
    ShimmerRecyclerView rView;
    ActionBar actionBar;
    ArrayList<CategoryListModel> return_likes = new ArrayList<CategoryListModel>();

    DrawerLayout drawerLayout;
    ImageView img;
    TextView usrbal_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        img = (ImageView) findViewById(R.id.img);
        usrbal_txt=findViewById(R.id.usrbal_txt);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        avl = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avl.show();
        rView = (ShimmerRecyclerView)findViewById(R.id.recycler_view);
        rView.setLayoutManager(new GridLayoutManager(this,2));
        rView.hideShimmerAdapter();
        rView.showShimmerAdapter();

        if(PreferencesUtils.getWorkMode().equalsIgnoreCase("1")) {
            getgurrentBalance(PreferencesUtils.getUser_id());
        }else {
            usrbal_txt.setText("");
        }
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                loadCategory();
            }
        }, 100);

    }


    @Override
    protected void onPause() {
        super.onPause();
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void loadCategory(){
        final ApiInterface mApiService= ApiUtils.getInterfaceService();
        Call<CategoryGet> mService=mApiService.getAnswers();
        mService.enqueue(new Callback<CategoryGet>() {
            @Override
            public void onResponse(Call<CategoryGet> call, Response<CategoryGet> response) {
                CategoryGet ob=response.body();
                avl.hide();
                if(ob.status==true){
                    CategoryAdapter adapter=new CategoryAdapter(HomeScreen.this,ob.data);
                    rView.hideShimmerAdapter();
                    rView.setAdapter(adapter);

                }

            }
            @Override
            public void onFailure(Call<CategoryGet> call, Throwable t) {
                avl.hide();
            }
        });
    }

    @Override
    public void onBackPressed() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void getgurrentBalance(String usrid){
        avl.show();
        final ApiInterface mApiServices=ApiUtils.getInterfaceService();
        Call<BalanceModel> mService=mApiServices.getBalance(usrid);
        mService.enqueue(new Callback<BalanceModel>() {
            @Override
            public void onResponse(Call<BalanceModel> call, Response<BalanceModel> response) {
                BalanceModel object=response.body();
                if(object.status){
                    PreferencesUtils.setAvalibleBalance(object.data.get(0).balance);
                    usrbal_txt.setText("Balance: "+object.data.get(0).balance+" IQD");
                }
            }

            @Override
            public void onFailure(Call<BalanceModel> call, Throwable t) {

            }
        });

    }
}
