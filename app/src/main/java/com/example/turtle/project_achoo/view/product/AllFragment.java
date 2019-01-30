package com.example.turtle.project_achoo.view.product;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
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

public class AllFragment extends Fragment implements AbsListView.OnScrollListener {

    UIThread U;
    UIHandler u;
    String state;

    private SharedPreferences appData;
    private String id;

    private EditText search;
    private ListView listView;
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

        setView(rootView);

        return rootView;
    }

    private void setView(View rootView) {

        appData = this.getActivity().getSharedPreferences("appData", MODE_PRIVATE); // SharedPreferences 객체 가져오기

        if (appData.contains("login_status")) {

            id = appData.getString("login_id", "defValue"); // 로그인한 아이디 가져오기

        }

        search = rootView.findViewById(R.id.all_search_text);
        listView = rootView.findViewById(R.id.all_listview);

        // 상품 리스트 서버에 요청
        getPrductList(this);

        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String text = search.getText().toString();
                //searchFunction(text);
            }
        });


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

                intent.putExtra("Pcode", result.get(position).getPcode());
                intent.putExtra("Pimg", result.get(position).getPimg());
                intent.putExtra("Pbrand", result.get(position).getPbrand());
                intent.putExtra("Pname", result.get(position).getPname());
                intent.putExtra("Pprice", result.get(position).getPprice());


                startActivity(intent);
            }
        });
    }

//    private void searchFunction(String text) {
//
//
//        ArrayList<ProductDTO> container = new ArrayList<>();
//        container.addAll(result);
//
//        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
//        result.clear();
//
//        // 문자 입력이 없을때는 모든 데이터를 보여준다.
//        if (text.length() == 0) {
//
//            result.addAll(container);
//        }
//        // 문자 입력을 할때..
//        else {
//            // 리스트의 모든 데이터를 검색한다.
//            for (int i = 0; i < container.size(); i++) {
//                // arraylist의 모든 데이터에 입력받은 단어(text)가 포함되어 있으면 true를 반환한다.
//                if (container.get(i).toLowerCase().contains(text)) {
//                    // 검색된 데이터를 리스트에 추가한다.
//                    result.add(container.get(i));
//                }
//            }
//        }
//        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
//        adapter.notifyDataSetChanged();
//
//    }

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

    public void onResume() {
        super.onResume();
        state = "Active";
    }
}
