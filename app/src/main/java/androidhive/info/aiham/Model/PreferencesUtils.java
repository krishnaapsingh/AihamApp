package androidhive.info.aiham.Model;
import com.orhanobut.hawk.Hawk;

import java.io.File;

public class PreferencesUtils {
    private static String user_name = "user_name";
    private static String user_id = "user_id";
    private static String EMAIL = "email";
    private static String Mobile = "Mobile";
    private static String MODEData = "modeData";
    private static String FilePath = "FilePath";

    private static String LATEVALUE = "LATEVALUE";
    private static String lONGVALUE = "lONGVALUE";

    private static String ImageUrl = "ImageUrl";


    public  static void setImageUrl(String data){
        Hawk.put(ImageUrl, data);
    }
    public static String getImageUrl() {

        return Hawk.get(ImageUrl, "");
    }

    public  static void setAvalibleBalance(String data){
        Hawk.put("AvalibleBalance", data);
    }
    public static String getAvalibleBalance() {

        return Hawk.get("AvalibleBalance", "");
    }

    public  static void setUserId(String data){
        Hawk.put(user_id, data);
    }
    public static String getUser_id() {

        return Hawk.get(user_id, "");
    }

    public static void setEmail(String data){
        Hawk.put(EMAIL, data);
    }
    public static String getEmail() {
        return Hawk.get(EMAIL, "");
    }

    public static void setName(String data){
        Hawk.put(user_name, data);
    }
    public static String getName() {
        return Hawk.get(user_name, "");
    }

    public static void setMobile(String data)
    {  Hawk.put(Mobile, data);
    }
    public static String getMobile() {
        return Hawk.get(Mobile, "");
    }

    public static void setWorkMode(String data){
        Hawk.put(MODEData, data);
    }
    public static String getWorkMode() {
        return Hawk.get(MODEData, "");
    }

    public static void setFilePath(File data){
        Hawk.put(FilePath, data);
    }
    public static File getFilePath() {
        return Hawk.get(FilePath, null);
    }

    public static void setlatValue(String data){
        Hawk.put(LATEVALUE, data);
    }
    public static String getlatValue() {
        return Hawk.get(LATEVALUE, null);
    }

    public static void setlongValue(String data){
        Hawk.put(lONGVALUE, data);
    }
    public static String getlongValue() {
        return Hawk.get(lONGVALUE, null);
    }

}
