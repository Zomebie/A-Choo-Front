package com.example.turtle.project_achoo.view.selfTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.turtle.project_achoo.R;

public class Test9 extends AppCompatActivity {
    Button lip_btn;
    CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9, cb10, cb11, cb12, cb13, cb14, cb15, cb16;
    private String color_result = "";
    private String color_result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test9);

        Intent intent = getIntent();
        final int warm = getIntent().getIntExtra("warm", 1);
        final int cool = getIntent().getIntExtra("cool", 1);
        final int sw = getIntent().getIntExtra("sw", 1);
        final int aw = getIntent().getIntExtra("aw", 1);
        final int sc = getIntent().getIntExtra("sc", 1);
        final int wc = getIntent().getIntExtra("wc", 1);
        final String result = getIntent().getStringExtra("imgtext");
        final int money = getIntent().getIntExtra("money", 1);

        Button btn = (Button) findViewById(R.id.lip_btn);

        //립스틱 체크박스...... 16개인데 어떻게 줄일 수 없나 ㅜㅜ
        cb1 = (CheckBox) findViewById(R.id.dark_cherry_red);
        cb2 = (CheckBox) findViewById(R.id.chili_red);
        cb3 = (CheckBox) findViewById(R.id.pumpkin_red);
        cb4 = (CheckBox) findViewById(R.id.claudia_pink);

        cb5 = (CheckBox) findViewById(R.id.bright_orange);
        cb6 = (CheckBox) findViewById(R.id.neon_coral);
        cb7 = (CheckBox) findViewById(R.id.pink_cherry);
        cb8 = (CheckBox) findViewById(R.id.pink_beige);


        cb9 = (CheckBox) findViewById(R.id.baby_pink);
        cb10 = (CheckBox) findViewById(R.id.violet_rose);
        cb11 = (CheckBox) findViewById(R.id.neon_pink);
        cb12 = (CheckBox) findViewById(R.id.nude_pink);


        cb13 = (CheckBox) findViewById(R.id.magenta_red);
        cb14 = (CheckBox) findViewById(R.id.burgundy_brown);
        cb15 = (CheckBox) findViewById(R.id.vivid_purple);
        cb16 = (CheckBox) findViewById(R.id.raspberry);

        lip_btn = findViewById(R.id.lip_btn);
        lip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lip = 0; // 결과를 출력할 문자열
                String color_hashcode = null;

                if (cb1.isChecked() == true) {
                    lip += cb1.getCurrentTextColor(); // db에 들어갈
                    color_hashcode = lip_result(lip);
                    color_result = add(color_hashcode);

                }
                if (cb2.isChecked() == true) {
                    lip += cb2.getCurrentTextColor(); // db에 들어갈 값
                    color_hashcode = lip_result(lip);
                    color_result = add(color_hashcode);
                }
                if (cb3.isChecked() == true) {
                    lip += cb3.getCurrentTextColor(); // db에 들어갈 값
                    color_hashcode = lip_result(lip);
                    color_result = add(color_hashcode);
                }
                if (cb4.isChecked() == true) {
                    lip += cb4.getCurrentTextColor(); // db에 들어갈 값
                    color_hashcode = lip_result(lip);
                    color_result = add(color_hashcode);
                }

                if (cb5.isChecked() == true) {
                    lip += cb5.getCurrentTextColor(); // db에 들어갈 값
                    color_hashcode = lip_result(lip);
                    color_result = add(color_hashcode);
                }
                if (cb6.isChecked() == true) {
                    lip += cb6.getCurrentTextColor(); // db에 들어갈 값
                    color_hashcode = lip_result(lip);
                    color_result = add(color_hashcode);
                }
                if (cb7.isChecked() == true) {
                    lip += cb7.getCurrentTextColor(); // db에 들어갈 값
                    color_hashcode = lip_result(lip);
                    color_result = add(color_hashcode);
                }
                if (cb8.isChecked() == true) {
                    lip += cb8.getCurrentTextColor(); // db에 들어갈 값
                    color_hashcode = lip_result(lip);
                    color_result = add(color_hashcode);
                }

                if (cb9.isChecked() == true) {
                    lip += cb9.getCurrentTextColor(); // db에 들어갈 값
                    color_hashcode = lip_result(lip);
                    color_result = add(color_hashcode);
                }
                if (cb10.isChecked() == true) {
                    lip += cb10.getCurrentTextColor(); // db에 들어갈 값
                    color_hashcode = lip_result(lip);
                    color_result = add(color_hashcode);
                }
                if (cb11.isChecked() == true) {
                    lip += cb11.getCurrentTextColor(); // db에 들어갈 값
                    color_hashcode = lip_result(lip);
                    color_result = add(color_hashcode);
                }
                if (cb12.isChecked() == true) {
                    lip += cb12.getCurrentTextColor(); // db에 들어갈 값
                    color_hashcode = lip_result(lip);
                    color_result = add(color_hashcode);
                }

                if (cb13.isChecked() == true) {
                    lip += cb13.getCurrentTextColor(); // db에 들어갈 값
                    color_hashcode = lip_result(lip);
                    color_result = add(color_hashcode);
                }
                if (cb14.isChecked() == true) {
                    lip += cb14.getCurrentTextColor(); // db에 들어갈 값
                    color_hashcode = lip_result(lip);
                    color_result = add(color_hashcode);
                }
                if (cb15.isChecked() == true) {
                    lip += cb15.getCurrentTextColor(); // db에 들어갈 값.
                    color_hashcode = lip_result(lip);
                    color_result = add(color_hashcode);
                }
                if (cb16.isChecked() == true) {
                    lip += cb16.getCurrentTextColor(); // db에 들어갈 값
                    color_hashcode = lip_result(lip);
                    color_result = add(color_hashcode);
                }


                Intent intent = new Intent(getApplicationContext(), Test10.class);
                intent.putExtra("warm", warm);
                intent.putExtra("cool", cool);
                intent.putExtra("sw", sw);
                intent.putExtra("aw", aw);
                intent.putExtra("sc", sc);
                intent.putExtra("wc", wc);
                intent.putExtra("imgtext", result);
                intent.putExtra("money", money);
                intent.putExtra("lip_color",color_result);
                startActivity(intent);
            }
        });

    }

    public String lip_result(int lip) {
        String lip_result = Integer.toHexString(lip);
        StringBuffer color_code = new StringBuffer();
        for (int i = 2; i < lip_result.length(); i++) {
            color_code.append(lip_result.charAt(i));
        }
        String shop = "#";
        String color_HashCode = shop.concat(color_code + "");
        System.out.println(color_HashCode);
        return color_HashCode;
    }

    public String add(String color_hashcode) {
        color_result2 = color_result.concat(color_hashcode);
        return color_result2;
    }
}
