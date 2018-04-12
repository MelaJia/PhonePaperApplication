package cn.edu.gdmec.android.testapplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
/**
 * Android实现网络图片查看
* 设置网络图片为手机背景图片
 * 1、开通两个权限
 * 2、
*/
public class MainActivity extends Activity implements View.OnClickListener{

    private TextView mtextView1;
    private ImageView mimageView1;
    private EditText meditText1;
    private Button mbutton1;
    private Button mbutton2;
    private Bitmap bm;
   // private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mtextView1 = (TextView) findViewById(R.id.textView1);
        mimageView1 = (ImageView) findViewById(R.id.imageView1);
        meditText1 = (EditText) findViewById(R.id.editText1);

        mbutton1 = (Button) findViewById(R.id.button1);
        mbutton2 = (Button) findViewById(R.id.button2);
        mbutton2.setEnabled(false);

        mbutton1.setOnClickListener(this);
        mbutton2.setOnClickListener(this);

//        mbutton1.setOnClickListener(new Button.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                String path=meditText1.getText().toString();
//                if(path.equals(""))
//                {
//                    showDialog("网址不可为空白!");
//                    Toast.makeText(ActivityMainActivity.this, "AAAA", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//          /* 传入type=1为预览图片 */
//                    setImage(path,1);
//                }
//            }
//        });
//
//        mbutton2.setOnClickListener(new Button.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                try
//                {
//                    String path=meditText1.getText().toString();
//                    if(path.equals(""))
//                    {
//                        showDialog("网址不可为空白!");
//                    }
//                    else
//                    {
//            /* 传入type=2为设置桌面 */
//                        setImage(path,2);
//                    }
//                }
//                catch (Exception e)
//                {
//                    showDialog("读取错误!!");
//                    bm = null;
//                    mimageView1.setImageBitmap(bm);
//                    mbutton2.setEnabled(false);
//                    e.printStackTrace();
//                }
//            }
//        });



        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

    }

  // String path=meditText1.getText().toString();
    @Override
    public void onClick(View view) {
        String path=meditText1.getText().toString();
        switch (view.getId()) {
            //设置预览图片
            case R.id.button1:
               if (path.equals("")){
                showDialog("网址不可为空白");
               }else {
//                   传入type=1位预览图片
                   setImage(path,1);
               }
                break;
            case R.id.button2:
                //将图片设为桌面背景
                if (path.equals("")){
                    showDialog("网址不可为空白");

                }else {
                    //传入type=2位设置桌面
                    setImage(path,2);
                }

        }

    }
    private void showDialog(String mess){
        AlertDialog builder=new AlertDialog.Builder(this).create();
        builder.setTitle("提示");
        builder.setMessage(mess);
        builder.setButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();

    }
    //将图片抓取下来预览并设置为桌面背景图
    private void setImage(String path,int type){
        try {
            URL url=new URL(path);
//            URLConnection返回一个URLConnect对象，他表示到URL所引用的远程对象的连接.
//            openConnection()返回一个URLConnection对象，该对象表示应用程序与URL之间的通信连接，应用程序可以通过URLConnection实例向此URL发送请求，并读取URL引用的资源
            URLConnection conn=url.openConnection();
            conn.connect();
            if (type==1){
                //预览图片
// ①设置URL对象和连接
//
//②设置一下在连接和读取过程中的超时时间防止长时间无响应的等待
//
//③连接成功后根据数据流创建一个Bitmap的位图对象，最后返回Bitmap类型
//
//④通过ImageView显示图片即可
                bm= BitmapFactory.decodeStream(conn.getInputStream());
                mimageView1.setImageBitmap(bm);
                mbutton2.setEnabled(true);
            }else if (type==2){
                //设置手机壁纸 ，使用WallpaperManager的setBitmap(Bitmap bitmap)方法
//                setWallpaper(InputStream data)将InputStream设置为桌面图片
                this.setWallpaper(conn.getInputStream());
                bm=null;
                mimageView1.setImageBitmap(bm);
                mbutton2.setEnabled(false);
                showDialog("桌面背景设置完成！");

            }
        } catch (MalformedURLException e) {
            showDialog("读取错误！网址可能不是图片或者地址错误！");
            bm=null;
            mbutton2.setEnabled(false);
            e.printStackTrace();
        } catch (IOException e) {


        }
    }





}
