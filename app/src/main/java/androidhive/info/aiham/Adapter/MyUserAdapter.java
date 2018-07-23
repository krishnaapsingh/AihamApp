package androidhive.info.aiham.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidhive.info.aiham.Activity.AvailableCard;
import androidhive.info.aiham.ApiClass.ApiUtils;
import androidhive.info.aiham.Model.AvailableCardModel;
import androidhive.info.aiham.Model.MyUserData;
import androidhive.info.aiham.R;

public class MyUserAdapter extends RecyclerView.Adapter<MyUserAdapter.MyViewHolder> {

    private List<MyUserData> cardList;
    Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView usr_pio;
        TextView name_txt,email_txt;

        public MyViewHolder(View view) {
            super(view);
             usr_pio=(ImageView)view.findViewById(R.id.usr_pic);
            name_txt=(TextView)view.findViewById(R.id.name_txt);
            email_txt=(TextView)view.findViewById(R.id.email_txt);
        }
    }


    public MyUserAdapter(Context context, List<MyUserData> cardList) {
        this.cardList = cardList;
        mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_userview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        MyUserData movie = cardList.get(position);

        if(!ApiUtils.isEmptyString(movie.last_name)){
            holder.name_txt.setText(movie.first_name +" "+ movie.last_name);
        }else {
            holder.name_txt.setText(movie.first_name);
        }
        holder.email_txt.setText(movie.email);
        Glide.with(mContext).load(ApiUtils.ImageBaseUrl+movie.profile_pic).error(R.drawable.profile).into(holder.usr_pio);

    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
