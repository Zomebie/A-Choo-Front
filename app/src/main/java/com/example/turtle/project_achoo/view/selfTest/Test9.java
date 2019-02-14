package com.example.turtle.project_achoo.view.selfTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.turtle.project_achoo.R;

public class Test9 extends AppCompatActivity {

    private int cnt = 0;
    Button lip_btn;
    CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8,cb9,cb10,cb11,cb12,cb13,cb14,cb15,cb16;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test9);

        Intent intent = getIntent();
        final int warm = getIntent().getIntExtra("warm",1);
        final int cool = getIntent().getIntExtra("cool", 1);
        final int sw = getIntent().getIntExtra("sw", 1);
        final int aw = getIntent().getIntExtra("aw", 1);
        final int sc = getIntent().getIntExtra("sc", 1);
        final int wc = getIntent().getIntExtra("wc", 1);
        final String result = getIntent().getStringExtra("imgtext");
        final int money=getIntent().getIntExtra("money",0);
//        final int money1 = getIntent().getIntExtra("money1",1);
//        final int money2 = getIntent().getIntExtra("money2", 1);
//        final int money3 = getIntent().getIntExtra("money3", 1);
//        final int money4 = getIntent().getIntExtra("money4", 1);
//        final int money5 = getIntent().getIntExtra("money5", 1);
//        final int money6 = getIntent().getIntExtra("money6", 1);

        Button btn = (Button)findViewById(R.id.lip_btn);

        //립스틱 체크박스...... 16개인데 어떻게 줄일 수 없나 ㅜㅜ
        cb1 = (CheckBox)findViewById(R.id.dark_cherry_red);
        cb2 = (CheckBox)findViewById(R.id.chili_red);
        cb3 = (CheckBox)findViewById(R.id.pumpkin_red);
        cb4 = (CheckBox)findViewById(R.id.claudia_pink);

        cb5 = (CheckBox)findViewById(R.id.bright_orange);
        cb6 = (CheckBox)findViewById(R.id.neon_coral);
        cb7 = (CheckBox)findViewById(R.id.pink_cherry);
        cb8 = (CheckBox)findViewById(R.id.pink_beige);


        cb9 = (CheckBox)findViewById(R.id.baby_pink);
        cb10 = (CheckBox)findViewById(R.id.violet_rose);
        cb11 = (CheckBox)findViewById(R.id.neon_pink);
        cb12 = (CheckBox)findViewById(R.id.nude_pink);


        cb13 = (CheckBox)findViewById(R.id.magenta_red);
        cb14 = (CheckBox)findViewById(R.id.burgundy_brown);
        cb15 = (CheckBox)findViewById(R.id.vivid_purple);
        cb16 = (CheckBox)findViewById(R.id.raspberry);

        lip_btn=findViewById(R.id.lip_btn);
        lip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lip = ""; // 결과를 출력할 문자열


                if(cb1.isChecked() == true) {
                    cnt++; // 체크 갯수 제한
                    lip += cb1.getCurrentTextColor(); // db에 들어갈 값
                }
                if(cb2.isChecked() == true) {
                    cnt++;
                    lip += cb2.getCurrentTextColor(); // db에 들어갈 값
                }
                if(cb3.isChecked() == true) {
                    cnt++;
                    lip += cb3.getCurrentTextColor(); // db에 들어갈 값
                }
                if(cb4.isChecked() == true) {
                    cnt++;
                    lip += cb4.getCurrentTextColor(); // db에 들어갈 값
                }

                if(cb5.isChecked() == true) {
                    cnt++;
                    lip += cb5.getCurrentTextColor(); // db에 들어갈 값
                }
                if(cb6.isChecked() == true) {
                    cnt++;
                    lip += cb6.getCurrentTextColor(); // db에 들어갈 값
                }
                if(cb7.isChecked() == true) {
                    cnt++;
                    lip += cb7.getCurrentTextColor(); // db에 들어갈 값
                }
                if(cb8.isChecked() == true) {
                    cnt++;
                    lip += cb8.getCurrentTextColor(); // db에 들어갈 값
                }

                if(cb9.isChecked() == true) {
                    cnt++;
                    lip += cb9.getCurrentTextColor(); // db에 들어갈 값
                }
                if(cb10.isChecked() == true) {
                    cnt++;
                    lip += cb10.getCurrentTextColor(); // db에 들어갈 값
                }
                if(cb11.isChecked() == true) {
                    cnt++;
                    lip += cb11.getCurrentTextColor(); // db에 들어갈 값
                }
                if(cb12.isChecked() == true) {
                    cnt++;
                    lip += cb12.getCurrentTextColor(); // db에 들어갈 값
                }

                if(cb13.isChecked() == true) {
                    cnt++;
                    lip += cb13.getCurrentTextColor(); // db에 들어갈 값
                }
                if(cb14.isChecked() == true) {
                    cnt++;
                    lip += cb14.getCurrentTextColor(); // db에 들어갈 값
                }
                if(cb15.isChecked() == true) {
                    cnt++;
                    lip += cb15.getCurrentTextColor(); // db에 들어갈 값
                }
                if(cb16.isChecked() == true) {
                    cnt++;
                    lip += cb16.getCurrentTextColor(); // db에 들어갈 값
                }

                Intent intent = new Intent(getApplicationContext(),Test10.class);
                intent.putExtra("warm",warm);
                intent.putExtra("cool",cool);
                intent.putExtra("sw",sw);
                intent.putExtra("aw",aw);
                intent.putExtra("sc",sc);
                intent.putExtra("wc",wc);
                intent.putExtra("imgtext",result);
                intent.putExtra("money",money);
               /* intent.putExtra("money1",money1);
                intent.putExtra("money2",money2);
                intent.putExtra("money3",money3);
                intent.putExtra("money4",money4);
                intent.putExtra("money5",money5);
                intent.putExtra("money6",money6);*/
                intent.putExtra("lip",lip);
                startActivity(intent);
            }
        });






    }
}
