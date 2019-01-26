package com.example.turtle.project_achoo.function.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.turtle.project_achoo.function.background.DownloadImageTask;
import com.example.turtle.project_achoo.function.model.product.ProductDTO;
import com.example.turtle.project_achoo.R;

import java.util.ArrayList;

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

        new DownloadImageTask(product_image).execute(listviewitem.getPimg());

        TextView product_brand = convertView.findViewById(R.id.product_brand);
        product_brand.setText(listviewitem.getPbrand());

        TextView product_name =  convertView.findViewById(R.id.product_name);
        product_name.setText(listviewitem.getPname());


        TextView product_price = convertView.findViewById(R.id.product_price);
        product_price.setText(listviewitem.getPprice());

        return convertView;
    }
}