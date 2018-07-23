package androidhive.info.aiham.Activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import androidhive.info.aiham.Adapter.MyUserAdapter;
import androidhive.info.aiham.ApiClass.ApiUtils;
import androidhive.info.aiham.Interface.ApiInterface;
import androidhive.info.aiham.Model.AvailableCardModel;
import androidhive.info.aiham.Model.GetMyUserModel;
import androidhive.info.aiham.Model.PreferencesUtils;
import androidhive.info.aiham.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyUserActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;
    List<AvailableCardModel> rowListItem=new ArrayList<>();
    AVLoadingIndicatorView avl;
    ShimmerRecyclerView rView;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("User List");
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Drawable drawable=toolbar.getNavigationIcon();
        drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.DST_IN);

        avl=(AVLoadingIndicatorView)findViewById(R.id.avi);
        rView = (ShimmerRecyclerView)findViewById(R.id.recycler_view);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.showShimmerAdapter();
        myUserGet(PreferencesUtils.getUser_id());
    }

    public void myUserGet(String u_id){
        final ApiInterface mApiService= ApiUtils.getInterfaceService();
        Call<GetMyUserModel> mService=mApiService.getMyUser(u_id);
        mService.enqueue(new Callback<GetMyUserModel>() {
            @Override
            public void onResponse(Call<GetMyUserModel> call, Response<GetMyUserModel> response) {
                GetMyUserModel model=response.body();
                if(model.status){
                    MyUserAdapter adapter=new MyUserAdapter(MyUserActivity.this,model.data);
                    rView.setAdapter(adapter);
                }else {
                    Toast.makeText(MyUserActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }
                avl.hide();
                rView.hideShimmerAdapter();
            }

            @Override
            public void onFailure(Call<GetMyUserModel> call, Throwable t) {
                avl.hide();
                Toast.makeText(MyUserActivity.this,"Please check your internet connection",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
