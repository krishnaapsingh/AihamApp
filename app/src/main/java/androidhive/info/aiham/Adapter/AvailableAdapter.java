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
import androidhive.info.aiham.OhterPrinter.PrinterTestDemoAct;
import androidhive.info.aiham.R;

public class AvailableAdapter extends RecyclerView.Adapter<AvailableAdapter.MyViewHolder> {

    private List<AvailableCardModel> cardList;
    Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, disc,valid_date;
        ImageView image;
        RelativeLayout main;

        public MyViewHolder(View view) {
            super(view);
             name = (TextView) view.findViewById(R.id.productname);
             disc = (TextView) view.findViewById(R.id.disc);
            valid_date=(TextView) view.findViewById(R.id.valid_date);
            image=(ImageView) view.findViewById(R.id.image);
            main=(RelativeLayout) view.findViewById(R.id.main);


        }
    }


    public AvailableAdapter(Context context, List<AvailableCardModel> cardList) {
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
       AvailableCardModel movie = cardList.get(position);
        holder.name.setText(movie.card_name);
      //  holder.disc.setText(movie.discount+"%");

        if(!ApiUtils.isEmptyString(movie.sell_amount)) {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            Date newDate = null;
//            try {
//
//                newDate = format.parse(movie.validity_end);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            format = new SimpleDateFormat("dd/MM/yyyy");
//            String date = null;
//            date = format.format(newDate);
           // holder.valid_date.setText(movie.sell_amount);
        }
        Glide.with(mContext).load(ApiUtils.ImageBaseUrl+movie.image_url).into(holder.image);
        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AvailableCard.position=position;
                Intent intent=new Intent(mContext, PrinterTestDemoAct.class);
                intent.putExtra("quantity",cardList.get(position).quantity);
                intent.putExtra("card_id",cardList.get(position).card_id);
                intent.putExtra("image_url",cardList.get(position).image_url);
                intent.putExtra("sno",cardList.get(position).sno);
                intent.putExtra("validity_end",cardList.get(position).validity_end);
                intent.putExtra("card_name",cardList.get(position).card_name);
                intent.putExtra("desc",cardList.get(position).desc);
                intent.putExtra("cat_name",cardList.get(position).cat_name);
                intent.putExtra("pincode",cardList.get(position).pincode);
                intent.putExtra("price",cardList.get(position).sell_amount);
                intent.putExtra("arabic_desc",cardList.get(position).arabic_desc);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
