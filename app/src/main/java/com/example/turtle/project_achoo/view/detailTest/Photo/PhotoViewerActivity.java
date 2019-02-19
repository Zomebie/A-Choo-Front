
package com.example.turtle.project_achoo.view.detailTest.Photo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.turtle.project_achoo.R;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class PhotoViewerActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "PhotoViewerActivity";

    ImageButton gallery, camera, back;
    Button spring_filter, summer_filter, fall_filter, winter_filter;
    HorizontalScrollView pasnel_color_spring_view, pasnel_color_summer_view, pasnel_color_fall_view, pasnel_color_winter_view;

    static final int getimagesetting = 1001;//for request intent
    private String[] permissions = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE
            ,Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int MULTIPLE_PERMISSIONS = 101;
    static final int getCamera = 2001;
    static final int getGallery = 2002;
    //
    String mCurrentPhotoPath;
    //
    ImageView faceImageView;

    // spring
    ImageButton ECB4B5, F19294, E31E27, E1B24E, D59B5B, A96127, D2B481, EDBB7B, c543116, DAE275, F79434, F15836, F46F37, c9AD7EC, c63C9DF, c72CCD5, c55C2BE,
            c0A71B4, c0E569E, BED954, F9EFA6, FBE80F, FFEFB7, FFDF77, FEFBE8, FEDBBF, FFE7CF, c7D3D9C, c28B04C, c8FC742;
    //summer
    ImageButton FAD2DD, F9A5C7, F367A2, F34161, FFF6A9, BAE0C5, c018659, c2C645C, A7C8EB, c70A4D4, c4C6BAE, c586F8E, c325889, AA9DCB, D893C1, c79527E, FFFFFB,
            BBC2C8, c8E877F, A2716A, EA9BA8, A75669, c963F52, E9C2BD, DDA197, C9B9DD, DAAED2, c2D7D72, AEC8E1, ABD4BC;
    // fall
    ImageButton F4C7AA, F48F8F, C72028, c8A171A, B55427, c971A20, FED653, F2A732, D0DE92, c689741, c58602B, A3C0CB, c4A83A3, c135E6C, c11395F, c58266C, FAF8E1,
            B38F5E, c7B583C, c553415, F1A45F, FA8F62, E1C983, FFCC31, c8EA183, c63AD70, c68785D, c445F4C, c0C6E70, ADBE63;
    // winter
    ImageButton F4D8E6, F15199, EE0383, B31E3A, c720C24, FAEE2A, c1AAD5C, c0D8F53, c154F48, E2F0FB, c159FCD, c1B489C, c3B2D7C, c120D43, AA218A, FFFFFF, D9DADF,
            c504B45, c000000, EAE0BD, EE165F, c96192B, c5C0818, c3D0F0F, D9D0B1, A2CD40, c302D7B, E0DACE, c6F645E, c43256D;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);
        if(checkPermissions())
            init();

