package com.example.turtle.project_achoo.function.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.turtle.project_achoo.function.backgroundTask.DownloadImageTask;
import com.example.turtle.project_achoo.function.model.product.ProductDTO;
import com.example.turtle.project_achoo.R;
import com.example.turtle.project_achoo.function.service.networkService.ProductService;
import com.example.turtle.project_achoo.function.service.networkService.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListviewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<ProductDTO> data;
    private int layout;


    public ListviewAdapter(Context context, int layout, ArrayList<ProductDTO> data) {

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position).getPname();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }
        ProductDTO listviewitem = data.get(position);

        ImageView product_image = convertView.findViewById(R.id.product_image);

        //Async로 제품 이미지 url 요청
        //new DownloadImageTask(product_image).execute(listviewitem.getPimg());

        // Glide lib
        String img_url = "http://172.30.32.111:4000/static/images/" + listviewitem.getPimg();
        Glide.with(inflater.getContext()).load(img_url).into(product_image);


        TextView product_brand = convertView.findViewById(R.id.product_brand);
        product_brand.setText(listviewitem.getPbrand());

        TextView product_name = convertView.findViewById(R.id.product_name);
        product_name.setText(listviewitem.getPname());


        TextView product_price = convertView.findViewById(R.id.product_price);
        product_price.setText(listviewitem.getPprice());

        return convertView;
    }
}