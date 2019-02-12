package com.example.turtle.project_achoo.view.selfTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turtle.project_achoo.R;
public class Test7 extends AppCompatActivity {

    private String imgtext;
    int w , c, sW, aW, sC, wC;
    TextView next, previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test7);

        final Intent intent = getIntent();
        final int warm = getIntent().getIntExtra("warm", 1);
        final int cool = getIntent().getIntExtra("cool", 1);
        final int sw = getIntent().getIntExtra("sw", 1);
        final int aw = getIntent().getIntExtra("aw", 1);
        final int sc = getIntent().getIntExtra("sc", 1);
        final int wc = getIntent().getIntExtra("wc", 1);

        Log.i("test", warm + "입니다.");
        Log.i("test", cool + "입니다.");
        Log.i("test", sw + "입니다.");
        Log.i("test", aw + "입니다.");
        Log.i("test", sc + "입니다.");
        Log.i("test", wc + "입니다.");

        w = warm;
        c = cool;
        sW = sw;
        aW = aw;
        sC = sc;
        wC = wc;

        previous = (TextView) findViewById(R.id.preview);

        next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"체크해주세요",Toast.LENGTH_LONG).show();
            }
        });
        final RadioGroup rg6 = (RadioGroup) findViewById(R.id.radioGroup7);
        //７. 당신의 이미지를 골라주세요
        rg6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);

                if(rb.isChecked()){
                    if (rb.getText().equals("어려보이고 친밀한 느낌 / 생기있으며 발랄한 느낌")) { // 봄웜톤
                        imgtext = "sw";
                        w++;
                        sW++;
                    } else if (rb.getText().equals("부드러우면서 여성스러운느낌 / 우아하면서 산뜻한 느낌")) { // 여름쿨톤
                        imgtext = "sc";
                        c++;
                        sC++;
                    } else if (rb.getText().equals("성숙하고 차분한 느낌 / 신뢰감을 주는 느낌")) { // 가을웜톤
                        imgtext = "aw";
                        w++;
                        aW++;
                    } else if (rb.getText().equals("존재감이 있는 카리스마 / 도시적이며 샤프한 느낌")) { // 겨울쿨톤
                        imgtext = "wc";
                        c++;
                        wC++;
                    }
                }
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Test8.class);
                        intent.putExtra("warm", w);
                        intent.putExtra("cool", c);
                        intent.putExtra("sw", sW);
                        intent.putExtra("aw", aW);
                        intent.putExtra("sc", sC);
                        intent.putExtra("wc", wC);
                        intent.putExtra("imgtext", imgtext);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public void onclick(View view){

        Intent intent = null;

        switch (view.getId()){

            case R.id.preview : intent = new Intent(this, Test6.class);
                startActivity(intent);
                break;
            case R.id.next :
                Toast.makeText(getApplicationContext(),"체크해주세요,",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
