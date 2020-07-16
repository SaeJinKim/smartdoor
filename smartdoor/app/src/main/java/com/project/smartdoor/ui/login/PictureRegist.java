package com.project.smartdoor.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.project.smartdoor.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PictureRegist extends AppCompatActivity{
    private FrameLayout frameLayout;
    private  static  final  int REQUEST_IMAGE_CAPTURE=672;
    private  String imageFilePath;
    private Uri photoUri;
    private Bitmap bitmap;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            bitmap = BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(imageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int exifOrientation;
            int exifDegree;

            if (exif != null) {
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegress(exifOrientation);
            } else {
                exifDegree = 0;
            }

            ((ImageView) findViewById(R.id.picture_result)).setImageBitmap(rotate(bitmap, exifDegree));
        }

    }

    //화면 돌아갔을 때 각도 계산
    private int exifOrientationToDegress(int exifOrientation) {
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90){
            return  90;
        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }
        return 0;
    }

    //화면 돌아갔을 때
    private Bitmap rotate(Bitmap bitmap , float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_regist);
        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");

        final Button picture = findViewById(R.id.picture);
        final Button pictureSave = findViewById(R.id.savePicture);


        //사진찍기 버튼 눌렀을때 사진찍기
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사진 찍기 ----!------
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager())!=null){
                    File photoFile =null;
                    try {
                        photoFile = createImageFile();
                    }catch (IOException e){

                    }

                    if(photoFile != null){

                        photoUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName(), photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                        startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
                    }
                }
            }

            private File createImageFile() throws IOException {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "Test_" +timeStamp + "_";
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File image = File.createTempFile(
                        imageFileName,
                        ".jpg",
                        storageDir
                );
                  imageFilePath = image.getAbsolutePath();
                  return  image;
            };
        });
        
        
        //권한 체크 확인, 거부 시 이벤트
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getApplicationContext(),"권한이 허용됨", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(getApplicationContext(),"권한이 거부됨", Toast.LENGTH_SHORT).show();
            }
        };
            //권한 체크 팝업창
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다.")
                .setDeniedMessage("거부하셨습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();


        pictureSave.setOnClickListener(new View.OnClickListener() {
            //찍은 사진을 ftp 파일 서버로 이동 시킴 -> 해당 사용자의 폴더에 저장 -----!-----
            @Override
            public void onClick(View view) {
                if (bitmap != null) {
                    Toast.makeText(getApplicationContext(), "사진이 업로드되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PictureRegist.this, MainActivity.class);
                    intent.putExtra("userID",userID);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "사진이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}