//        faceView = findViewById(R.id.faceView);
        faceImageView = findViewById(R.id.faceImageView);
        back = (ImageButton)findViewById(R.id.back);
        spring_filter = (Button)findViewById(R.id.spring_filter);
        summer_filter = (Button)findViewById(R.id.summer_filter);
        fall_filter = (Button)findViewById(R.id.fall_filter);
        winter_filter = (Button)findViewById(R.id.winter_filter);
        pasnel_color_spring_view = (HorizontalScrollView)findViewById(R.id.pasnel_color_spring_view);
        pasnel_color_summer_view = (HorizontalScrollView)findViewById(R.id.pasnel_color_summer_view);
        pasnel_color_fall_view = (HorizontalScrollView)findViewById(R.id.pasnel_color_fall_view);
        pasnel_color_winter_view = (HorizontalScrollView)findViewById(R.id.pasnel_color_winter_view);

        SpringParsnel_ID();
        SpringParsnel_CLICK();
        SummerParsnel_ID();
        SummerParsnel_CLICK();
        FallParsnel_ID();
        FallParsnel_CLICK();
        WinterParsnel_ID();
        WinterParsnel_CLICK();

        Pasnel_Color_Back();

        spring_filter.setOnClickListener(this);
        summer_filter.setOnClickListener(this);
        fall_filter.setOnClickListener(this);
        winter_filter.setOnClickListener(this);

    }   // onCreate

    private void Pasnel_Color_Back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });
    }   // Pasnel_Color_Back

    @Override
    public void onClick(View v) {

        Intent intent=new Intent();

        switch (v.getId()){

            case R.id.camera:
                dispatchTakePictureIntent();
                break;

            case R.id.gallery:
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/gallery");
                startActivityForResult(intent, getGallery);
                break;

            case R.id.spring_filter:
                if(pasnel_color_spring_view.getVisibility() == View.VISIBLE) {
                    pasnel_color_spring_view.setVisibility(View.GONE);
//                    summer_filter.setEnabled(true);
//                    fall_filter.setEnabled(true);
//                    winter_filter.setEnabled(true);
                } else {
                    pasnel_color_spring_view.setVisibility(View.VISIBLE);
                    pasnel_color_summer_view.setVisibility(View.GONE);
                    pasnel_color_fall_view.setVisibility(View.GONE);
                    pasnel_color_winter_view.setVisibility(View.GONE);
//                    summer_filter.setEnabled(false);
//                    fall_filter.setEnabled(false);
//                    winter_filter.setEnabled(false);
                }
                break;

            case R.id.summer_filter:
                if(pasnel_color_summer_view.getVisibility() == View.VISIBLE) {
                    pasnel_color_summer_view.setVisibility(View.GONE);
                } else {
                    pasnel_color_spring_view.setVisibility(View.GONE);
                    pasnel_color_summer_view.setVisibility(View.VISIBLE);
                    pasnel_color_fall_view.setVisibility(View.GONE);
                    pasnel_color_winter_view.setVisibility(View.GONE);
                }
                break;

            case R.id.fall_filter:
                if(pasnel_color_fall_view.getVisibility() == View.VISIBLE) {
                    pasnel_color_fall_view.setVisibility(View.GONE);
                } else {
                    pasnel_color_spring_view.setVisibility(View.GONE);
                    pasnel_color_summer_view.setVisibility(View.GONE);
                    pasnel_color_fall_view.setVisibility(View.VISIBLE);
                    pasnel_color_winter_view.setVisibility(View.GONE);
                }
                break;

            case R.id.winter_filter:
                if(pasnel_color_winter_view.getVisibility() == View.VISIBLE) {
                    pasnel_color_winter_view.setVisibility(View.GONE);
                } else {
                    pasnel_color_spring_view.setVisibility(View.GONE);
                    pasnel_color_summer_view.setVisibility(View.GONE);
                    pasnel_color_fall_view.setVisibility(View.GONE);
                    pasnel_color_winter_view.setVisibility(View.VISIBLE);
                }
                break;
        }
    }   // onClick

    private void SpringParsnel_ID() {

        ECB4B5 = findViewById(R.id.ECB4B5);
        F19294 = findViewById(R.id.F19294);
        E31E27 = findViewById(R.id.E31E27);
        E1B24E = findViewById(R.id.E1B24E);
        D59B5B = findViewById(R.id.D59B5B);
        A96127 = findViewById(R.id.A96127);
        D2B481 = findViewById(R.id.D2B481);
        EDBB7B = findViewById(R.id.EDBB7B);
        c543116 = findViewById(R.id.c543116);
        DAE275 = findViewById(R.id.DAE275);
        F79434 = findViewById(R.id.F79434);
        F15836 = findViewById(R.id.F15836);
        F46F37 = findViewById(R.id.F46F37);
        c9AD7EC = findViewById(R.id.c9AD7EC);
        c63C9DF = findViewById(R.id.c63C9DF);
        c72CCD5 = findViewById(R.id.c72CCD5);
        c55C2BE = findViewById(R.id.c55C2BE);
        c0A71B4 = findViewById(R.id.c0A71B4);
        c0E569E = findViewById(R.id.c0E569E);
        BED954 = findViewById(R.id.BED954);
        F9EFA6 = findViewById(R.id.F9EFA6);
        FBE80F = findViewById(R.id.FBE80F);
        FFEFB7 = findViewById(R.id.FFEFB7);
        FFDF77 = findViewById(R.id.FFDF77);
        FEFBE8 = findViewById(R.id.FEFBE8);
        FEDBBF = findViewById(R.id.FEDBBF);
        FFE7CF = findViewById(R.id.FFE7CF);
        c7D3D9C = findViewById(R.id.c7D3D9C);
        c28B04C = findViewById(R.id.c28B04C);
        c8FC742 = findViewById(R.id.c8FC742);
    }   // SpringParsnel_ID

    private void SpringParsnel_CLICK() {

        ECB4B5.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFECB4B5);
            }
        });
        F19294.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFF19294);
            }
        });
        E31E27.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFE31E27);
            }
        });
        E1B24E.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFE1B24E);
            }
        });
        D59B5B.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFD59B5B);
            }
        });
        A96127.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFA96127);
            }
        });
        D2B481.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFD2B481);
            }
        });
        EDBB7B.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFEDBB7B);
            }
        });
        c543116.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF543116);
            }
        });
        DAE275.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFDAE275);
            }
        });
        F79434.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFF79434);
            }
        });
        F15836.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFF15836);
            }
        });
        F46F37.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFF46F37);
            }
        });
        c9AD7EC.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF9AD7EC);
            }
        });
        c63C9DF.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF63C9DF);
            }
        });
        c72CCD5.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF72CCD5);
            }
        });
        c55C2BE.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF55C2BE);
            }
        });
        c0A71B4.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF0A71B4);
            }
        });
        c0E569E.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF0E569E);
            }
        });
        BED954.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFBED954);
            }
        });
        F9EFA6.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFF9EFA6);
            }
        });
        FBE80F.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFFBE80F);
            }
        });
        FFEFB7.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFFFEFB7);
            }
        });
        FFDF77.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFFFDF77);
            }
        });
        FEFBE8.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFFEFBE8);
            }
        });
        FEDBBF.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFFEDBBF);
            }
        });
        FFE7CF.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFFFE7CF);
            }
        });
        c7D3D9C.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF7D3D9C);
            }
        });
        c28B04C.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF28B04C);
            }
        });
        c8FC742.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF8FC742);
            }
        });
    }   // SpringParsnel_CLICK

    public void FaceImageViewSetOnClick(int color){
        Bitmap faceImageViewBitmap = ((BitmapDrawable)faceImageView.getDrawable()).getBitmap();

        int rgb = faceImageViewBitmap.getPixel(1,faceImageViewBitmap.getHeight() -1);
        Bitmap b=replaceColor(faceImageViewBitmap, rgb, color);

        faceImageView.setImageBitmap(b);
    }   // FaceImageViewSetOnClick

    private void SummerParsnel_ID() {

        FAD2DD = findViewById(R.id.FAD2DD);
        F9A5C7 = findViewById(R.id.F9A5C7);
        F367A2 = findViewById(R.id.F367A2);
        F34161 = findViewById(R.id.F34161);
        FFF6A9 = findViewById(R.id.FFF6A9);
        BAE0C5 = findViewById(R.id.BAE0C5);
        c018659 = findViewById(R.id.c018659);
        c2C645C = findViewById(R.id.c2C645C);
        A7C8EB = findViewById(R.id.A7C8EB);
        c70A4D4 = findViewById(R.id.c70A4D4);
        c4C6BAE = findViewById(R.id.c4C6BAE);
        c586F8E = findViewById(R.id.c586F8E);
        c325889 = findViewById(R.id.c325889);
        AA9DCB = findViewById(R.id.AA9DCB);
        D893C1 = findViewById(R.id.D893C1);
        c79527E = findViewById(R.id.c79527E);
        FFFFFB = findViewById(R.id.FFFFFB);
        BBC2C8 = findViewById(R.id.BBC2C8);
        c8E877F = findViewById(R.id.c8E877F);
        A2716A = findViewById(R.id.A2716A);
        EA9BA8 = findViewById(R.id.EA9BA8);
        A75669 = findViewById(R.id.A75669);
        c963F52 = findViewById(R.id.c963F52);
        E9C2BD = findViewById(R.id.E9C2BD);
        DDA197 = findViewById(R.id.DDA197);
        C9B9DD = findViewById(R.id.C9B9DD);
        DAAED2 = findViewById(R.id.DAAED2);
        c2D7D72 = findViewById(R.id.c2D7D72);
        AEC8E1 = findViewById(R.id.AEC8E1);
        ABD4BC = findViewById(R.id.ABD4BC);
    }   // SummerParsnel_ID

    private void SummerParsnel_CLICK() {

        FAD2DD.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFFAD2DD);
            }
        });
        F9A5C7.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFF9A5C7);
            }
        });
        F367A2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFF367A2);
            }
        });
        F34161.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFF34161);
            }
        });
        FFF6A9.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFFFF6A9);
            }
        });
        BAE0C5.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFBAE0C5);
            }
        });
        c018659.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF018659);
            }
        });
        c2C645C.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF2C645C);
            }
        });
        A7C8EB.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFA7C8EB);
            }
        });
        c70A4D4.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF70A4D4);
            }
        });
        c4C6BAE.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF4C6BAE);
            }
        });
        c586F8E.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF586F8E);
            }
        });
        c325889.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF325889);
            }
        });
        AA9DCB.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFAA9DCB);
            }
        });
        D893C1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFD893C1);
            }
        });
        c79527E.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF79527E);
            }
        });

        FFFFFB.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFFFFFFB);
            }
        });BBC2C8.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFBBC2C8);
            }
        });c8E877F.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF8E877F);
            }
        });A2716A.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFA2716A);
            }
        });EA9BA8.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFEA9BA8);
            }
        });A75669.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFA75669);
            }
        });c963F52.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF963F52);
            }
        });E9C2BD.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFE9C2BD);
            }
        });DDA197.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFDDA197);
            }
        });C9B9DD.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFC9B9DD);
            }
        });DAAED2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFDAAED2);
            }
        });c2D7D72.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF2D7D72);
            }
        });AEC8E1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFAEC8E1);
            }
        });ABD4BC.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFABD4BC);
            }
        });
    }   // SummerParsnel_CLICK

    private void FallParsnel_ID() {

        F4C7AA = findViewById(R.id.F4C7AA);
        F48F8F = findViewById(R.id.F48F8F);
        C72028  = findViewById(R.id.C72028);
        c8A171A = findViewById(R.id.c8A171A);
        B55427  = findViewById(R.id.B55427);
        c971A20 = findViewById(R.id.c971A20);
        FED653 = findViewById(R.id.FED653);
        F2A732  = findViewById(R.id.F2A732);
        D0DE92 = findViewById(R.id.D0DE92);
        c689741  = findViewById(R.id.c689741);
        c58602B = findViewById(R.id.c58602B);
        A3C0CB  = findViewById(R.id.A3C0CB);
        c4A83A3  = findViewById(R.id.c4A83A3);
        c135E6C = findViewById(R.id.c135E6C);
        c11395F = findViewById(R.id.c11395F);
        c58266C  = findViewById(R.id.c58266C);
        FAF8E1  = findViewById(R.id.FAF8E1);
        B38F5E = findViewById(R.id.B38F5E);
        c7B583C = findViewById(R.id.c7B583C);
        c553415 = findViewById(R.id.c553415);
        F1A45F  = findViewById(R.id.F1A45F);
        FA8F62 = findViewById(R.id.FA8F62);
        E1C983   = findViewById(R.id.E1C983);
        FFCC31  = findViewById(R.id.FFCC31);
        c8EA183  = findViewById(R.id.c8EA183);
        c63AD70 = findViewById(R.id.c63AD70);
        c68785D  = findViewById(R.id.c68785D);
        c445F4C = findViewById(R.id.c445F4C);
        c0C6E70 = findViewById(R.id.c0C6E70);
        ADBE63  = findViewById(R.id.ADBE63);
    }   // FallParsnel_ID

    private void FallParsnel_CLICK() {

        F4C7AA.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFF4C7AA);
            }
        });F48F8F.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFF48F8F);
            }
        });C72028.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFC72028);
            }
        });c8A171A.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF8A171A);
            }
        });B55427.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFB55427);
            }
        });c971A20.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF971A20);
            }
        });FED653.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFFED653);
            }
        });F2A732.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFF2A732);
            }
        });D0DE92.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFD0DE92);
            }
        });c689741.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF689741);
            }
        });c58602B.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF58602B);
            }
        });A3C0CB.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFA3C0CB);
            }
        });c4A83A3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF4A83A3);
            }
        });c135E6C.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF135E6C);
            }
        });c11395F.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF11395F);
            }
        });c58266C.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF58266C);
            }
        });FAF8E1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFFAF8E1);
            }
        });B38F5E.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFB38F5E);
            }
        });c7B583C.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF7B583C);
            }
        });c553415.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF553415);
            }
        });F1A45F.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFF1A45F);
            }
        });FA8F62.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFFA8F62);
            }
        });E1C983.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFE1C983);
            }
        });FFCC31.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFFFCC31);
            }
        });c8EA183.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF8EA183);
            }
        });c63AD70.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF63AD70);
            }
        });c68785D.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF68785D);
            }
        });c445F4C.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF445F4C);
            }
        });c0C6E70.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF0C6E70);
            }
        });ADBE63.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFADBE63);
            }
        });
    }   // FallParsnel_CLICK

    private void WinterParsnel_ID() {

        F4D8E6= findViewById(R.id.F4D8E6);
        F15199= findViewById(R.id.F15199);
        EE0383= findViewById(R.id.EE0383);
        B31E3A= findViewById(R.id.B31E3A);
        c720C24= findViewById(R.id.c720C24);
        FAEE2A = findViewById(R.id.FAEE2A);
        c1AAD5C = findViewById(R.id.c1AAD5C);
        c0D8F53 = findViewById(R.id.c0D8F53);
        c154F48= findViewById(R.id.c154F48);
        E2F0FB = findViewById(R.id.E2F0FB);
        c159FCD = findViewById(R.id.c159FCD);
        c1B489C = findViewById(R.id.c1B489C);
        c3B2D7C = findViewById(R.id.c3B2D7C);
        c120D43 = findViewById(R.id.c120D43);
        AA218A = findViewById(R.id.AA218A);
        FFFFFF = findViewById(R.id.FFFFFF);
        D9DADF = findViewById(R.id.D9DADF);
        c504B45 = findViewById(R.id.c504B45);
        c000000= findViewById(R.id.c000000);
        EAE0BD = findViewById(R.id.EAE0BD);
        EE165F = findViewById(R.id.EE165F);
        c96192B  = findViewById(R.id.c96192B);
        c5C0818 = findViewById(R.id.c5C0818);
        c3D0F0F = findViewById(R.id.c3D0F0F);
        D9D0B1 = findViewById(R.id.D9D0B1);
        A2CD40 = findViewById(R.id.A2CD40);
        c302D7B = findViewById(R.id.c302D7B);
        E0DACE = findViewById(R.id.E0DACE);
        c6F645E = findViewById(R.id.c6F645E);
        c43256D = findViewById(R.id.c43256D);
    }   // WinterParsnel_ID

    private void WinterParsnel_CLICK() {

        F4D8E6.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFF4D8E6);
            }
        });F15199.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFF15199);
            }
        });EE0383.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFEE0383);
            }
        });B31E3A.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFB31E3A);
            }
        });c720C24.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF720C24);
            }
        });FAEE2A.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFFAEE2A);
            }
        });c1AAD5C.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF1AAD5C);
            }
        });c0D8F53.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF0D8F53);
            }
        });c154F48.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF154F48);
            }
        });E2F0FB.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFE2F0FB);
            }
        });c159FCD.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF159FCD);
            }
        });c1B489C.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF1B489C);
            }
        });c3B2D7C.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF3B2D7C);
            }
        });c120D43.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF120D43);
            }
        });AA218A.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFAA218A);
            }
        });FFFFFF.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFFFFFFF);
            }
        });D9DADF.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFD9DADF);
            }
        });
        c504B45.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF504B45);
            }
        });c000000.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF000000);
            }
        });EAE0BD.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFEAE0BD);
            }
        });EE165F.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFEE165F);
            }
        });c96192B.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF96192B);
            }
        });c5C0818.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF5C0818);
            }
        });c3D0F0F.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF3D0F0F);
            }
        });D9D0B1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFD9D0B1);
            }
        });A2CD40.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFA2CD40);
            }
        });c302D7B.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF302D7B);
            }
        });E0DACE.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFFE0DACE);
            }
        });c6F645E.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF6F645E);
            }
        });c43256D.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceImageViewSetOnClick(0xFF43256D);
            }
        });
    }   // WinterParsnel_CLICK

    public Bitmap replaceColor(Bitmap src,int fromColor, int targetColor) {
        if(src == null) {
            return null;
        }
        // Source image size
        int width = src.getWidth();
        int height = src.getHeight();
        int[] pixels = new int[width * height];
        //get pixels
        src.getPixels(pixels, 0, width, 0, 0, width, height);

        for(int x = 0; x < pixels.length; ++x) {
            pixels[x] = (pixels[x] == fromColor) ? targetColor : pixels[x];
        }
        // create result bitmap output
        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());
        //set pixels
        result.setPixels(pixels, 0, width, 0, 0, width, height);

        return result;
    }   // replaceColor

    private boolean checkPermissions() {
        int result;
        List<String> permissionList = new ArrayList<>();
        for (String pm : permissions) {
            result = ContextCompat.checkSelfPermission(this, pm);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(pm);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }   // checkPermissions

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (permissions[i].equals(this.permissions[0])) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                showNoPermissionToastAndFinish();
                                break;
                            }
                        }
                        if(i==permissions.length-1)
                            init();
                    }
                } else {
                    showNoPermissionToastAndFinish();
                }
            }
        }
    }   // onRequestPermissionsResult

    private void showNoPermissionToastAndFinish() {
        Toast.makeText(this,getString(R.string.limit),Toast.LENGTH_SHORT).show();
        init();
    }   // showNoPermissionToastAndFinish

    void init(){
        gallery = findViewById(R.id.gallery);
        gallery.setOnClickListener(this);
        camera = findViewById(R.id.camera);
        camera.setOnClickListener(this);
    }   // init

    void run(Bitmap image) {
        Log.d("qwer", "11111111111111111111111111111");
        FaceDetector detector = new FaceDetector.Builder(getApplicationContext())
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .build();
        Log.d("qwer", "222222222222222222222222222");
        //Detector<Face> safeDetector = new SafeFaceDetector(detector);

        DetectorThread detectorThread=new DetectorThread(detector ,image);
        detectorThread.run();

        Detector<Face> safeDetector2 = detectorThread.getDetector();
        SparseArray<Face> faces2 = detectorThread.getfaces();
//        Frame frame = new Frame.Builder().setBitmap(image).build();
//        SparseArray<Face> faces = safeDetector2.detect(frame);

        Log.d("qwer", "33333333333333333333333333");
        if (!safeDetector2.isOperational()) {

            Log.w(TAG, "Face detector dependencies are not yet available.");

            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show();
                Log.w(TAG, getString(R.string.low_storage_error));
            }
        }
        Log.d("qwer", "44444444444444444444444444");
