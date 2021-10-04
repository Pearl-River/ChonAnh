package com.example.chonanh;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton bntActionButton, bntActionButtonCamera, bntActionButtonFolder, bntActionButtonImage;
    private ImageView imgHinhAnh;
    private Animation fabOpen, fabClose, rotateForward, rotateBackward;

    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgHinhAnh = findViewById(R.id.imgHinhAnh);
        bntActionButton = findViewById(R.id.bntActionButton);
        bntActionButtonCamera = findViewById(R.id.bntActionButtonCamera);
        bntActionButtonFolder = findViewById(R.id.bntActionButtonFolder);
        bntActionButtonImage = findViewById(R.id.bntActionButtonImage);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backwawrd);

        bntActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
            }
        });

        bntActionButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                galleryActivityResultLauncher.launch(intent);
            }
        });

    }
    private ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        Uri imageUri = data.getData();
                        imgHinhAnh.setImageURI(imageUri);
                    }
                }
            }
    );
    private  void animateFab(){
        if (isOpen){
            bntActionButton.startAnimation(rotateBackward);
            bntActionButtonFolder.startAnimation(fabClose);
            bntActionButtonCamera.startAnimation(fabClose);
            bntActionButtonImage.startAnimation(fabClose);
            bntActionButtonFolder.setClickable(false);
            bntActionButtonCamera.setClickable(false);
            bntActionButtonImage.setClickable(false);
            isOpen = false;
        }
        else{
            bntActionButton.startAnimation(rotateForward);
            bntActionButtonFolder.startAnimation(fabOpen);
            bntActionButtonCamera.startAnimation(fabOpen);
            bntActionButtonImage.startAnimation(fabOpen);
            bntActionButtonFolder.setClickable(true);
            bntActionButtonCamera.setClickable(true);
            bntActionButtonImage.setClickable(true);
            isOpen = true;
        }
    }
}