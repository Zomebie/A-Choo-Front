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

    /* UIThread U;
     UIHandler u;
     String state;*/
    private SharedPreferences appData;
    private String id;

    private List<LikeProductDTO> likeProductContainer = null;
    List<String> pcodeList;

    // UI 요소
    private ListView listView;
    private ArrayList<ProductDTO> result = null;
    private ListviewAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //u = new UIHandler();

        View rootView = inflater.inflate(R.layout.fragment_price, container, false);

        setView(rootView);
        /*

        state = "Active";
        U = new UIThread();
        U.start();*/

        return rootView;
    }

    private void setView(View rootView) {

        appData = this.getActivity().getSharedPreferences("appData", MODE_PRIVATE); // SharedPreferences 객체 가져오기

        if (appData.contains("login_status")) {

            id = appData.getString("login_id", "defValue"); // 로그인한 아이디 가져오기

        }

        listView = rootView.findViewById(R.id.all_listview);

        LikeProductService();

        if (likeProductContainer != null) { // 관심 상품 존재


            getLikePrductList(this);

        } else {


        }


    }

    private void LikeProductService() { // 관심 상품이 있는 지 확인


        LikeProductService likeProductService = RetrofitInstance.getLikeProductService();
        Call<ProductDTO_info> call = likeProductService.likeProductList(id);

        call.enqueue(new Callback<ProductDTO_info>() {
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

    private void getLikePrductList(PriceFragment productList) {


        ProductService productService = RetrofitInstance.getProductService(); // 레트로핏 객체
        Call<ProductDTO_info> call = productService.getProductList(); // 상품리스트 요청 준비


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

  /*  private class UIThread extends Thread {
        Message msg;
        boolean loop = true;

        public void run() {
            try {
                while (loop) {
                    Thread.sleep(100);

                    if (Thread.interrupted()) { // 인터럽트가 들어오면 루프를 탈출
                        loop = false;
                        break;
                    }

                    msg = u.obtainMessage();
                    msg.arg1 = 1;

                    u.sendMessage(msg);
                }
            } catch (InterruptedException e) {
                // 슬립 상태에서 인터럽트가 들어오면 익셉션 발생
                loop = false;
            }
        }
    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case 1:
                    if (state.equals("DeActive"))   // Fragment가 숨겨진 상태일 때
                        break;
                    // Fragment의 UI를 변경하는 작업을 수행합니다.
            }
        }
    }

    public void onStop() {
        super.onStop();
        state = "DeActive";
        U.interrupt();
    }

    public void onResume(){
        super.onResume();
        state = "Active";
    }
*/

}
