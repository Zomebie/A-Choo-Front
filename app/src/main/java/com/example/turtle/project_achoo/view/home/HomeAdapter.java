package com.example.turtle.project_achoo.view.home;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.turtle.project_achoo.R;


public class HomeAdapter extends PagerAdapter {

    private int[] images = {R.drawable.ad1, R.drawable.ad2, R.drawable.ad3, R.drawable.potopoto, R.drawable.ad4, R.drawable.ad6, R.drawable.ad5};
    private LayoutInflater inflater;
    private Context context;

    public HomeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==  object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.slider, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.homeImageView);

        imageView.setImageResource(images[position]);
        String text = (position + 1) + "번째 이미지";
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
}