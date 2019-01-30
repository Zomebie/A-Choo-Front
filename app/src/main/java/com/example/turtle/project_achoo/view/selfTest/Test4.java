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

public class Test4 extends AppCompatActivity {

    int w , c, sW, aW, sC, wC;
    TextView preview;
    TextView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test4);

        Intent intent = getIntent();
        final int warm = getIntent().getIntExtra("warm",1);
        final int cool = getIntent().getIntExtra("cool", 1);
        final int sw = getIntent().getIntExtra("sw", 1);
        final int aw = getIntent().getIntExtra("aw", 1);
        final int sc = getIntent().getIntExtra("sc", 1);
        final int wc = getIntent().getIntExtra("wc", 1);

        Log.i("test",warm +"입니다.");
        Log.i("test",cool+"입니다.");
        Log.i("test",sw+"입니다.");
        Log.i("test",aw+"입니다.");
        Log.i("test",sc+"입니다.");
        Log.i("test",wc+"입니다.");

        w = warm;
        c = cool;
        sW = sw;
        aW = aw;
        sC = sc;
        wC = wc;

        preview = (TextView)findViewById(R.id.preview);
        next = (TextView)findViewById(R.id.next);

        final RadioGroup rg4 = (RadioGroup)findViewById(R.id.radioGroup4);
        //４. 볼에 홍조가 있나요?
        rg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton)findViewById(checkedId);

                if(rb.isChecked()) {
                    if (rb.getText().equals("홍조가 없거나 미세해서 얼굴에 변화를 주지 않는다")) { // 웜톤
                        w++;
                        sW++;
                        aW++;
                    } else if (rb.getText().equals("홍조가 심하거나 얼굴에 자주 붉은홍조가 나타난다")) {// 쿨톤
                        c++;
                        sC++;
                        wC++;
                    }
                }
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(),Test5.class);
                        intent.putExtra("warm",w);
                        intent.putExtra("cool",c);
                        intent.putExtra("sw",sW);
                        intent.putExtra("aw",aW);
                        intent.putExtra("sc",sC);
                        intent.putExtra("wc",wC);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public void onclick(View view){

        Intent intent = null;

        switch (view.getId()){

            case R.id.preview: intent = new Intent(this, Test3.class);
                startActivity(intent);
                break;
            case R.id.next:
                Toast.makeText(getApplicationContext(),"선택해주세요.",Toast.LENGTH_LONG).show();
            break;
        }
    }
}
