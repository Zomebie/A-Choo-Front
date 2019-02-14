package com.example.turtle.project_achoo.view.selfTest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turtle.project_achoo.R;
import com.example.turtle.project_achoo.function.service.networkService.MemberService;
import com.example.turtle.project_achoo.function.service.networkService.RetrofitInstance;
import com.example.turtle.project_achoo.view.home.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Test8 extends AppCompatActivity {

    TextView preview, next;
    int w, c, sW, aW, sC, wC;
    //int money1, money2, money3, money4, money5, money6;
    int money = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test8);

        Intent intent = getIntent();
        final int warm = getIntent().getIntExtra("warm", 1);
        final int cool = getIntent().getIntExtra("cool", 1);
        final int sw = getIntent().getIntExtra("sw", 1);
        final int aw = getIntent().getIntExtra("aw", 1);
        final int sc = getIntent().getIntExtra("sc", 1);
        final int wc = getIntent().getIntExtra("wc", 1);
        final String result = getIntent().getStringExtra("imgtext");

        Log.i("test", warm + "입니다.");
        Log.i("test", cool + "입니다.");
        Log.i("test", sw + "입니다.");
        Log.i("test", aw + "입니다.");
        Log.i("test", sc + "입니다.");
        Log.i("test", wc + "입니다.");
        Log.i("test", result + "입니다.");

        w = warm;
        c = cool;
        sW = sw;
        aW = aw;
        sC = sc;
        wC = wc;

        preview = (TextView) findViewById(R.id.preview);
        next = (TextView) findViewById(R.id.next);

        final RadioGroup rg7_5 = (RadioGroup) findViewById(R.id.radioGroup7_5);

        rg7_5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);

                if (rb.isChecked()) {
                    if (rb.getText().equals("0 원 ~ 10,000원 미만")) {
                        money++;
                    } else if (rb.getText().equals("10,000원 ~ 20,000원 미만")) {
                        money++;
                    } else if (rb.getText().equals("20,000 원 ~ 30,000원 미만")) {
                        money++;
                    } else if (rb.getText().equals("30,000 원 ~ 40,000원 미만")) {
                        money++;
                    } else if (rb.getText().equals("40,000 원 ~ 50,000원 미만")) {
                        money++;
                    } else if (rb.getText().equals("50,000원 이상")) {
                        money++;
                    }
                }
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Test9.class);
                        intent.putExtra("warm", w);
                        intent.putExtra("cool", c);
                        intent.putExtra("sw", sW);
                        intent.putExtra("aw", aW);
                        intent.putExtra("sc", sC);
                        intent.putExtra("wc", wC);
                        intent.putExtra("imgtext", result);
                        intent.putExtra("money", money);
//                        intent.putExtra("money1",money1);
//                        intent.putExtra("money2",money2);
//                        intent.putExtra("money3",money3);
//                        intent.putExtra("money4",money4);
//                        intent.putExtra("money5",money5);
//                        intent.putExtra("money6",money6);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public void onclick(View view) {

        Intent intent = null;

        switch (view.getId()) {

            case R.id.preview:
                intent = new Intent(this, Test7.class);
                startActivity(intent);
                break;
            case R.id.next:
                Toast.makeText(getApplicationContext(), "체크해주세요,", Toast.LENGTH_LONG).show();
                break;
        }
    }
}