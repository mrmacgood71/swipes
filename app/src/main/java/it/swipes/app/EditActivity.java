package it.swipes.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;

import it.swipes.app.databinding.ActivityEditBinding;

public class EditActivity extends AppCompatActivity {

    ActivityEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.pick.setOnClickListener(view -> {
            int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                pickImage();

            } else {
                if (permission != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

                } else {
                    pickImage();
                }
            }
        });

        binding.backToMain.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("id", "115105241828159907784");
            intent.putExtra("photo", "https://lh3.googleusercontent.com/a-/AOh14GgTrb04ca8CwETwya3lTr7PSdVE67X6j4NMgwtL");
            startActivity(intent);
        });

        binding.send.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("id", "115105241828159907784");
            intent.putExtra("photo", "https://lh3.googleusercontent.com/a-/AOh14GgTrb04ca8CwETwya3lTr7PSdVE67X6j4NMgwtL");
            startActivity(intent);
        });
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/**");
        startActivityForResult(intent, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage();
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();

            switch (requestCode) {
                case 100:
                    Intent intent = new Intent(this, DsPhotoEditorActivity.class);

                    intent.setData(uri);

                    intent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "Images");

                    intent.putExtra(DsPhotoEditorConstants.DS_TOOL_BAR_BACKGROUND_COLOR, Color.parseColor("#CCCCCC"));
                    intent.putExtra(DsPhotoEditorConstants.DS_MAIN_BACKGROUND_COLOR, Color.parseColor("#000000"));

                    intent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, new int[]{
                            DsPhotoEditorActivity.TOOL_WARMTH,
                            DsPhotoEditorActivity.TOOL_PIXELATE,
                            DsPhotoEditorActivity.TOOL_STICKER,
                            DsPhotoEditorActivity.TOOL_DRAW,
                            DsPhotoEditorActivity.TOOL_ORIENTATION,
                            DsPhotoEditorActivity.TOOL_VIGNETTE,
                            DsPhotoEditorActivity.TOOL_FRAME,
                            DsPhotoEditorActivity.TOOL_ROUND,
                            DsPhotoEditorActivity.TOOL_EXPOSURE,
                            DsPhotoEditorActivity.TOOL_CONTRAST,
                    });

                    startActivityForResult(intent, 101);

                    break;
                case 101:
                    binding.image.setImageURI(uri);

                    break;

            }
        }

    }
}