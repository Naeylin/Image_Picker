package com.example.imagepicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button pickBtn;
    ImageView imgView;
    Button changeBtn;


    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Views
        pickBtn = findViewById(R.id.pick_btn);
        imgView = findViewById(R.id.image_view);
        changeBtn = findViewById(R.id.change_btn);

        // Listeners
        pickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        // Permission not granted, to be requested
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        // Show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);

                    } else {
                        // Permission already granted
                        pickImageFromGallery();
                    }
                } else {
                        pickImageFromGallery();
                }
            }
        });
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
               openActivity2();
            }
        });
    }

    private void openActivity2() {
        Intent intent = new Intent (this, Activity2.class);
        startActivity(intent);
    }

    private void pickImageFromGallery() {
        // Intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);

    }

    // Handle Result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted
                    pickImageFromGallery();
                } else {
                    //permission was denied
                    Toast.makeText(this, "Permission denied :(", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Handle result of picked image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            // Set image to background
            imgView.setImageURI(data.getData());
        }
    }
}



