package com.example.turtle.project_achoo.view.recommend;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.turtle.project_achoo.R;
import com.example.turtle.project_achoo.function.adapter.ListviewAdapter;
import com.example.turtle.project_achoo.function.model.product.LikeProductDTO;
import com.example.turtle.project_achoo.function.model.product.LikeProductDTO_info;
import com.example.turtle.project_achoo.function.model.product.ProductDTO;
import com.example.turtle.project_achoo.function.model.product.ProductDTO_info;
import com.example.turtle.project_achoo.function.service.networkService.LikeProductService;
import com.example.turtle.project_achoo.function.service.networkService.ProductService;
import com.example.turtle.project_achoo.function.service.networkService.RetrofitInstance;
import com.example.turtle.project_achoo.view.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class PriceFragment extends Fragment {

    private SharedPreferences appData;
    private String id;

    // UI 요소
    private ListView listView;
    private ArrayList<ProductDTO> result = null;
    private ListviewAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_price, container, false);

        setView(rootView);

        return rootView;
    }

    private void setView(View rootView) {

        appData = this.getActivity().getSharedPreferences("appData", MODE_PRIVATE); // SharedPreferences 객체 가져오기

        if (appData.contains("login_status")) {

            id = appData.getString("login_id", "defValue"); // 로그인한 아이디 가져오기

        }

        listView = rootView.findViewById(R.id.all_listview);

        getPreferPricePrductList(this);


    }


    private void getPreferPricePrductList(PriceFragment productList) {


        ProductService productService = RetrofitInstance.getProductService(); // 레트로핏 객체
        Call<ProductDTO_info> call = productService.getPreferPriceProductList(id); // 상품리스트 요청 준비


        //  네트워크 요청하는 별도의 스레드가 비동기로 실행되고 있다는 점을 고려해야한다.
        call.enqueue(new Callback<ProductDTO_info>() {

            //OnResponse와 OnFailure의 콜백은 메인 스레드에서 돈다.
            @Override
            public void onResponse(Call<ProductDTO_info> call, Response<ProductDTO_info> response) {

                ProductDTO_info productDTO_info = response.body(); // 데이터 받아오기


                if (productDTO_info != null && productDTO_info.getProductDTO() != null) {

                    result = (ArrayList<ProductDTO>) productDTO_info.getProductDTO(); // 데이터 컨테이너에 담기

                    adapter = new ListviewAdapter(getActivity(), R.layout.product, result);
                    listView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<ProductDTO_info> call, Throwable t) {


            }
        });


    }

}
