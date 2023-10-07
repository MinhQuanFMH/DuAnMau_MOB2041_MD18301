package quanvmph44046.fpoly.duanmau_md18301.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import quanvmph44046.fpoly.duanmau_md18301.R;

public class ManHinhChaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        // Ánh xạ
        ImageView logomhc = findViewById(R.id.logomhc);
        Glide.with(this).load(R.mipmap.logomhc1).into(logomhc);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            startActivity(new Intent(ManHinhChaoActivity.this, LoginActivity.class));
            }
        }, 4500);
    }
}