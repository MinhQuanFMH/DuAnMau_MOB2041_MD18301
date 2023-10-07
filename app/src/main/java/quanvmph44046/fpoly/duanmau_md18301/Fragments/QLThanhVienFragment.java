package quanvmph44046.fpoly.duanmau_md18301.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import quanvmph44046.fpoly.duanmau_md18301.Adapters.ThanhVienAdapter;
import quanvmph44046.fpoly.duanmau_md18301.DAO.ThanhVienDAO;
import quanvmph44046.fpoly.duanmau_md18301.Model.ThanhVien;
import quanvmph44046.fpoly.duanmau_md18301.R;

public class QLThanhVienFragment extends Fragment {
    ThanhVienDAO thanhVienDAO;
    RecyclerView recyclerThanhVien;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlthanhvien, container, false);
        recyclerThanhVien = view.findViewById(R.id.recyclerThanhVien);
        FloatingActionButton floatAddThanhVien = view.findViewById(R.id.floatAddThanhVien);

        thanhVienDAO = new ThanhVienDAO(getContext());
        loadData();

        floatAddThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }

    private void loadData() {
        ArrayList<ThanhVien> list = thanhVienDAO.getDSThanhVien();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerThanhVien.setLayoutManager(linearLayoutManager);

        ThanhVienAdapter thanhVienAdapter = new ThanhVienAdapter(getContext(), list, thanhVienDAO);
        recyclerThanhVien.setAdapter(thanhVienAdapter);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_themthanhvien, null);
        builder.setView(view);

        EditText edtHoTen = view.findViewById(R.id.edtHoTen);
        EditText edtNamSinh = view.findViewById(R.id.edtNamSinh);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String hoten = edtHoTen.getText().toString();
                String namsinh = edtNamSinh.getText().toString();

                boolean check = thanhVienDAO.themThanhVien(hoten, namsinh);
                if(check == true){
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    // load data
                    loadData();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
