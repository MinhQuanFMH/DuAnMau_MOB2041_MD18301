package quanvmph44046.fpoly.duanmau_md18301.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import quanvmph44046.fpoly.duanmau_md18301.DAO.ThuThuDAO;
import quanvmph44046.fpoly.duanmau_md18301.MainActivity;
import quanvmph44046.fpoly.duanmau_md18301.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText edtusername = findViewById(R.id.edtusername);
        EditText edtpassword = findViewById(R.id.edtpassword);
        Button btndangnhap = findViewById(R.id.btndangnhap);

        ThuThuDAO thuThuDAO = new ThuThuDAO(this);

        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtusername.getText().toString();
                String pass = edtpassword.getText().toString();
                if(thuThuDAO.checkDangNhap(user, pass)){
                    // Lưu thông tin ở đây
                    SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("matt", user);
                    editor.commit();

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Tài khoản và mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}