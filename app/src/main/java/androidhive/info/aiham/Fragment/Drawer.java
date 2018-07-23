package androidhive.info.aiham.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;

import androidhive.info.aiham.Activity.LogIn;
import androidhive.info.aiham.Activity.MyUserActivity;
import androidhive.info.aiham.Activity.New_User;
import androidhive.info.aiham.ApiClass.ApiUtils;
import androidhive.info.aiham.Model.PreferencesUtils;
import androidhive.info.aiham.R;

/**
 * Created by Trieffects on 01-Sep-17.
 */

public class Drawer extends Fragment implements View.OnClickListener{

    RelativeLayout main;
    LinearLayout profile,account,layout,logout;
    TextView usr_name,usr_email;
    ImageView img;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.drawer,container,false);
        main=(RelativeLayout)view.findViewById(R.id.main);
        main.setOnClickListener(this);
        profile=(LinearLayout)view.findViewById(R.id.profile);
        profile.setOnClickListener(this);
       // account=(LinearLayout)view.findViewById(R.id.account);
       // account.setOnClickListener(this);
       // layout=(LinearLayout)view.findViewById(R.id.layout);
        //layout.setOnClickListener(this);
        logout=(LinearLayout)view.findViewById(R.id.logout);
        logout.setOnClickListener(this);
        usr_email =(TextView)view.findViewById(R.id.usr_email);
        usr_name =(TextView)view.findViewById(R.id.usr_name);
        img =(ImageView)view.findViewById(R.id.img);
         usr_email.setText(PreferencesUtils.getEmail());
         usr_name.setText(PreferencesUtils.getName());
         if(!ApiUtils.isEmptyString(PreferencesUtils.getImageUrl())){
             Glide.with(getActivity()).load(ApiUtils.ImageBaseUrl+PreferencesUtils.getImageUrl()).error(R.drawable.profile).into(img);
         }
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main:
                break;
            case R.id.profile:
                Intent intent=new Intent(getActivity(), New_User.class);
                intent.putExtra("data","1");
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
            case R.id.logout:
                Hawk.deleteAll();
                Intent in=new Intent(getActivity(), LogIn.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(in);
                getActivity().overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                getActivity().finish();
                break;

        }
    }
}
