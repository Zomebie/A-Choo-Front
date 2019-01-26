package com.example.turtle.project_achoo.view.product;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.turtle.project_achoo.function.adapter.ListviewAdapter;
import com.example.turtle.project_achoo.function.model.product.ProductDTO;
import com.example.turtle.project_achoo.function.model.product.ProductDTO_info;
import com.example.turtle.project_achoo.R;
import com.example.turtle.project_achoo.function.service.networkService.ProductService;
import com.example.turtle.project_achoo.function.service.networkService.RetrofitInstance;
import com.example.turtle.project_achoo.view.home.HomeActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AllFragment extends Fragment implements AbsListView.OnScrollListener{

    UIThread U;
    UIHandler u;
    String state;

    private ListView listView;
    private String id;

    private ArrayList<ProductDTO> result = null;
    private ListviewAdapter adapter = null;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {

        u = new UIHandler();

        View rootView = inflater.inflate(R.layout.fragment_all, container, false);

        state = "Active";
        U = new UIThread();
        U.start();

        SharedPreferences appData = this.getActivity().getSharedPreferences("appData", MODE_PRIVATE); // SharedPreferences 객체 가져오기

        if (appData.contains("login_status")) {

            id = appData.getString("login_id", "defValue"); // 로그인한 아이디 가져오기

        }

        listView = rootView.findViewById(R.id.all_listview);

        // 상품 리스트 서버에 요청
        getPrductList(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id2) {

                // 제품 상세 보기 넘어가기 , 조회 수 올리기

                ProductService productService = RetrofitInstance.getProductService();
                Call<Integer> call = productService.countHits(id, result.get(position).getPcode());
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        int result = response.body();
                        if (result == 1) Log.d(HomeActivity.TAG, "카운트 성공");
                        else if (result == 0) Log.d(HomeActivity.TAG, "카운트 실패");
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                        Log.d(HomeActivity.TAG, "요청 실패");

                    }
                });

                Intent intent = new Intent(rootView.getContext(), ProductListDetail.class);

                intent.putExtra("Pcode",result.get(position).getPcode());
                intent.putExtra("Pimg", result.get(position).getPimg());
                intent.putExtra("Pbrand", result.get(position).getPbrand());
                intent.putExtra("Pname", result.get(position).getPname());
                intent.putExtra("Pprice", result.get(position).getPprice());


                startActivity(intent);
            }
        });

        return rootView;
    }

    public void getPrductList(AllFragment productList) {

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
                Toast.makeText(getActivity(), "Data load failed.", Toast.LENGTH_LONG)
                        .show();


            }
        });

    } // ProductList class

    // 리스트 뷰 페이징
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    } // 리스트 뷰 페이징

    private class UIThread extends Thread {
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
}