//        FaceView overlay = (FaceView) findViewById(R.id.faceView);
//        overlay.setContent(image, faces);
//=============================================================================================
        faceImageView.setImageBitmap(image);
        Bitmap faceImageViewBitmapOrgin = ((BitmapDrawable)faceImageView.getDrawable()).getBitmap();

        Bitmap faceImageViewBitmap = faceImageViewBitmapOrgin.copy(Bitmap.Config.ARGB_8888, true);

        Canvas BitmapCanvas = new Canvas(faceImageViewBitmap);

        double viewWidth = BitmapCanvas.getWidth();
        double viewHeight = BitmapCanvas.getHeight();
        double imageWidth = faceImageViewBitmap.getWidth();
        double imageHeight = faceImageViewBitmap.getHeight();
        double scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight);

        Rect destBounds = new Rect(0, 0, (int)(imageWidth * scale), (int)(imageHeight * scale));
        BitmapCanvas.drawBitmap(faceImageViewBitmap, null, destBounds, null);

        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        Paint paint1 = new Paint();
        paint1.setColor(Color.BLUE);
        paint1.setStyle(Paint.Style.FILL);

        for (int i = 0; i < faces2.size(); ++i) {
            Face face = faces2.valueAt(i);
            BitmapCanvas.drawRect((float)(face.getPosition().x * scale),
                    (float)(face.getPosition().y * scale),
                    (float)((face.getPosition().x + face.getWidth()) * scale),
                    (float)((face.getPosition().y + face.getHeight()) * scale),
                    paint);
            // 밑에
            BitmapCanvas.drawRect(0,
                    BitmapCanvas.getHeight(),
                    BitmapCanvas.getWidth(),
                    (float)((face.getPosition().y + face.getHeight()) * scale),
                    paint1);
        }
        Log.d("qwer", "555555555555555555555555555");


        faceImageView.setImageDrawable(new BitmapDrawable(getResources(), faceImageViewBitmap));
        Log.d("qwer", "666666666666666666666666666");
