package com.example.turtle.project_achoo.view.detailTest.Photo;

import android.graphics.Bitmap;
import android.util.SparseArray;

import com.example.turtle.project_achoo.view.detailTest.Patch.SafeFaceDetector;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class DetectorThread implements Runnable {

    FaceDetector detector;
    Bitmap image;
    SparseArray<Face> faces;


    public DetectorThread(FaceDetector detector, Bitmap image){

        this.detector=detector;
        this.image=image;
    }
    @Override
    public void run() {
        Detector<Face> safeDetector = new SafeFaceDetector(detector);
        Frame frame = new Frame.Builder().setBitmap(image).build();
        faces = safeDetector.detect(frame);

    }

    public FaceDetector getDetector(){

        return detector;
    }

    public SparseArray<Face> getfaces(){

        return faces;
    }
}
