package cn.edu.gdmec.android.testapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NextActivity extends Activity  {
    private Context mContext;
    //、对于一个没有被载入或者想要动态载入的界面，都需要使用LayoutInflater.inflate()来载入；
    private LayoutInflater mLayoutInflater;
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout mGalleryLinearLayout;
    private String[] m_imageURLs=new String[]{
            "http://www.w3school.com.cn/i/eg_mouse.jpg",
            "http://b393.photo.store.qq.com/psb?/313793d8-4fcf-48c9-8593-e47af98f7953/UIAZ8K2IrtjgqW2NrC2m9TKjsUj9O47VBmQmHnyDpb8!/b/dAkcQupoBgAA&bo=SQHcAAAAAAABALM!&rf=viewer_4",
            "http://b396.photo.store.qq.com/psb?/313793d8-4fcf-48c9-8593-e47af98f7953/hW8hD10HQogTjH22yW*kN2tNkcfjschgSWIntRkn09I!/b/dK4WEuxTDQAA&bo=XgHaAAAAAAABAKI!&rf=viewer_4"
   , "http://www.w3school.com.cn/i/eg_mouse.jpg",
            "http://www.w3school.com.cn/i/eg_mouse.jpg",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
       // mGalleryLinearLayout = (LinearLayout) findViewById(R.id.galleryLinearLayout);



        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        initView();
        initData();


    }

    private void initData() {
        View itemView=null;
        ImageView imageView=null;
        for (int i=0;i<m_imageURLs.length;i++){
            itemView=mLayoutInflater.inflate(R.layout.gallery_item,null);
            imageView=itemView.findViewById(R.id.imageView);
            try {
                URL url=new URL(m_imageURLs[i]);
                URLConnection conn;
                conn=url.openConnection();
                conn.connect();
                InputStream in=conn.getInputStream();
                Bitmap bmp= BitmapFactory.decodeStream(in);
                in.close();
                imageView.setImageBitmap(bmp);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mGalleryLinearLayout.addView(itemView);
        }

    }

    private void initView(){
        mContext=this;
        mGalleryLinearLayout=findViewById(R.id.galleryLinearLayout);
//        获得 LayoutInflater 实例
        mLayoutInflater=LayoutInflater.from(mContext);

    }


}
