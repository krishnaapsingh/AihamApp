package androidhive.info.aiham.Activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import androidhive.info.aiham.Adapter.AvailableAdapter;
import androidhive.info.aiham.ApiClass.ApiUtils;
import androidhive.info.aiham.ApiClass.CardData;
import androidhive.info.aiham.ApiClass.CategoryGet;
import androidhive.info.aiham.Interface.ApiInterface;
import androidhive.info.aiham.Model.AvailableCardModel;
import androidhive.info.aiham.Model.BalanceModel;
import androidhive.info.aiham.Model.CategoryListModel;
import androidhive.info.aiham.Model.PreferencesUtils;
import androidhive.info.aiham.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailableCard extends AppCompatActivity {
    private RecyclerView.LayoutManager layoutManager;
    List<AvailableCardModel> rowListItem=new ArrayList<>();
    AVLoadingIndicatorView avl;
    ShimmerRecyclerView rView;
    ActionBar actionBar;
    TextView usrbal_txt;
    public static int position;
    public String spinerPosition="-1";
   ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        img=findViewById(R.id.img);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if(PreferencesUtils.getWorkMode().equalsIgnoreCase("1")) {
            toolbar.setTitle(""); //Online user
        }else {
            toolbar.setTitle("Card"); //Offline User
        }
        //one is this
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Drawable drawable=toolbar.getNavigationIcon();
        drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.DST_IN);
        usrbal_txt=findViewById(R.id.usrbal_txt);
         avl=(AVLoadingIndicatorView)findViewById(R.id.avi);
          rView = (ShimmerRecyclerView)findViewById(R.id.recycler_view);
          rView.setLayoutManager(new GridLayoutManager(this,2));
          rView.hideShimmerAdapter();
          rView.showShimmerAdapter();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                AvailableCard.this.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        avl = (AVLoadingIndicatorView) findViewById(R.id.avi);
        usrbal_txt=findViewById(R.id.usrbal_txt);
        if(position>=0) {
            avl.show();
            spinerPosition = getIntent().getStringExtra("data");
            if(PreferencesUtils.getWorkMode().equalsIgnoreCase("1")) {
                getgurrentBalance(PreferencesUtils.getUser_id());
            }else {
                usrbal_txt.setText("Card");
            }
            avl.show();
            AvailableCardFunction(spinerPosition);
        }else {
            avl.hide();
        }

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
                    actionBar.setTitle("Balance :"+object.data.get(0).balance+" IQD"); // second this
                }
                avl.hide();
            }

            @Override
            public void onFailure(Call<BalanceModel> call, Throwable t) {
                avl.hide();
            }
        });

    }

    private void AvailableCardFunction(String catid){
        final ApiInterface mApiService = ApiUtils.getInterfaceService();
        Call<CardData> mService = mApiService.GetCard(PreferencesUtils.getUser_id(),catid);
        mService.enqueue(new Callback<CardData>() {
            @Override
            public void onResponse(Call<CardData> call, Response<CardData> response) {
                avl.hide();
                CardData cardinfo = response.body();
                if(cardinfo.status==true) {
                    rowListItem=cardinfo.data;
                    AvailableAdapter rcAdapter = new AvailableAdapter(AvailableCard.this, rowListItem);
                    rView.hideShimmerAdapter();
                    rView.setAdapter(rcAdapter);

                }else {
                    rowListItem.clear();
                    rView.hideShimmerAdapter();
                    AvailableAdapter rcAdapter = new AvailableAdapter(AvailableCard.this, rowListItem);
                    rView.hideShimmerAdapter();
                    rView.setAdapter(rcAdapter);
                    Toast.makeText(AvailableCard.this, "No card available", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<CardData> call, Throwable t) {
                call.cancel();
                rView.hideShimmerAdapter();
                avl.hide();
                Toast.makeText(AvailableCard.this, "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            AvailableCard.this.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AvailableCard.this.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }


}
