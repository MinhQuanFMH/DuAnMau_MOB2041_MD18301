package quanvmph44046.fpoly.duanmau_md18301;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import quanvmph44046.fpoly.duanmau_md18301.Activities.LoginActivity;
import quanvmph44046.fpoly.duanmau_md18301.DAO.ThuThuDAO;
import quanvmph44046.fpoly.duanmau_md18301.Fragments.DoanhThuFragment;
import quanvmph44046.fpoly.duanmau_md18301.Fragments.QLLoaiSachFragment;
import quanvmph44046.fpoly.duanmau_md18301.Fragments.QLPhieuMuonFragment;
import quanvmph44046.fpoly.duanmau_md18301.Fragments.QLSachFragment;
import quanvmph44046.fpoly.duanmau_md18301.Fragments.QLThanhVienFragment;
import quanvmph44046.fpoly.duanmau_md18301.Fragments.ThongKeTop10Fragment;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolBar = findViewById(R.id.toolBar);
        frameLayout = findViewById(R.id.frameLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment= null;
                if (item.getItemId()== R.id.mQLPhieuMuon) {
                    fragment= new QLPhieuMuonFragment();
                } else if (item.getItemId()== R.id.mQLLoaiSach) {
                    fragment= new QLLoaiSachFragment();
                } else if (item.getItemId()== R.id.mDangXuat){
                    Intent intent= new Intent(MainActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (item.getItemId()== R.id.mDoiMatKhau) {
                    showDialogDoiMatKhau();
                } else if (item.getItemId()== R.id.mTop10) {
                    fragment = new ThongKeTop10Fragment();
                } else if (item.getItemId()== R.id.mDoanhThu) {
                    fragment = new DoanhThuFragment();
                } else if (item.getItemId()== R.id.mQLThanhVien) {
                    fragment = new QLThanhVienFragment();
                } else if (item.getItemId()== R.id.mQLSach) {
                    fragment = new QLSachFragment();
                }else{
                    fragment= new QLPhieuMuonFragment();
                }

                if(fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, fragment)
                            .commit();
                    toolBar.setTitle(item.getTitle());
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogDoiMatKhau(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setPositiveButton("Cập nhật", null)
                .setNegativeButton("Huỷ", null);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_doimatkhau, null);
        // Ánh xạ các thành phần của dialog_doimatkhau
        EditText edtPassOld = view.findViewById(R.id.edtPassOld);
        EditText edtPassNew = view.findViewById(R.id.edtPassNew);
        EditText edtRePassNew = view.findViewById(R.id.edtRePassNew);

        builder.setView(view);


        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = edtPassOld.getText().toString();
                String newPass = edtPassNew.getText().toString();
                String reNewPass = edtRePassNew.getText().toString();
                // Check nếu người dùng không nhập gì
                if(oldPass.equals("") || newPass.equals("") || reNewPass.equals("")) {
                    Toast.makeText(MainActivity.this, "Cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if(newPass.equals(reNewPass)) {
                        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                        String matt = sharedPreferences.getString("matt", "");
                        // Cập nhật
                        ThuThuDAO thuThuDAO = new ThuThuDAO(MainActivity.this);
                        int check = thuThuDAO.capNhatMatKhau(matt, oldPass, newPass);
                        if (check == 1) {
                            Toast.makeText(MainActivity.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else if (check == 0) {
                            Toast.makeText(MainActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Mật khẩu đã nhập không trùng nhau", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}