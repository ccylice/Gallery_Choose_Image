package ice.ccylice.gallery_choose_image;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onChoose(View view) {
        /*系统内置Activity*/
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        myResult.launch(intent);
    }

    /*onActivityResult已经被弃用，使用Activity Result API进行Activity的数据传输与回调*/
    protected ActivityResultLauncher<Intent> myResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    if (data != null && data.getData() != null){
                        Uri mUri = data.getData();
                        Bitmap mBitmap = null;
                        try {
                            mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),mUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ImageView iv_gallery_image = findViewById(R.id.iv_gallery_image);
                        iv_gallery_image.setImageBitmap(mBitmap);
                    }
                }
            }
    );
}