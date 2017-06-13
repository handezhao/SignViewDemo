package com.hdz.signview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private CoverView coverView;
    private CoverView coverView2;
    private CoverView coverView3;
    private CoverView coverView4;

    private CoverAdapter<String> adapter;

    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coverView = (CoverView) findViewById(R.id.cover_view);
        coverView2 = (CoverView) findViewById(R.id.cover_view2);
        coverView3 = (CoverView) findViewById(R.id.cover_view3);
        coverView4 = (CoverView) findViewById(R.id.cover_view4);
        initData();

        adapter = new CoverAdapter<String>() {
            @Override
            public void onDisplayImage(Context context, ImageView imageView, String s) {
                Picasso.with(MainActivity.this).load(s).into(imageView);
            }
        };
        coverView.setAdapter(adapter);
        coverView.setData(list);

        coverView2.setAdapter(adapter);
        coverView2.setData(list);

        coverView3.setAdapter(adapter);
        coverView3.setData(list);

        coverView4.setAdapter(adapter);
        coverView4.setData(list);
    }

    private void initData() {
        list = new ArrayList<>();
        list.add("https://pic4.zhimg.com/02685b7a5f2d8cbf74e1fd1ae61d563b_xll.jpg");
        list.add("https://pic4.zhimg.com/fc04224598878080115ba387846eabc3_xll.jpg");
        list.add("https://pic3.zhimg.com/d1750bd47b514ad62af9497bbe5bb17e_xll.jpg");
        list.add("https://pic4.zhimg.com/da52c865cb6a472c3624a78490d9a3b7_xll.jpg");
        list.add("https://pic3.zhimg.com/0c149770fc2e16f4a89e6fc479272946_xll.jpg");
        list.add("https://pic1.zhimg.com/76903410e4831571e19a10f39717988c_xll.png");
        list.add("https://pic3.zhimg.com/33c6cf59163b3f17ca0c091a5c0d9272_xll.jpg");
        list.add("https://pic4.zhimg.com/52e093cbf96fd0d027136baf9b5cdcb3_xll.png");
        list.add("https://pic3.zhimg.com/f6dc1c1cecd7ba8f4c61c7c31847773e_xll.jpg");
    }
}
