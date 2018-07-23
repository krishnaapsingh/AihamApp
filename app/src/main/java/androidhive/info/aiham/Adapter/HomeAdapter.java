package androidhive.info.aiham.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidhive.info.aiham.Activity.AvailableCard;
import androidhive.info.aiham.ApiClass.ApiUtils;
import androidhive.info.aiham.Model.AvailableCardModel;
import androidhive.info.aiham.R;

/**
 * Created by Trieffects on 01-Sep-17.
 */

public class HomeAdapter  extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private List<AvailableCardModel> moviesList;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, disc,date;
       ImageView image;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            disc = (TextView) view.findViewById(R.id.disc);
            date= (TextView) view.findViewById(R.id.valid_date);
            image=(ImageView) view.findViewById(R.id.image);
        /*    view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, AvailableCard.class));
                }
            });*/

        }
    }


    public HomeAdapter(Context context,List<AvailableCardModel> moviesList) {
        this.moviesList = moviesList;
        mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homeview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       AvailableCardModel movie = moviesList.get(position);
        holder.name.setText(movie.card_name);
        if(!ApiUtils.isEmptyString(movie.validity_end)) {
            holder.date.setText(movie.validity_end);
        }
        Glide.with(mContext).load(ApiUtils.ImageBaseUrl+movie.image_url).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
