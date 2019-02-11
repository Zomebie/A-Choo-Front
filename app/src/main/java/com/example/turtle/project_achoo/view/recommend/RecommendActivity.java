package com.example.turtle.project_achoo.view.recommend;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turtle.project_achoo.R;
import com.example.turtle.project_achoo.view.detailTest.DetailActivity;
import com.example.turtle.project_achoo.view.detailTest.DetailChoiceActivity;
import com.example.turtle.project_achoo.view.home.HomeActivity;
import com.example.turtle.project_achoo.view.login.MainActivity;
import com.example.turtle.project_achoo.view.myPage.MypageActivity;
import com.example.turtle.project_achoo.view.product.ProductActivity;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class RecommendActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private ImageButton home , product, detail, community, mypage, logout_button;
    private TextView home_text;

    String state;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        SharedPreferences appData = getSharedPreferences("appData", MODE_PRIVATE); // SharedPreferences 객체 가져오기

        if (appData.contains("login_status")) {

            id = appData.getString("login_id", "defValue"); // 로그인한 아이디 가져오기

        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        home = (ImageButton) findViewById(R.id.home);
        product = (ImageButton) findViewById(R.id.product);
        detail = (ImageButton) findViewById(R.id.detail);
        community = (ImageButton) findViewById(R.id.community);
        mypage = (ImageButton) findViewById(R.id.mypage);
        logout_button = (ImageButton) findViewById(R.id.logout_button);
        home_text = (TextView)findViewById(R.id.home_text);

        // 마이페이지 전환 애니메이션
        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendActivity.this, MypageActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        // 상세진단
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendActivity.this, DetailChoiceActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RecommendActivity.this);
                builder.setTitle("logout");
                builder.setMessage("로그아웃 하시겠습니까?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences.Editor editor = appData.edit();
                        editor.putBoolean("login_status", false);
                        editor.commit();

                        // 카카오톡 로그아웃
                        UserManagement.requestLogout(new LogoutResponseCallback() {

                            @Override

                            public void onCompleteLogout() {


                            }

                        });

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                        Snackbar.make(v, "로그아웃 되었습니다.", Snackbar.LENGTH_LONG).show();
                        finish();
                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setCancelable(false);
                builder.setIcon(R.drawable.home1);

                builder.show();
            }
        });
    }

    public void onclick(View view){

        Intent intent = null;

        switch (view.getId()){

            case R.id.home : intent = new Intent(this,HomeActivity.class);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.product: intent = new Intent(this, ProductActivity.class);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
//            case R.id.detail: intent = new Intent(this, DetailActivity.class);
//                finish();
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//                break;
            case  R.id.community: intent = new Intent(this, RecommendActivity.class);
                finish();
                overridePendingTransition(0, 0);
                break;
//            case  R.id.mypage: intent = new Intent(this, MypageActivity.class);
//                finish();
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                break;
            case  R.id.home_text: intent = new Intent(this, HomeActivity.class);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;

        }
        startActivity(intent);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0: PriceFragment priceFragment = new PriceFragment();
                    return priceFragment;
                case 1: GoodFragment goodFragment = new GoodFragment();
                    return goodFragment;
                case 2:
                    MytoneFragment mytoneFragment = new MytoneFragment();
                    return mytoneFragment;
                case 3:
                    ColorFragment colorFragment = new ColorFragment();
                    return colorFragment;
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0: return "price";
                case 1: return "good";
                case 2: return "mytone";
                case 3: return "color";
            }

            return null;
        }
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("아름다움을 추천하다")
                .setMessage("정말로 앱을 종료하시겠습니까?")
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }
}