//=============================================================================
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/AnimationCapture";
//        final FaceView faceView = (FaceView) findViewById(R.id.faceView);//캡쳐할영역(프레임레이아웃)
        final ImageView faceView = (ImageView) findViewById(R.id.faceImageView);

        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
            Toast.makeText(getApplicationContext(), "폴더가 생성되었습니다.", Toast.LENGTH_SHORT).show();
        }
        Log.d("qwer", "7777777777777777777777777777777777");
        SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        faceView.buildDrawingCache();
        Bitmap captureview = faceView.getDrawingCache();

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(path+"/Capture"+day.format(date)+".jpeg");
            captureview.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path + "/Capture" + day.format(date) + ".JPEG")));
            Toast.makeText(getApplicationContext(), "저장완료", Toast.LENGTH_SHORT).show();
            fos.flush();
            fos.close();
            faceView.destroyDrawingCache();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("qwer", "888888888888888888888888888888888");
//=============================================================================
        safeDetector2.release();
    }   // run

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }   // createImageFile

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.turtle.project_achoo.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, getCamera);
            }
        }
    }   // dispatchTakePictureIntent

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bm=null;
        if(resultCode==RESULT_OK){

            switch(requestCode){
                case getCamera:
                    File file = new File(mCurrentPhotoPath);

                    try {
                        bm = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                        // 갤러리 이미지의 메타데이터를 받아온다.
                        ExifInterface exif = new ExifInterface(mCurrentPhotoPath);

                        // TAG_ORIENTATION 이미지의 회전된 각도, ORIENTATION_UNDEFINED
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                        bm = rotateBitmap(bm, orientation);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("qwer", "12341234123412341234");
                    run(bm);
                    break;

                case getGallery:
                    try {
                        // 갤러리의 이미지를 비트맵으로 받아온다.
                        bm = MediaStore.Images.Media.getBitmap( getContentResolver(), data.getData());
                        // 갤러리의 이미지의 경로를 받는다.
                        Uri uri = data.getData();
                        // 갤러리의 이미지의 실제 경로를 받는다.
                        String imageUri = getRealPathFromURI(uri);
                        // 갤러리 이미지의 메타데이터를 받아온다.
                        ExifInterface exif = new ExifInterface(imageUri);
                        // TAG_ORIENTATION 이미지의 회전된 각도, ORIENTATION_UNDEFINED
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                        // 비트맵을 회전한다.
                        bm = rotateBitmap(bm, orientation);

                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }catch(OutOfMemoryError e){
                        Toast.makeText(getApplicationContext(), "이미지 용량이 너무 큽니다.", Toast.LENGTH_SHORT).show();
                    }
                    run(bm);

                    break;

                default:
                    break;
            }
        }
    }   // onActivityResult

    private String getRealPathFromURI(Uri contentURI) {

        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);

        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();

        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }

        return result;
    }   // getRealPathFromURI

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }   // rotateBitmap

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }   // onBackPressed

}
