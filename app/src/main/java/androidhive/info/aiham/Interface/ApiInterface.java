package androidhive.info.aiham.Interface;


import androidhive.info.aiham.ApiClass.CardData;
import androidhive.info.aiham.ApiClass.CategoryGet;
import androidhive.info.aiham.ApiClass.ForgotPass;
import androidhive.info.aiham.ApiClass.Login;
import androidhive.info.aiham.ApiClass.Printcard;
import androidhive.info.aiham.ApiClass.SignUp;
import androidhive.info.aiham.Model.BalanceModel;
import androidhive.info.aiham.Model.GetMyUserModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiInterface {
    @FormUrlEncoded
    @POST("card_project/api/signup")
    Call<SignUp> registration(@Field("name") String username, @Field("imei") String imei, @Field("email") String email, @Field("password") String password, @Field("mode") String type);

    @FormUrlEncoded
    @POST("card_project/api/login")
    Call<Login> authenticate(@Field("acc_name") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("card_project/api/forgetpassword")
    Call<ForgotPass> forgetpassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("card_project/api/changepassword")
    Call<ForgotPass> updatepassword(@Field("accountname") String username,
                                    @Field("oldpassword") String oldpassword,
                                    @Field("password") String password);


    @FormUrlEncoded
    @POST("card_project/api/all_card_by_catid")
    Call<CardData> GetCard(@Field("user_id") String user_id,@Field("cat_id") String catid);

    @FormUrlEncoded
    @POST("card_project/api/usescards")
    Call<CardData> GetCard1(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("card_project/api/print_card")
    Call<Printcard> Printc(@Field("user_id") String userid, @Field("card_id") String productId,@Field("transfer_card_quantity") String transfer_card_quantity);


    @FormUrlEncoded
    @POST("card_project/api/profile_update")
    Call<SignUp> UpdateData(@Field("id") String id,@Field("name") String username, @Field("mode") String type);

    @GET("card_project/api/card_category_list")
    Call<CategoryGet> getAnswers();

    @FormUrlEncoded
    @POST("card_project/api/my_users")
    Call<GetMyUserModel> getMyUser(@Field("user_id") String u_id);

    @FormUrlEncoded
    @POST("card_project/api/user_curr_bal")
    Call<BalanceModel> getBalance(@Field("user_id") String usrid);
}
