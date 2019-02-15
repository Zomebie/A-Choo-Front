package com.example.turtle.project_achoo.view.myPage;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.turtle.project_achoo.R;
import com.example.turtle.project_achoo.function.adapter.ListviewAdapter;
import com.example.turtle.project_achoo.function.model.product.LikeProductDTO;
import com.example.turtle.project_achoo.function.model.product.ProductDTO;
import com.example.turtle.project_achoo.function.model.product.ProductDTO_info;
import com.example.turtle.project_achoo.function.service.networkService.LikeProductService;
import com.example.turtle.project_achoo.function.service.networkService.ProductService;
import com.example.turtle.project_achoo.function.service.networkService.RetrofitInstance;
import com.example.turtle.project_achoo.view.recommend.PriceFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikeProductActivity extends AppCompatActivity {

    private SharedPreferences appData;
    private String id;

    // UI 요소
    private ListView listView;
    private ArrayList<ProductDTO> result = null;
    private ListviewAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_product);

        setView();
    }

    private void setView() {

        appData = getSharedPreferences("appData", MODE_PRIVATE); // SharedPreferences 객체 가져오기

        if (appData.contains("login_status")) {

            id = appData.getString("login_id", "defValue"); // 로그인한 아이디 가져오기

        }

        listView =findViewById(R.id.all_listview);

        LikeProductService();


    }

    private void LikeProductService() { // 관심 상품 가져오기


        LikeProductService likeProductService = RetrofitInstance.getLikeProductService();
        Call<ProductDTO_info> call = likeProductService.likeProductList(id);

        call.enqueue(new Callback<ProductDTO_info>() {
            @Override
            public void onResponse(Call<ProductDTO_info> call, Response<ProductDTO_info> response) {
                ProductDTO_info productDTO_info = response.body(); // 데이터 받아오기


                if (productDTO_info != null && productDTO_info.getProductDTO() != null) {

                    result = (ArrayList<ProductDTO>) productDTO_info.getProductDTO(); // 데이터 컨테이너에 담기

                    adapter = new ListviewAdapter(LikeProductActivity.this, R.layout.product, result);
                    listView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<ProductDTO_info> call, Throwable t) {

            }
        });


    }

}
