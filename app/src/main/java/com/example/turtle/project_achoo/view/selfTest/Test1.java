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

public class Test1 extends AppCompatActivity {

    private int warm = 0; // 웜톤
    private int cool = 0; // 쿨톤
    private int sw = 0; // sw(봄웜톤)
    private int aw = 0; // aw(가을웜톤)
    private int sc = 0; // sc(여름쿨톤)
    private int wc = 0; // wc(겨울쿨톤)
    TextView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        next = (TextView) findViewById(R.id.next);
        final RadioGroup rg1 = (RadioGroup)findViewById(R.id.radioGroup1);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("test","버튼 클릭클릭");
                Toast.makeText(getApplicationContext(),"선택해주세요.",Toast.LENGTH_LONG).show();
            }
        });

        //해당 라디오 그룹에 속한 버튼들의 체크가 변경될때 마다 호출되는 리스너 함수를 생성
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            //얼굴색 피부질문 Group1(rg1)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton)findViewById(checkedId);

               if(rb.isChecked()){
                   if(rb.getText().equals("노란기")){
                       warm++;
                       sw++;
                       aw++;
                   }else if(rb.getText().equals("붉은기")) {
                       cool++;
                       sc++;
                       wc++;
                   }
                   next.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent = new Intent(getApplicationContext(),Test2.class);
                           intent.putExtra("warm",warm);
                           intent.putExtra("cool",cool);
                           intent.putExtra("sw",sw);
                           intent.putExtra("aw",aw);
                           intent.putExtra("sc",sc);
                           intent.putExtra("wc",wc);
                           startActivity(intent);
                       }
                   });
                }
            }
        });
    }
}
