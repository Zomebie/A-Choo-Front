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
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turtle.project_achoo.R;
import com.example.turtle.project_achoo.view.detailTest.Patch.SafeFaceDetector;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhotoViewerFaceActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "PhotoViewerActivity";
    Button gallery, camera;
    static final int getimagesetting = 1001;//for request intent
    private String[] permissions = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE
            ,Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int MULTIPLE_PERMISSIONS = 101;
    static final int getCamera = 2001;
    static final int getGallery = 2002;

//    FaceView faceView;
    ImageView faceImageView;
    TextView textView;
    Button btn_faceColor;

    float X1;
    float Y1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer_face);
        if(checkPermissions())
            init();

//        faceView = findViewById(R.id.faceView);
        faceImageView = findViewById(R.id.faceImageView);
        textView = findViewById(R.id.textView);
        btn_faceColor = findViewById(R.id.btn_faceColor);


        btn_faceColor.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap faceImageViewBitmap = ((BitmapDrawable)faceImageView.getDrawable()).getBitmap();

                int rgb = faceImageViewBitmap.getPixel((int) X1,(int) Y1);
                int zzz = faceImageViewBitmap.getPixel(1, faceImageViewBitmap.getHeight() - 1);
                Log.d(TAG, rgb + "!=======!" + zzz);

                Bitmap b=replaceColor(faceImageViewBitmap, zzz, rgb);

                faceImageView.setImageBitmap(b);

            }
        });


    }

    public Bitmap replaceColor(Bitmap src,int fromColor, int targetColor) {
        if(src == null) {
            return null;
        }

        int width = src.getWidth();
        int height = src.getHeight();
        int[] pixels = new int[width * height];

        src.getPixels(pixels, 0, width, 0, 0, width, height);

        for(int x = 0; x < pixels.length; ++x) {
            pixels[x] = (pixels[x] == fromColor) ? targetColor : pixels[x];
        }

        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());

        result.setPixels(pixels, 0, width, 0, 0, width, height);

        return result;
    }

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
    }

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
    }

    private void showNoPermissionToastAndFinish() {
        Toast.makeText(this,getString(R.string.limit),Toast.LENGTH_SHORT).show();
        init();
    }

    void init(){
        gallery=findViewById(R.id.gallery);
        gallery.setOnClickListener(this);
        camera=findViewById(R.id.camera);
        camera.setOnClickListener(this);
    }

    void run(Bitmap image){

        FaceDetector detector = new FaceDetector.Builder(getApplicationContext())
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .build();

        Detector<Face> safeDetector = new SafeFaceDetector(detector);

        Frame frame = new Frame.Builder().setBitmap(image).build();
        SparseArray<Face> faces = safeDetector.detect(frame);

        if (!safeDetector.isOperational()) {

            Log.w(TAG, "Face detector dependencies are not yet available.");

            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show();
                Log.w(TAG, getString(R.string.low_storage_error));
            }
        }

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

        Paint paint2 = new Paint();
        paint2.setColor(Color.RED);
        paint2.setStyle(Paint.Style.STROKE);

        Paint paint1 = new Paint();
        paint1.setColor(Color.BLUE);
        paint1.setStyle(Paint.Style.FILL);

        for (int i = 0; i < faces.size(); ++i) {
            Face face = faces.valueAt(i);
//            BitmapCanvas.drawRect((float)(face.getPosition().x * scale),
//                    (float)(face.getPosition().y * scale),
//                    (float)((face.getPosition().x + face.getWidth()) * scale),
//                    (float)((face.getPosition().y + face.getHeight()) * scale),
//                    paint);
            // 밑에
            BitmapCanvas.drawRect(0,
                    BitmapCanvas.getHeight(),
                    BitmapCanvas.getWidth(),
                    (float)((face.getPosition().y + face.getHeight()) * scale),
                    paint1);

                for (Landmark landmark : face.getLandmarks()) {
                    int cx = (int) (landmark.getPosition().x * scale);
                    int cy = (int) (landmark.getPosition().y * scale);
//                    BitmapCanvas.drawCircle(cx, cy, 10, paint);

                    X1 = ((((float)((face.getPosition().x + face.getWidth()) * scale) - (float)(face.getPosition().x * scale)) / 3) + (float)(face.getPosition().x * scale));
                    Y1 = (((((float)((face.getPosition().y + face.getHeight()) * scale) - (float)(face.getPosition().y * scale)) / 3) * 2) + (float)(face.getPosition().y * scale));
                    BitmapCanvas.drawCircle(X1, Y1, 2, paint2);
                }

        }

        faceImageView.setImageDrawable(new BitmapDrawable(getResources(), faceImageViewBitmap));

//=============================================================================
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/AnimationCapture";
//        final FaceView faceView = (FaceView) findViewById(R.id.faceView);//캡쳐할영역(프레임레이아웃)
        final ImageView faceView = (ImageView) findViewById(R.id.faceImageView);

        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
            Toast.makeText(getApplicationContext(), "폴더가 생성되었습니다.", Toast.LENGTH_SHORT).show();
        }

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
//=============================================================================

        safeDetector.release();
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()){

            case R.id.camera:
                intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, getCamera);
                break;

            case R.id.gallery:
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/gallery");
                startActivityForResult(intent, getGallery);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bm=null;
        if(resultCode==RESULT_OK){
            switch(requestCode){
                case getCamera:

                    bm = (Bitmap) data.getExtras().get("data");
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
    }

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
    }

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
    }
}
