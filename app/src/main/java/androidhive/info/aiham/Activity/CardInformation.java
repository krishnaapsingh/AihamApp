//package androidhive.info.aiham.Activity;
//
//import android.Manifest;
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothSocket;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.PorterDuff;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.graphics.drawable.LayerDrawable;
//import android.os.Build;
//import android.os.Environment;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.wang.avi.AVLoadingIndicatorView;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//
//import androidhive.info.aiham.ApiClass.ApiUtils;
//import androidhive.info.aiham.ApiClass.ForgotPass;
//import androidhive.info.aiham.ApiClass.Printcard;
//import androidhive.info.aiham.Fragment.LocationTrack;
//import androidhive.info.aiham.Interface.ApiInterface;
//import androidhive.info.aiham.Model.PreferencesUtils;
//import androidhive.info.aiham.OhterPrinter.PrinterTestDemoAct;
//import androidhive.info.aiham.PrinterESC.Bluetooth.BluetoothUtil;
//import androidhive.info.aiham.PrinterESC.Bluetooth.ESCUtil;
//import androidhive.info.aiham.R;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
//import static android.Manifest.permission.ACCESS_FINE_LOCATION;
//
//public class CardInformation extends AppCompatActivity implements View.OnClickListener{
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_card_information);
//
//
//    }
//
//    //cat_name, "Order No."+sno_txt,"Pickup time:2015-02-13 12:51:59"
//
//
//
//
//
//
//    public byte[] getBytesFromBitmap(Bitmap bitmap) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
//        return stream.toByteArray();
//    }
//  public  byte[] generateMockData(String catname,String sirno,String date,String cardname,String exparedate,String totalqty,String printqty,String des,String pincode,byte[] img) {
//        try {
//            byte[] next2Line = ESCUtil.nextLine(2);
//            byte[] title = catname.getBytes("gb2312");
//
//            next2Line = ESCUtil.nextLine(2);
//            byte[] boldOn = ESCUtil.boldOn();
//            byte[] fontSize2Big = ESCUtil.fontSizeSetBig(1);
//            byte[] center = ESCUtil.alignCenter();
//            byte[] Serialtxt = "Serial number".getBytes("gb2312");
//            byte[] boldOff = ESCUtil.boldOff();
//            byte[]  next1Line = ESCUtil.nextLine(1);
//            center = ESCUtil.alignCenter();
//            byte[] fontSize2Small = ESCUtil.fontSizeSetSmall(3);
//            byte[] Serialno = sirno.getBytes("gb2312");
//
//
//
//            next2Line = ESCUtil.nextLine(2);
//            boldOn = ESCUtil.boldOn();
//            fontSize2Big = ESCUtil.fontSizeSetBig(1);
//            center = ESCUtil.alignCenter();
//            byte[] orderSerinum ="Print Date".getBytes("gb2312");
//            boldOff = ESCUtil.boldOff();
//            next1Line = ESCUtil.nextLine(1);
//            center = ESCUtil.alignCenter();
//            byte[]   fontSize3Small = ESCUtil.fontSizeSetSmall(3);
//            byte[]  dateb = date.getBytes("gb2312");
//
//
//
//            next1Line = ESCUtil.nextLine(1);
//            center = ESCUtil.alignCenter();
//            fontSize3Small = ESCUtil.fontSizeSetSmall(3);
//            byte[]   card_name = cardname.getBytes("gb2312");
//
//            next2Line = ESCUtil.nextLine(2);
//            center = ESCUtil.alignCenter();
//            fontSize3Small = ESCUtil.fontSizeSetSmall(3);
//            byte[]  exp_date = exparedate.getBytes("gb2312");
//
//            next1Line = ESCUtil.nextLine(2);
//            center = ESCUtil.alignCenter();
//            fontSize2Big = ESCUtil.fontSizeSetBig(1);
//            byte[]  pinnotxt = "PinCode".getBytes("gb2312");
//
//            next1Line = ESCUtil.nextLine(1);
//            center = ESCUtil.alignCenter();
//            fontSize2Big = ESCUtil.fontSizeSetBig(1);
//            byte[]  dotedline = "***************************************".getBytes("gb2312");
//
//           next1Line = ESCUtil.nextLine(1);
//            center = ESCUtil.alignCenter();
//           fontSize3Small = ESCUtil.fontSizeSetSmall(3);
//            byte[]  pinno = pincode.getBytes("gb2312");
//
//
//            next2Line = ESCUtil.nextLine(2);
//            byte[] leftaligin = ESCUtil.alignLeft();
//            fontSize3Small = ESCUtil.fontSizeSetSmall(3);
//            byte[] tot_qtytext = "Total quantity".getBytes("gb2312");
//
//
//
//
//            next2Line = ESCUtil.nextLine(2);
//            byte[] rightalign = ESCUtil.alignRight();
//            fontSize3Small = ESCUtil.fontSizeSetSmall(3);
//            byte[] prit_txt = "Print quantity".getBytes("gb2312");
//            boldOn=ESCUtil.boldOn();
//            next1Line = ESCUtil.nextLine(1);
//            leftaligin = ESCUtil.alignLeft();
//            fontSize3Small = ESCUtil.fontSizeSetSmall(3);
//            byte[] tot_qtyno = totalqty.getBytes("gb2312");
//            boldOff=ESCUtil.boldOff();
//
//
//
//            boldOn=ESCUtil.boldOn();
//            next1Line = ESCUtil.nextLine(1);
//            rightalign = ESCUtil.alignRight();
//            fontSize3Small = ESCUtil.fontSizeSetSmall(3);
//            byte[] print_qtyno = printqty.getBytes("gb2312");
//            boldOff=ESCUtil.boldOff();
//
//            boldOn=ESCUtil.boldOn();
//            next2Line = ESCUtil.nextLine(1);
//            leftaligin = ESCUtil.alignLeft();
//            fontSize3Small = ESCUtil.fontSizeSetSmall(3);
//            byte[] des_txt = "Description".getBytes("gb2312");
//            boldOff=ESCUtil.boldOff();
//
//            next1Line = ESCUtil.nextLine(1);
//            rightalign = ESCUtil.alignRight();
//            fontSize3Small = ESCUtil.fontSizeSetSmall(3);
//            byte[]  txt_des = des.getBytes("gb2312");
//
//            byte[] breakPartial = ESCUtil.feedPaperCutPartial();
//
//
//
//
//
//
//            byte[][] cmdBytes = {center,title, next2Line, boldOn,fontSize2Big, center, Serialtxt, boldOff,
//                    next1Line, center, fontSize2Small, Serialno, next2Line, boldOn, fontSize2Big, center, orderSerinum,
//                    boldOff, next1Line, center, fontSize3Small, dateb,/*next2Line,img, */next1Line, center, fontSize3Small, card_name,
//                    next2Line, center, fontSize3Small, exp_date,next2Line,center,fontSize2Big,pinnotxt,next1Line,center,fontSize2Big,dotedline,
//                    next1Line,center,fontSize3Small,pinno,
//                    next2Line, leftaligin, fontSize3Small, tot_qtytext,
//                    rightalign,fontSize3Small,prit_txt,boldOn,next1Line,leftaligin,fontSize3Small,tot_qtyno,boldOff ,boldOn,rightalign
//                    ,fontSize3Small,print_qtyno,boldOff,boldOn,next2Line,leftaligin,fontSize3Small,des_txt,boldOff,
//                    next1Line,rightalign,fontSize3Small,txt_des,breakPartial};
//
//            return ESCUtil.byteMerger(cmdBytes);
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private File saveBitMap(Context context, View drawView){
//        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Handcare");
//        if (!pictureFileDir.exists()) {
//            boolean isDirectoryCreated = pictureFileDir.mkdirs();
//            if(!isDirectoryCreated)
//                Log.i("ATG", "Can't create directory to save the image");
//            return null;
//        }
//        String filename = pictureFileDir.getPath() +File.separator+ System.currentTimeMillis()+".jpg";
//        File pictureFile = new File(filename);
//        Bitmap bitmap =getBitmapFromView(drawView);
//        try {
//            btnPrint.setVisibility(View.VISIBLE);
//            createPdf(bitmap);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            pictureFile.createNewFile();
//            FileOutputStream oStream = new FileOutputStream(pictureFile);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
//            oStream.flush();
//            oStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.i("TAG", "There was an issue saving the image.");
//        }
//        // scanGallery( context,pictureFile.getAbsolutePath());
//        return pictureFile;
//    }
//
//    private Bitmap getBitmapFromView(View view) {
//        //Define a bitmap with the same size as the view
//        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
//        //Bind a canvas to it
//        Canvas canvas = new Canvas(returnedBitmap);
//        //Get the view's background
//        Drawable bgDrawable =view.getBackground();
//        if (bgDrawable!=null) {
//            //has background drawable, then draw it on the canvas
//            bgDrawable.draw(canvas);
//        }   else{
//            //does not have background drawable, then draw white background on the canvas
//            canvas.drawColor(Color.WHITE);
//        }
//        // draw the view on the canvas
//        view.draw(canvas);
//        //return the bitmap
//        return returnedBitmap;
//    }
//    private void createPdf(Bitmap bitmap) throws IOException, DocumentException {
//        int indentation = 0;
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        Document document = new Document(PageSize.A4);
//        Image signature;
//        signature = Image.getInstance(stream.toByteArray());
//        signature.setAbsolutePosition(5f, 100f);
//        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
//                - document.rightMargin() - indentation) / signature.getWidth()) * 120;
//        signature.scalePercent(scaler);
//        //Image image = Image.getInstance(byteArray);
//        Date date = new Date() ;
//        String timeStamp1 = new SimpleDateFormat("yyyyMMdd").format(date);
//        File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_DCIM),"S4S"+timeStamp1);
//        if (!pdfFolder.exists()) {
//            pdfFolder.mkdirs();
//            Log.i("Created", "Pdf Directory created");
//        }
//
//        //Create time stamp
//
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
//
//        File myFile = new File(pdfFolder,timeStamp + ".pdf");
//        PreferencesUtils.setFilePath(myFile);
//        OutputStream output = new FileOutputStream(myFile);
//        //Step 1
//
//
//        //Step 2
//        PdfWriter.getInstance(document, output);
//
//        //Step 3
//        document.open();
//
//        document.add(signature);
//        document.close();
//
//
//    }
//
//
//    private ArrayList findUnAskedPermissions(ArrayList wanted) {
//        ArrayList result = new ArrayList();
//
//        for (String perm : (String[]) wanted.toArray(new String[wanted.size()])) {
//            if (!hasPermission(perm)) {
//                result.add(perm);
//            }
//        }
//
//        return result;
//    }
//    private boolean hasPermission(String permission) {
//        if (canMakeSmores()) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
//            }
//        }
//        return true;
//    }
//
//    private boolean canMakeSmores() {
//        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
//    }
//
//    @TargetApi(Build.VERSION_CODES.M)
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//
//        switch (requestCode) {
//
//            case ALL_PERMISSIONS_RESULT:
//                for (String perms : (String[]) permissionsRejected.toArray(new String[permissionsRejected.size()])) {
//                    if (!hasPermission(perms)) {
//                        permissionsRejected.add(perms);
//                    }
//                }
//
//                if (permissionsRejected.size() > 0) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
//                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
//                                            }
//                                        }
//                                    });
//                            return;
//                        }
//                    }
//
//                }
//
//                break;
//        }
//
//    }
//    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder(CardInformation.this)
//                .setMessage(message)
//                .setPositiveButton("OK", okListener)
//                .setNegativeButton("Cancel", null)
//                .create()
//                .show();
//    }
//
//
//}
