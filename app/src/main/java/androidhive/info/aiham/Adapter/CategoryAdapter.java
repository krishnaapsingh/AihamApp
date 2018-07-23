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
import androidhive.info.aiham.Model.CategoryListModel;
import androidhive.info.aiham.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private List<CategoryListModel> cardList;
    Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView productname,valid_date;
        RelativeLayout main,discRelay;

        public MyViewHolder(View view) {
            super(view);

            image=(ImageView) view.findViewById(R.id.image);
            main=(RelativeLayout) view.findViewById(R.id.main);
            productname=(TextView) view.findViewById(R.id.productname);
            valid_date=(TextView) view.findViewById(R.id.valid_date);
            valid_date.setVisibility(View.GONE);
            discRelay=(RelativeLayout) view.findViewById(R.id.discRelay);
            discRelay.setVisibility(View.GONE);
        }
    }


    public CategoryAdapter(Context context, List<CategoryListModel> cardList) {
        this.cardList = cardList;
        mContext=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.available_cardview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        CategoryListModel movie = cardList.get(position);
        holder.productname.setText(movie.name);
       Glide.with(mContext).load(ApiUtils.ImageBaseUrl+movie.cat_image).into(holder.image);
        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AvailableCard.position=0;
                Intent intent=new Intent(mContext, AvailableCard.class);
                intent.putExtra("data",cardList.get(position).id);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

}
