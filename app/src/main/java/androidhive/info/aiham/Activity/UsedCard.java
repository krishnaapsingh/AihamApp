package androidhive.info.aiham.Activity;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import androidhive.info.aiham.Adapter.HomeAdapter;
import androidhive.info.aiham.ApiClass.ApiUtils;
import androidhive.info.aiham.ApiClass.CardData;
import androidhive.info.aiham.Interface.ApiInterface;
import androidhive.info.aiham.Model.AvailableCardModel;
import androidhive.info.aiham.Model.PreferencesUtils;
import androidhive.info.aiham.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsedCard extends AppCompatActivity {

    List<AvailableCardModel> rowListItem=new ArrayList<>();
    ImageView back;
    AVLoadingIndicatorView avl;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ShimmerRecyclerView shimmerRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardviewbutton);
        mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeToRefresh);

        shimmerRecycler = (ShimmerRecyclerView) findViewById(R.id.recycler_view);
        avl = (AVLoadingIndicatorView) findViewById(R.id.avi);
        //       mAdapter = new MoviesAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        shimmerRecycler.setLayoutManager(mLayoutManager);
        shimmerRecycler.setItemAnimator(new DefaultItemAnimator());
        HomeAdapter rcAdapter = new HomeAdapter(UsedCard.this, rowListItem);
        shimmerRecycler.setAdapter(rcAdapter);
        shimmerRecycler.showShimmerAdapter();
        shimmerRecycler.showShimmerAdapter();
     loginProcessWithRetrofit(PreferencesUtils.getUser_id());

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shimmerRecycler.showShimmerAdapter();
                shimmerRecycler.showShimmerAdapter();
                loginProcessWithRetrofit(PreferencesUtils.getUser_id());
            }
        });

        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                UsedCard.this.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });
    }
    private void loginProcessWithRetrofit(String userid){
        final ApiInterface mApiService = ApiUtils.getInterfaceService();
        Call<CardData> mService = mApiService.GetCard1(userid);
        mService.enqueue(new Callback<CardData>() {
            @Override
            public void onResponse(Call<CardData> call, Response<CardData> response) {
                avl.hide();
                CardData cardinfo = response.body();
                if(cardinfo.status==true) {
                    avl.hide();
                    mSwipeRefreshLayout.setRefreshing(false);
                    rowListItem=cardinfo.data;
                    HomeAdapter rcAdapter = new HomeAdapter(UsedCard.this, rowListItem);
                    shimmerRecycler.setAdapter(rcAdapter);
                    shimmerRecycler.hideShimmerAdapter();
                }else {
                    avl.hide();
                    shimmerRecycler.hideShimmerAdapter();
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(UsedCard.this, "No card available", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<CardData> call, Throwable t) {
                call.cancel();
                avl.hide();
                Toast.makeText(UsedCard.this, "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
            }
        });
    }


}
